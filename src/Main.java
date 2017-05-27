import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public class Main {

    public static void main(String[] args) {


        try {
            Tests.integratorAndAllocatorTest();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

}
