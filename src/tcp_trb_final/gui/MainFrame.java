package tcp_trb_final.gui;

import tcp_trb_final.Integrator;
import tcp_trb_final.new_allocator.NewAllocatorAdapter;
import tcp_trb_final.room_xml.AllocationType;
import tcp_trb_final.room_xml.Parser;

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
        fileChooser.setFileFilter(new FileNameExtensionFilter(".xml, .xlsx", "xml","xlsx"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                if (JOptionPane.showConfirmDialog(null, "Deseja importar no alocador do outro grupo?", "Alocar",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    AllocationType allocationType = Parser.importFile(fileChooser.getSelectedFile().getAbsolutePath());
                    NewAllocatorAdapter newAllocatorAdapter = new NewAllocatorAdapter(allocationType);
                    newAllocatorAdapter.allocate();
                    newAllocatorAdapter.save(fileChooser.getSelectedFile().getParent() + "/resultadosOutroAlocador.xml");
                } else {
                    Integrator integrator = new Integrator(fileChooser.getSelectedFile().getAbsolutePath());
                    integrator.saveToFile(fileChooser.getSelectedFile().getParent() + "/resultados.xml");
                }
                JOptionPane.showMessageDialog(MainFrame.this, "Resultados importados para resultados.xml");
            } catch (JAXBException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(MainFrame.this, "Erro ao ler o XML","Erro",JOptionPane.ERROR_MESSAGE);
            } catch (XMLStreamException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(MainFrame.this, "Erro ao ler o XML","Erro",JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Erro ao ler o arquivo","Erro",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            System.exit(0);

        }
    }
}
