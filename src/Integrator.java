import allocator.Allocator;

import allocator.Requirement;
import allocator.ToAllocate;
import room_allocations.*;
import room_xml.*;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 26/05/2017.
 */
public class Integrator extends Allocator{

    private AllocationType allocationType;

    private Map<StartDate, ToAllocate> professorStartTimeMap = new HashMap<>();

    public Integrator(String path) throws JAXBException, XMLStreamException {
        super(new ArrayList<ToAllocate>(), new ArrayList<ToAllocate>());
        this.allocationType = Parser.unmarshall(path);
        integrateAvailableList();
        integrateToAllocateList();
        allocate();
        updateAllocationType();
    }

    private void integrateAvailableList(){
        for (BuildingType buildingType : allocationType.getBuildings().getBuilding()){
            for (RoomType roomType : buildingType.getRoom()){
                if (roomType.getAvailableForAllocation()==null){
                    ToAllocate available = new ToAllocate();
                    String answer = buildingType.getId()+ "#" + roomType.getId();
                    available.setAnswer(answer);
                    available.setId(answer);
                    available.addRequirement(new NumberOfPlaces(Integer.parseInt(roomType.getNumberOfPlaces())));
                    if (roomType.getFeatureIds()!=null) {
                        String[] features = roomType.getFeatureIds().split(", ");
                        for (String feature : features)
                            available.addRequirement(new Features(feature));
                    }
                    availableList.add(available);
                }
            }
        }

    }

    private void integrateToAllocateList(){
        for (CourseType courseType : allocationType.getCourses().getCourse()){
            for (GroupType groupType : courseType.getGroup()){
                boolean hasFeatures = false;
                ToAllocate toAllocate = null;
                for (SessionType sessionType : groupType.getSession()){
                    if (toAllocate==null){              //primeira aula -> cria uma nova alocacao
                        toAllocate = createToAllocateWithoutFeatures(courseType.getId(),groupType,sessionType);
                        if (!hasFeatures && sessionType.getFeatureIds()!=null) {  // verifica sem tem features na primeira aula
                            hasFeatures = true;
                            toAllocate.addRequirement(new Features(sessionType.getFeatureIds()));
                        }
                    }
                    else if (hasFeatures && sessionType.getFeatureIds()==null){  //aula anterior teve features, essa nao tem
                        handleToAllocate(toAllocate);
                        toAllocate = createToAllocateWithoutFeatures(courseType.getId(),groupType,sessionType);
                        hasFeatures = false;
                    }
                    else if (!hasFeatures && sessionType.getFeatureIds()!=null){ //aula anterior tem features, essa sim
                        handleToAllocate(toAllocate);
                        toAllocate = createToAllocateWithoutFeatures(courseType.getId(),groupType,sessionType);
                        hasFeatures = true;
                        toAllocate.addRequirement(new Features(sessionType.getFeatureIds()));
                    }
                    else {              // aula sera na mesma sala anterior, senda assim, so adicionamos novos horarios para alocar, assim como o professor naquele horario
                        if (sessionType.getRequiresRoomId()!=null && sessionType.getRequiresBuildingId()!=null)//tambem verifica se possui requiresRoom
                            toAllocate.addRequirement(new RequiresRoom(sessionType.getRequiresBuildingId() + "#" + sessionType.getRequiresRoomId()));
                        List<StartDate> startDateList = createStartDate(Integer.parseInt(sessionType.getDuration()), sessionType.getStartTime(), sessionType.getWeekday());
                        for (StartDate startDate : startDateList){
                            toAllocate.addRequirement(startDate);
                            toAllocate.addRequirement(new Teacher(groupType.getTeacher(), startDate));
                        }
                    }
                }
                handleToAllocate(toAllocate);
            }
        }
    }

    private void handleToAllocate(ToAllocate toAllocate){
        for (Requirement requirement : toAllocate.getRequirements())
            if (requirement instanceof RequiresRoom){
                RequiresRoom requiresRoom = (RequiresRoom) requirement;
                toAllocate.setAnswer(requirement.answer());
                setToAllocateInfo(toAllocate);
                ToAllocate available = searchAvailable(toAllocate.getAnswer());
                if (available!=null)
                    for (StartDate startDate : getStartDateFromRequirements(toAllocate.getRequirements()))
                        available.addRequirement(startDate);
                return;
            }
        toAllocateList.add(toAllocate);
    }

