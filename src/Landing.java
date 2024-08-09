import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Landing implements ActionListener {
    JFrame frame = new JFrame();
    JMenuBar mb = new JMenuBar();
    JMenu fileMenu, editMenu, aboutMenu;
    JMenuItem item1, item2, item3, 
            item4, item5, item6,
            item7, item8, item9,
            item10, item11; 
            
    JTextArea textarea = new JTextArea();
    JScrollPane editorScrollPane = new JScrollPane(textarea);

    
    
    
    Landing() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setTitle("Isaac's Text Editor");
        frame.setSize(1024,726);
        Image icon = new ImageIcon(this.getClass().getResource("./assets/scroll.png")).getImage();
        frame.setIconImage(icon);
        
        // Menu Bar 
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        aboutMenu = new JMenu("Help");

        item1 = new JMenuItem("New");
        item1.addActionListener(this);
        item2 = new JMenuItem("New window");
        item2.addActionListener(this);
        item3 = new JMenuItem("Save");
        item3.addActionListener(this);
        item4 = new JMenuItem("Save As");
        item5 = new JMenuItem("Open file");
        item5.addActionListener(this);
        item6 = new JMenuItem("Print");
        item7 = new JMenuItem("Copy");
        item8 = new JMenuItem("Cut");
        item9 = new JMenuItem("Paste");
        item10 = new JMenuItem("Fonts...");
        item11 = new JMenuItem("About Isaac's Text Editor");
        item11.addActionListener(this);

        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);
        fileMenu.add(item5);
        fileMenu.addSeparator();
        fileMenu.add(item6);

        editMenu.add(item7);
        editMenu.add(item8);
        editMenu.add(item9);
        editMenu.addSeparator();
        editMenu.add(item10);

        aboutMenu.add(item11);

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(aboutMenu);

        // Text Field
        textarea.setEditable(true);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(250,145));
        editorScrollPane.setMinimumSize(new Dimension(10,10));
        textarea.setLineWrap(true);
        textarea.setMargin(new Insets(2, 5, 2, 5));
        textarea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        frame.setJMenuBar(mb);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(editorScrollPane);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to close this window?",
                    "Confirm Close",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    frame.dispose(); // Close the current window
                }
            }
        });
      
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == item3) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.showSaveDialog(fileChooser);

            File file = fileChooser.getSelectedFile();
            
            try {
                FileWriter fileWriter = new FileWriter(file);
                String text = textarea.getText();
                fileWriter.write(text);
                fileWriter.close();
                String filename = fileChooser.getSelectedFile().getName();
                frame.setTitle(filename + " - Isaac's Text Editor");
            } catch(IOException exception) {
                System.out.println(exception);
            }
        } else if(e.getSource() == item5) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.showOpenDialog(fileChooser);            
            File file = fileChooser.getSelectedFile();

            try {
                BufferedReader bufferReader = new BufferedReader(new FileReader(file));
                textarea.setText("");
                String line;
                while((line = bufferReader.readLine()) !=null) {
                    textarea.append(line + "\n");
                }
                bufferReader.close();
                String filename = file.getName();
                frame.setTitle(filename + " - Isaac's Text Editor");
            } catch (IOException exception) {
                System.out.println(exception);
            }
        } else if(e.getSource() == item2) {
            SwingUtilities.invokeLater(() -> new Landing());
        } else if(e.getSource() == item1) {
            textarea.setText("");
            frame.setTitle("New Document - Isaac's Text Editor");
        } else if(e.getSource() == item11) {
            JOptionPane.showMessageDialog(frame,"The Text Editor is still in development", "About Isaac's Text Editor", JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
