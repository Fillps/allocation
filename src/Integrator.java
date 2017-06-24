import allocator.Allocator;

import allocator.Requirement;
import allocator.ToAllocate;
import com.sun.org.apache.regexp.internal.RE;
import room_allocations.*;
import room_xml.*;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 26/05/2017.
 */
public class Integrator extends Allocator{

    private AllocationType allocationType;

    public Integrator(String path) throws JAXBException, XMLStreamException {
        super(new ArrayList<ToAllocate>(), new ArrayList<ToAllocate>());
        this.allocationType = Parser.unmarshall(path);
        integrateToAllocateList();
        integrateAvailableList();
        allocate();
    }

    private void integrateAvailableList(){
        for (BuildingType buildingType : allocationType.getBuildings().getBuilding()){
            for (RoomType roomType : buildingType.getRoom()){
                if (roomType.getAvailableForAllocation()==null){
                    ToAllocate available = new ToAllocate();
                    available.setAnswer(buildingType.getId()+ "#" + roomType.getId());
                    available.addRequirement(new NumberOfPlaces(Integer.parseInt(roomType.getNumberOfPlaces())));
                    if (roomType.getFeatureIds()!=null) {
                        String[] features = roomType.getFeatureIds().split(", ");
                        for (String feature : features)
                            available.addRequirement(new Features(feature));
                    }
                    else
                        available.addRequirement(new Features("0"));
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
                        if ((!hasFeatures && sessionType.getFeatureIds()!=null) || (!hasFeatures && sessionType.getFeaturesIds()!=null))  // verifica sem tem features na primeira aula
                            hasFeatures = true;
                    }
                    else if (hasFeatures && (sessionType.getFeaturesIds()==null && sessionType.getFeatureIds()==null)){  //aula anterior teve features, essa nao tem
                        toAllocateList.add(toAllocate);
                        toAllocate = createToAllocateWithoutFeatures(courseType.getId(),groupType,sessionType);
                        hasFeatures = false;
                    }
                    else if (!hasFeatures && (sessionType.getFeatureIds()!=null || sessionType.getFeaturesIds()!=null)){ //aula anterior tem features, essa sim
                        toAllocateList.add(toAllocate);
                        toAllocate = createToAllocateWithoutFeatures(courseType.getId(),groupType,sessionType);
                        hasFeatures = true;
                        if (sessionType.getFeatureIds()!=null)
                            toAllocate.addRequirement(new Features(sessionType.getFeatureIds()));
                        else
                            toAllocate.addRequirement(new Features(sessionType.getFeaturesIds()));
                    }
                    else {              // aula sera na mesma sala anterior, senda assim, so adicionamos novos horarios para alocar, assim como o professor naquele horario
                        List<StartDate> startDateList = createStartDate(Integer.parseInt(sessionType.getDuration()), sessionType.getStartTime(), sessionType.getWeekday());
                        for (StartDate startDate : startDateList){
                            toAllocate.addRequirement(startDate);
                            toAllocate.addRequirement(new Teacher(groupType.getTeacher(), startDate));
                        }
                    }


                }
                toAllocateList.add(toAllocate);
            }
        }
    }

    private ToAllocate createToAllocateWithoutFeatures(String course, GroupType groupType, SessionType sessionType){

        ToAllocate toAllocate = new ToAllocate();
        toAllocate.setId(course + "#" + groupType.getId());
        toAllocate.addRequirement(new NumberOfPlaces(Integer.parseInt(groupType.getNumberOfStudents())));

        List<StartDate> startDateList = createStartDate(Integer.parseInt(sessionType.getDuration()), sessionType.getStartTime(), sessionType.getWeekday());
        for (StartDate startDate : startDateList){
            toAllocate.addRequirement(startDate);
            toAllocate.addRequirement(new Teacher(groupType.getTeacher(), startDate));
        }
        return toAllocate;
    }

    private List<StartDate> createStartDate(int duration, String startTime, String day){
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
        for(ToAllocate toAllocate : toAllocateList){
            String[] ids = toAllocate.getId().split("#"); //id -> course#group
            String[] answers = toAllocate.getAnswer().split("#"); //answer -> building#room
            for (String day : getDaysFromRequirements(toAllocate.getRequirements()))
                setRoomID(ids[0], ids[1], answers[1],answers[0],day);
        }
        Parser.marshall(allocationType, path);
    }

    private void setRoomID(String course, String group, String room, String building, String day){
       GroupType groupType = findGroup(course,group);
       if (groupType==null)
           return;
       for(SessionType sessionType : groupType.getSession()) {
           if (sessionType.getWeekday().equals(day))
               sessionType.setBuildingId(building);
               sessionType.setRoomId(room);
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

    private List<String> getDaysFromRequirements(List<Requirement> requirements){
        List<String> results = new ArrayList<>();
        for (Requirement requirement : requirements){
            if(requirement instanceof StartDate){
                StartDate startDate = (StartDate) requirement;
                results.add(startDate.getDay());
            }
        }
        return results;
    }


    public List<ToAllocate> getToAllocateList() {
        return toAllocateList;
    }

    public List<ToAllocate> getAvailableList() {
        return availableList;
    }


}
