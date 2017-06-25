import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import room_allocations.StartDate;
import room_xml.*;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by filip on 25/06/2017.
 */
class IntegratorTest {
    Integrator integrator;
    List<RoomTest> roomTestList;
    List<SessionTypeTest> sessionTypeTestList;
    @BeforeEach
    void setUp() {
        try {
            integrator = new Integrator("resources\\DemandasRecursos.xml");
            roomTestList = generateRoomTestList(integrator.getAllocationType().getBuildings().getBuilding());
            sessionTypeTestList = generateSessionTypeTestList(integrator.getAllocationType().getCourses().getCourse());
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Test
    void resultsTest() {
        for (SessionTypeTest session : sessionTypeTestList){
            if (!session.getBuildingId().equals("not found")){
                RoomTest room = searchRooms(session.getBuildingId(),session.getRoomId());
                assertNotNull(room);

                if (Integer.parseInt(room.getNumberOfPlaces())<session.getNumber_of_seats()){
                    System.out.println("Room: " + session.getBuildingId() + " - " + session.getRoomId() + "  Students: " + session.getNumber_of_seats() + " Seats: " + room.getNumberOfPlaces());
                }
                //assertTrue(Integer.parseInt(room.getNumberOfPlaces())>=session.getNumber_of_seats());
                if (session.getFeatureIds()!=null) {
                    if (room.getFeatureIds().contains(session.getFeatureIds())==false)
                    System.out.println("Room: " + session.getBuildingId() + " - " + session.getRoomId() + "  Feature Required: " + session.getFeatureIds() + " Feature Available: " + room.getFeatureIds());
                    //assertTrue(room.getFeatureIds().contains(session.getFeatureIds()));
                }

                for (StartDate startDate : integrator.createStartDate(Integer.parseInt(session.getDuration()), session.getStartTime(), session.getWeekday())) {
                    boolean insert = (room.addSession(startDate));
                    if (!insert)
                        System.out.println("Room: " + session.getBuildingId() + " - " + session.getRoomId() + "  StartDate: " + startDate.getDay() + " Hour: " + startDate.getHour());
                    //assertTrue(insert);
                }

            }
        }
        return;
    }

    RoomTest searchRooms(String building_id, String room_id){
        for (RoomTest roomTest : roomTestList)
            if (roomTest.getBuilding_id().equals(building_id) && roomTest.getId().equals(room_id))
                return roomTest;
        return null;
    }

    private List<SessionTypeTest> generateSessionTypeTestList(List<CourseType> courseTypeList){
        List<SessionTypeTest> sessionTypeTestList = new ArrayList<>();
        for (CourseType courseType : courseTypeList)
            for (GroupType groupType : courseType.getGroup())
                for (SessionType sessionType : groupType.getSession())
                    sessionTypeTestList.add(new SessionTypeTest(Integer.parseInt(groupType.getNumberOfStudents()),groupType.getTeacher(),sessionType));
        return sessionTypeTestList;
    }

    private List<RoomTest> generateRoomTestList(List<BuildingType> buildingTypeList){
        List<RoomTest> roomTestList = new ArrayList<>();
        for (BuildingType buildingType : buildingTypeList)
            for (RoomType roomType : buildingType.getRoom())
                roomTestList.add(new RoomTest(buildingType.getId(),roomType));
        return roomTestList;
    }

    class RoomTest extends RoomType {
        Set<StartDate> startDateSet = new HashSet<>();
        String building_id;

        public RoomTest(String building_id, RoomType roomType) {
            setAvailableForAllocation(roomType.getAvailableForAllocation());
            setFeatureIds(roomType.getFeatureIds());
            setId(roomType.getId());
            setNote(roomType.getNote());
            setNumberOfPlaces(roomType.getNumberOfPlaces());
            setValue(roomType.getValue());
            this.building_id = building_id;
        }

        public String getBuilding_id() {
            return building_id;
        }

        boolean addSession(StartDate startDate){
            return startDateSet.add(startDate);
        }
    }

    class SessionTypeTest extends SessionType{

        int number_of_seats;
        String teacher;

        public SessionTypeTest(int number_of_seats, String teacher, SessionType sessionType) {
            setBuildingId(sessionType.getBuildingId());
            setDuration(sessionType.getDuration());
            setFeatureIds(sessionType.getFeatureIds());
            setRequiresBuildingId(sessionType.getRequiresBuildingId());
            setRequiresRoomId(sessionType.getRequiresRoomId());
            setRoomId(sessionType.getRoomId());
            setStartTime(sessionType.getStartTime());
            setValue(sessionType.getValue());
            setWeekday(sessionType.getWeekday());
            this.number_of_seats = number_of_seats;
            this.teacher = teacher;
        }

        public int getNumber_of_seats() {
            return number_of_seats;
        }

        public String getTeacher() {
            return teacher;
        }

    }

}