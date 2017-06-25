import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public class Main {

    public static void main(String[] args) {

        System.out.println("Inicio");
        try {
            Tests.integratorAndAllocatorTest();
            return;
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

}
