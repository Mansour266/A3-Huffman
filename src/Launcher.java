import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Launcher {
    public static void main(String[] args) {
        // Run this program on the Event Dispatch Thread (EDT)
        EventQueue.invokeLater(() -> {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                             | UnsupportedLookAndFeelException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                }
            }
            new MainFrame();
        });
    }
}
