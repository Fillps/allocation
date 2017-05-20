import estrutura.AllocationType;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;


public class Main {

    public static void main(String[] args) {

        String path = "D:\\Documents\\Intellij IDEA Workspace\\Alocacao de Turmas\\resources\\DemandasRecursos.xml";
        String resultPath = "D:\\Documents\\Intellij IDEA Workspace\\Alocacao de Turmas\\resources\\resultados.xml";
        try {
            AllocationType allocationType = Parser.unmarshall(path);

            Parser.marshall(allocationType, resultPath);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
