import allocator.Allocator;

import allocator.ToAllocate;
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
        integreteToAllocateList();
        integreteAvailableList();
        allocate();
    }

    private void integrateAvailableList(){
        for (BuildingType buildingType : allocationType.getBuildings().getBuilding()){
            for (RoomType roomType : buildingType.getRoom()){
                if (roomType.getAvailableForAllocation()==null){
                    ToAllocate available = new ToAllocate();
                    available.setAnswer(buildingType.getId()+ "#" + roomType.getId());
                    available.addRequirement(new NumberOfPlaces(Integer.parseInt(roomType.getNumberOfPlaces())));
                    if (roomType.getFeatureIds()!=null)
                        available.addRequirement(new Features(roomType.getFeatureIds()));
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
            String[] ids = toAllocate.getId().split("#");
            setRoomID(ids[0], ids[1], toAllocate.getAnswer());
        }
        for(ToAllocate toAllocate : toAllocateList){
            String[] ids = toAllocate.getId().split("#");
            setRoomID(ids[0], ids[1], toAllocate.getAnswer());
        }
        Parser.marshall(allocationType, path);
    }

    private void setRoomID(String course, String group, String answer){
        for (CourseType courseType : allocationType.getCourses().getCourse()){
            if (courseType.getId().equals(course))
                for (GroupType groupType : courseType.getGroup()){
                    if (groupType.getId().equals(group))
                        for(SessionType sessionType : groupType.getSession()){
                            if(sessionType.getRoomId()==null || sessionType.getRoomId().length()==0){
                                String[] ids = answer.split("#");
                                if (ids.length==1)
                                    sessionType.setRoomId(answer);
                                else if(ids.length==2){
                                    sessionType.setBuildingId(ids[0]);
                                    sessionType.setRoomId(ids[1]);
                                }
                                return;
                            }
                        }
                }
        }
        return;
    }


    public List<ToAllocate> getToAllocateList() {
        return toAllocateList;
    }

    public List<ToAllocate> getAvailableList() {
        return availableList;
    }


}
