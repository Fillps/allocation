package tcp_trb_final.gui;

import tcp_trb_final.Integrator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;


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
                JOptionPane.showMessageDialog(MainFrame.this, "Resultados importados para resultados.xml");
            } catch (JAXBException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(MainFrame.this, "Erro ao ler o XML","Erro",JOptionPane.ERROR_MESSAGE);
            } catch (XMLStreamException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(MainFrame.this, "Erro ao ler o XML","Erro",JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);

        }
    }
}
