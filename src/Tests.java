import allocator.Requirement;
import room_allocations.StartDate;
import room_allocations.xml.AllocationType;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

/**
 * Created by filip on 24/05/2017.
 */
public class Tests {

    public static void importXML(){
        String path = "resources\\DemandasRecursos.xml";
        String resultPath = "resources\\resultados.xml";
        try {
            AllocationType allocationType = Parser.unmarshall(path);

            allocationType.getCourses().getCourse().get(0).getGroup().get(0).getSession().get(0).setRoomId("111");
            System.out.println(allocationType.getCourses().getCourse().get(0).getId());
            Parser.marshall(allocationType, resultPath);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public static void allocatorTest1_0(){
        Requirement startDate1 = new StartDate("4", "13:30");
        Requirement startDate2 = new StartDate("4", "13:30");
        Requirement startDate3 = new StartDate("5", "13:30");
        Requirement startDate4 = new StartDate("4", "15:30");

        System.out.print(startDate1.compare(startDate2) + " | " + startDate1.compare(startDate3) + " | " + startDate1.compare(startDate4) );
    }
}
