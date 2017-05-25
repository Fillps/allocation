import allocator.Allocator;
import estrutura.AllocationType;

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
        Allocator.ToAllocate class1 = new Allocator.ToAllocate();
        class1.addRequirement("alunos", "30");
        class1.addRequirement("horario", "13:30");
        class1.addRequirement("dia", "5");
        class1.addRequirement("duration", "120");

        Allocator.Available room1 = new Allocator.Available();
        room1.setId("43425-108");
        room1.addRequirement("alunos", "60");
        room1.addRequirement("feature", "5");
        room1.addRequirement("feature", "8");

    }
}
