import javax.swing.*;

/**
 * Created by ben on 2016-11-03.
 */
public class Main {
    public static void main (String []args) {
        // Setup the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Graph.createAndShowGUI();
            }
        });
    }
}
