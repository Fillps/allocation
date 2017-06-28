package gui;

import room_allocations.Integrator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;


/**
 * Created by filip on 27/06/2017.
 */
public class MainFrame extends JFrame {
    public MainFrame(){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Escolha o arquivo a ser importado");
        fileChooser.setFileFilter(new FileNameExtensionFilter(".xml", "xml"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                Integrator integrator = new Integrator(fileChooser.getSelectedFile().getAbsolutePath());
                integrator.saveToFile(fileChooser.getSelectedFile().getParent() + "/resultados.xml");
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }

        }
    }
}
