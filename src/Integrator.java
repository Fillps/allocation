import allocator.Allocator;

import allocator.Requirement;
import allocator.ToAllocate;
import com.sun.org.apache.regexp.internal.RE;
import room_allocations.*;
import room_xml.*;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
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
                    if (toAllocate==null){
                        toAllocate = new ToAllocate();
                        if ((!hasFeatures && sessionType.getFeatureIds()!=null) || (!hasFeatures && sessionType.getFeaturesIds()!=null))
                            hasFeatures = true;
                    }
                    else if (hasFeatures && (sessionType.getFeaturesIds()==null && sessionType.getFeatureIds()==null)){
                        toAllocateList.add(toAllocate);
                        toAllocate = new ToAllocate();
                        hasFeatures = false;
                    }
                    else if (!hasFeatures && sessionType.getFeatureIds()!=null){
                        toAllocateList.add(toAllocate);
                        toAllocate = new ToAllocate();
                        hasFeatures = true;
                        toAllocate.addRequirement(new Features(sessionType.getFeatureIds()));
                    }
                    else if (!hasFeatures && sessionType.getFeaturesIds()!=null){
                        toAllocateList.add(toAllocate);
                        toAllocate = new ToAllocate();
                        hasFeatures = true;
                        toAllocate.addRequirement(new Features(sessionType.getFeaturesIds()));
                    }

                    toAllocate.setId(courseType.getId() + "#" + groupType.getId());

                    toAllocate.addRequirement(new NumberOfPlaces(Integer.parseInt(groupType.getNumberOfStudents())));

                    int classes = Integer.valueOf(sessionType.getDuration())/120;
                    for (int i = 0; i < classes; i++){
                        String time = sessionType.getStartTime();
                        int hours = Integer.parseInt(time.substring(0,1));
                        hours += i*2;
                        time.replaceFirst(time.substring(0,1), String.valueOf(hours));
                        StartDate startDate = new StartDate(sessionType.getWeekday(), time);
                        toAllocate.addRequirement(startDate);
                        toAllocate.addRequirement(new Teacher(groupType.getTeacher(), startDate));
                    }


                }
                toAllocateList.add(toAllocate);
            }
        }
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
