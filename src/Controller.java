import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    public static void initializeActionListeners() {
        MainLayout.pointGenerateParticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer invalidResult = invalidFields(MainLayout.pointPanel);
                if(invalidResult != null) {
                    showErrorDialog(invalidResult);
                }
                else {
                    //Parsing Input
                    int startX = Integer.parseInt(MainLayout.pointStartXField.getText());
                    int endX = Integer.parseInt(MainLayout.pointEndXField.getText());
                    int startY = 720 - Integer.parseInt(MainLayout.pointStartYField.getText());
                    int endY = 720 - Integer.parseInt(MainLayout.pointEndYField.getText());
                    int numParticles = Integer.parseInt(MainLayout.pointNumField.getText());
                    int velocity = Integer.parseInt(MainLayout.pointVelocityField.getText());
                    int angle = Integer.parseInt(MainLayout.pointAngleField.getText());

                    System.out.println(startX);
                    System.out.println(endX);
                    System.out.println(startY);
                    System.out.println(endY);

                    //Get Distance from start and end
                    int distanceX = Math.abs(startX - endX);
                    int distanceY = Math.abs(startY - endY);

                    //Get increment
                    int incrementX = distanceX / (numParticles + 1);
                    int incrementY = distanceY / (numParticles + 1);

                    System.out.println(incrementX);
                    System.out.println(incrementY);

                    //Pointer starts at start
                    int pointerX = startX + incrementX;
                    int pointerY = startY - incrementY;

                    //Apply threading here maybe? For now single threaded while testing
                    for(int x = 0;x < numParticles;x++) {
                        pointerX = Math.min(pointerX, 1270);
                        pointerY = Math.min(pointerY, 710);

                        MainLayout.particlePanel.addParticle(new Particle(pointerX, pointerY, velocity, angle));
                        System.out.println("X = " + pointerX);
                        System.out.println("Y = " + pointerY);
                        pointerX += incrementX;
                        pointerY -= incrementY;
                    }
                }

            }
        });

        MainLayout.wallGenerateWallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer invalidResult = invalidFields(MainLayout.wallPanel);
                if(invalidResult != null) {
                    showErrorDialog(invalidResult);
                }
                else {
                    int startX = Integer.parseInt(MainLayout.wallStartXField.getText());
                    int startY = 720 - Integer.parseInt(MainLayout.wallStartYField.getText());
                    int endX = Integer.parseInt(MainLayout.wallEndXField.getText());
                    int endY = 720 - Integer.parseInt(MainLayout.wallEndYField.getText());
                    MainLayout.particlePanel.addWall(new Wall(startX, startY, endX, endY));
                }
            }
        });
    }

    /* General method to check if there are invalid inputs in panel's fields */
    public static Integer invalidFields(JPanel panel) {
        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField textField) {
                String val = textField.getText();
                if (val.isEmpty()) {
                    // Returns status code 1 if there is an empty field
                    return 1;
                }
                try {
                    double d = Integer.parseInt(val);
                } catch (NumberFormatException nfe) {
                    // Returns status code 2 if there is a non-numeric value
                    return 2;
                }
            }
        }
        // Returns null if no invalid field found
        return null;
    }
    public static void showErrorDialog(Integer invalidResult) {
        if(invalidResult != null && invalidResult == 1) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
        }
        else if (invalidResult != null && invalidResult == 2) {
            JOptionPane.showMessageDialog(null, "Please make sure you're only entering numbers.");
        }
    }


    public static Timer createTimer(int t, ActionListener r) {
        Timer tempTimer = new Timer(t, r);
        tempTimer.setRepeats(false);
        return tempTimer;
    }
}
