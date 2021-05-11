import javax.swing.*;
import java.awt.*;

public class CLIMenuGUI extends JFrame{
    private JPanel mainPanel;
    private JLabel navTitle;
    private JPanel navBar;
    private JPanel navTitleWrapper;
    private JPanel navLinksWrapper;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    public CLIMenuGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new CLIMenuGUI();
                frame.setVisible(true);
            }
        });


    }
}
