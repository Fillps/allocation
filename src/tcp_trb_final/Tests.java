package tcp_trb_final;

import tcp_trb_final.allocator.Requirement;
import tcp_trb_final.room_allocations.StartDate;
import tcp_trb_final.room_allocations.Teacher;
import tcp_trb_final.room_xml.Parser;
import tcp_trb_final.allocator.Allocator;
import tcp_trb_final.allocator.ToAllocate;
import tcp_trb_final.room_xml.AllocationType;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filip on 24/05/2017.
 */
public class Tests {

    public static void importXML(){
        String path = "resources\\DemandasRecursos.xml";
        String resultPath = "resources\\resultados.xml";
        try {
            AllocationType allocationType = Parser.importFile(path);

            allocationType.getCourses().getCourse().get(0).getGroup().get(0).getSession().get(0).setRoomId("111");
            System.out.println(allocationType.getCourses().getCourse().get(0).getId());
            Parser.marshall(allocationType, resultPath);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void allocatorTest1_0(){
        Requirement startDate1 = new StartDate("4", "13:30");
        Requirement startDate2 = new StartDate("4", "13:30");
        Requirement startDate3 = new StartDate("5", "13:30");
        Requirement startDate4 = new StartDate("4", "15:30");

        System.out.print(startDate1.verify(startDate2) + " | " + startDate1.verify(startDate3) + " | " + startDate1.verify(startDate4) );
    }

    public static void allocatorTest2_0(){
        Requirement startDate1 = new StartDate("2", "13:30");
        Requirement startDate2 = new StartDate("4", "13:30");
        Requirement startDate3 = new StartDate("2", "13:30");
        Requirement startDate4 = new StartDate("5", "15:30");

        Requirement teacher1 = new Teacher("JOAO", (StartDate) startDate1);
        Requirement teacher2 = new Teacher("JOAO", (StartDate) startDate2);
        Requirement teacher3 = new Teacher("JOAO", (StartDate) startDate3);
        Requirement teacher4 = new Teacher("MARIA", (StartDate) startDate4);

        ToAllocate toAllocate1 = new ToAllocate();
        toAllocate1.addRequirement(startDate1);
        toAllocate1.addRequirement(startDate2);
        toAllocate1.addRequirement(teacher1);
        toAllocate1.addRequirement(teacher2);

        ToAllocate toAllocate2 = new ToAllocate();
        toAllocate2.addRequirement(startDate3);
        toAllocate2.addRequirement(startDate4);
        toAllocate2.addRequirement(teacher3);
        toAllocate2.addRequirement(teacher4);

        List<ToAllocate> toAllocateList = new ArrayList<>();
        toAllocateList.add(toAllocate1);
        toAllocateList.add(toAllocate2);

        ToAllocate available = new ToAllocate();
        available.setAnswer("teste");
        available.setId("1");

        List<ToAllocate> availableList = new ArrayList<>();
        availableList.add(available);

        Allocator allocator = new Allocator(toAllocateList, availableList);

        System.out.println(allocator.allocate().get(0).getAnswer());
        System.out.println(allocator.allocate().get(1).getAnswer());

    }

    public static void integratorTest() throws JAXBException, XMLStreamException, IOException {
        Integrator integrator = new Integrator("resources\\DemandasRecursos.xml");

        //for (CourseType courseType : allocationType.getCourses().getCourse())
            //System.out.println(courseType.getId());
        for (ToAllocate toAllocate : integrator.getToAllocateList())
            System.out.println(toAllocate.getId());

        for (ToAllocate available : integrator.getAvailableList())
            System.out.println(available.getAnswer());
        integrator.saveToFile("1");
    }

    public static void integratorAndAllocatorTest() throws JAXBException, XMLStreamException, IOException {
        Integrator integrator = new Integrator("resources\\DemandasRecursos.xml");
        integrator.saveToFile("resources\\resultados.xml");
    }
}
