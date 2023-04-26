import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class RecursiveListerGUI extends JFrame {
    JPanel mainPnl;
    JPanel displayPnl;
    JPanel optionPnl;

    JLabel titleLbl;

    JTextArea displayTA;
    JScrollPane scroller;

    JButton startBtn;
    JButton quitBtn;

    public RecursiveListerGUI() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createDisplayPanel();
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        createOptionPanel();
        mainPnl.add(optionPnl, BorderLayout.SOUTH);

        add(mainPnl);
        setSize(600, 600);
        setTitle("Recursive Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createDisplayPanel() {
        displayPnl = new JPanel();
        displayPnl.setLayout(new BorderLayout());

        titleLbl = new JLabel("Recursive Filelister");
        displayTA = new JTextArea();
        displayTA.setEditable(false);
        scroller = new JScrollPane(displayTA);

        displayPnl.add(titleLbl, BorderLayout.NORTH);
        displayPnl.add(scroller, BorderLayout.CENTER);
    }

    public void createOptionPanel() {
        optionPnl = new JPanel();
        optionPnl.setLayout(new GridLayout(1,2));

        startBtn = new JButton("Start");
        quitBtn = new JButton("Quit");

        startBtn.addActionListener((ActionEvent ae) -> chooseDirectory());
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        optionPnl.add(startBtn);
        optionPnl.add(quitBtn);
    }
    // choose a directory and lists directory contents
    public void chooseDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File chosenDir = chooser.getSelectedFile();
            displayTA.setText("Chosen Directory: "+chosenDir+"\n\n");
            displayTA.append(chosenDir+"\n");
            listDirectory(chosenDir, 1);
        }
        else {
            displayTA.append("Error: File not found.");
        }
    }
    // list file names within a directory as well as subdirectories
    private void listDirectory(File directory, int indentLevel) {
        File[] contents = directory.listFiles();

        if (contents != null) {
            // list files in a directory first
            for(File c : contents) {
                if (c.isFile()) {
                    for (int i = 0; i < indentLevel; i++) {
                        displayTA.append("    ");
                    }
                    displayTA.append(c + "\n");
                }
            }
            // list subdirectories second
            for(File c : contents) {
                if (c.isDirectory()) {
                    for(int i = 0; i < indentLevel; i++) {
                        displayTA.append("    ");
                    }
                    displayTA.append(c + "\n");

                    if (c.isDirectory()) {
                        listDirectory(c, indentLevel + 1);
                    }
                }
            }
        }
    }
}
