import javax.swing.*;

/**
 * Created by ben on 2016-11-03.
 */
public class Main {
    public static void main (String []args) {
        // Setup the GUI
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (Exception e) {

        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Graph.createAndShowGUI();
            }
        });

    }
}
