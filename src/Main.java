import javax.swing.*;

/**
 * Author: Benjamin Baird
 * Created on: 2016-11-04
 * Last Updated on: 2016-11-05
 * Filename: NodePackage.java
 * Description: Main class to execute the GUI and setup the styling of it.
 **/
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
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Graph.createAndShowGUI();
            }
        });

    }
}
