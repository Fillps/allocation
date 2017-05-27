package room_allocations;

import room_allocations.xml.AllocationType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Created by filip on 20/05/2017.
 */
public class Parser {

    public static AllocationType unmarshall(String path) throws JAXBException, XMLStreamException {
        JAXBContext jaxbContext = JAXBContext.newInstance(AllocationType.class);

        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(path));

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return  (AllocationType) jaxbUnmarshaller.unmarshal(xsr);
    }

    public static void marshall(AllocationType allocationType, String path) throws JAXBException {
        File file = new File(path);

        JAXBContext jaxbContext = JAXBContext.newInstance(AllocationType.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(allocationType, file);
    }
}