    private ToAllocate searchAvailable(String id){
        for(ToAllocate available : availableList)
            if (available.getId().equals(id))
                return available;
        return null;
    }

    private ToAllocate createToAllocateWithoutFeatures(String course, GroupType groupType, SessionType sessionType){
        ToAllocate toAllocate = new ToAllocate();
        toAllocate.setId(course + "#" + groupType.getId());
        if (sessionType.getRequiresRoomId()!=null && sessionType.getRequiresBuildingId()!=null)
            toAllocate.addRequirement(new RequiresRoom(sessionType.getRequiresBuildingId() + "#" + sessionType.getRequiresRoomId()));


        List<StartDate> startDateList = createStartDate(Integer.parseInt(sessionType.getDuration()), sessionType.getStartTime(), sessionType.getWeekday());

        boolean numberOfStudentsAdded = false;
        for (StartDate startDate : startDateList){
            toAllocate.addRequirement(startDate);
            toAllocate.addRequirement(new Teacher(groupType.getTeacher(), startDate));
            if (professorStartTimeMap.containsKey(startDate)){
                int updatedNumber = updateNumberOfStudents(professorStartTimeMap.get(startDate),Integer.parseInt(groupType.getNumberOfStudents()));
                toAllocate.addRequirement(new NumberOfPlaces(updatedNumber));
                numberOfStudentsAdded = true;
            }
            if (!numberOfStudentsAdded)
                toAllocate.addRequirement(new NumberOfPlaces(Integer.parseInt(groupType.getNumberOfStudents())));
        }

        toAllocate.addRequirement(new NumberOfPlaces(Integer.parseInt(groupType.getNumberOfStudents())));


        return toAllocate;
    }

    private int updateNumberOfStudents(ToAllocate toAllocate, int numberOfStudents){
        for (Requirement requirement : toAllocate.getRequirements()){
            if (requirement instanceof NumberOfPlaces){
                NumberOfPlaces numberOfPlaces = (NumberOfPlaces) requirement;
                numberOfPlaces.setSeats(numberOfPlaces.getSeats() + numberOfStudents);
                return numberOfPlaces.getSeats();
            }
        }
        return numberOfStudents;
    }

    public List<StartDate> createStartDate(int duration, String startTime, String day){
        List<StartDate> startDateList = new ArrayList<>();
        int classes = duration/120;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (int i = 0; i < classes; i++){
            int minutesToAdd = i*120;
            LocalTime lTime = LocalTime.parse(startTime);
            String finalTime = timeFormatter.format(lTime.plusMinutes(minutesToAdd));
            startDateList.add(new StartDate(day, finalTime));
        }
        return startDateList;
    }

    public void saveToFile(String path) throws JAXBException {
        Parser.marshall(allocationType, path);
    }

    private void setToAllocateInfo(ToAllocate toAllocate){
        String[] ids = toAllocate.getId().split("#"); //id -> course#group
        String[] answers = toAllocate.getAnswer().split("#"); //answer -> building#room
        for (StartDate startDate : getStartDateFromRequirements(toAllocate.getRequirements()))
            setRoomID(ids[0], ids[1], answers[1],answers[0],startDate.getDay());
    }

    private void setRoomID(String course, String group, String room, String building, String day){
       GroupType groupType = findGroup(course,group);
       if (groupType==null)
           return;
       for(SessionType sessionType : groupType.getSession()) {
           if (sessionType.getWeekday().equals(day)){
               sessionType.setBuildingId(building);
               sessionType.setRoomId(room);
           }
       }
    }

    private GroupType findGroup(String course, String group){
        for (CourseType courseType : allocationType.getCourses().getCourse())
            if (courseType.getId().equals(course))
                for (GroupType groupType : courseType.getGroup())
                    if (groupType.getId().equals(group))
                        return groupType;
        return null;
    }

    private List<StartDate> getStartDateFromRequirements(List<Requirement> requirements){
        List<StartDate> results = new ArrayList<>();
        for (Requirement requirement : requirements)
            if(requirement instanceof StartDate)
                results.add((StartDate) requirement);
        return results;
    }


    public List<ToAllocate> getToAllocateList() {
        return toAllocateList;
    }

    public List<ToAllocate> getAvailableList() {
        return availableList;
    }

    private void updateAllocationType(){
        for(ToAllocate toAllocate : toAllocateList)
            setToAllocateInfo(toAllocate);
    }

    public AllocationType getAllocationType() {
        return allocationType;
    }


}
