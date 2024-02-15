import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    public static void initializeActionListeners() {
        MainLayout.pointGenerateParticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InvalidType invalidResult = invalidFields(MainLayout.pointPanel);
                if(invalidResult != null) {
                    showErrorDialog(invalidResult);
                }
                else {
                    //Parsing Input
                    Double startX = Double.parseDouble(MainLayout.pointStartXField.getText());
                    Double endX = Double.parseDouble(MainLayout.pointEndXField.getText());
                    Double startY = 720 - Double.parseDouble(MainLayout.pointStartYField.getText());
                    Double endY = 720 - Double.parseDouble(MainLayout.pointEndYField.getText());
                    int numParticles = Integer.parseInt(MainLayout.pointNumField.getText());
                    Double velocity = Double.parseDouble(MainLayout.pointVelocityField.getText());
                    Double angle = Double.parseDouble(MainLayout.pointAngleField.getText());

                    System.out.println(startX);
                    System.out.println(endX);
                    System.out.println(startY);
                    System.out.println(endY);

                    //Get Distance from start and end
                    double distanceX = Math.abs(startX - endX);
                    double distanceY = Math.abs(startY - endY);

                    //Get increment
                    Double incrementX = distanceX / (numParticles + 1);
                    Double incrementY = distanceY / (numParticles + 1);

                    System.out.println(incrementX);
                    System.out.println(incrementY);

                    //Pointer starts at start
                    double pointerX = startX + incrementX;
                    double pointerY = startY - incrementY;

                    //Apply threading here maybe? For now single threaded while testing
                    for(int x = 0;x < numParticles;x++) {
                        pointerX = Math.min(pointerX, 1270);
                        pointerY = Math.min(pointerY, 710);

                        Main.commandQueue.add(new Command(pointerX, pointerY, velocity, angle));

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
                InvalidType invalidResult = invalidFields(MainLayout.wallPanel);
                if(invalidResult != null) {
                    showErrorDialog(invalidResult);
                }
                else {
                    int startX = Integer.parseInt(MainLayout.wallStartXField.getText());
                    int startY = 720 - Integer.parseInt(MainLayout.wallStartYField.getText());
                    int endX = Integer.parseInt(MainLayout.wallEndXField.getText());
                    int endY = 720 - Integer.parseInt(MainLayout.wallEndYField.getText());

                    Main.commandQueue.add(new Command(startX, startY, endX, endY));
                    System.out.println("ADDING GENERATE_WALL COMMAND");
                }
            }
        });
    }

    /* General method to check if there are invalid inputs in panel's fields */
    public static InvalidType invalidFields(JPanel panel) {
        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField textField) {
                String val = textField.getText();
                if (val.isEmpty()) {
                    // Returns status code 1 if there is an empty field
                    return InvalidType.EMPTY_FIELD;
                }
                try {
                    double d = Integer.parseInt(val);
                } catch (NumberFormatException nfe) {
                    // Returns status code 2 if there is a non-numeric value
                    return InvalidType.NON_NUMERIC;
                }
            }
        }
        // Returns null if no invalid field found
        return null;
    }
    public static void showErrorDialog(InvalidType invalidResult) {
        if(invalidResult == InvalidType.EMPTY_FIELD) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields");
        }
        else if (invalidResult == InvalidType.NON_NUMERIC) {
            JOptionPane.showMessageDialog(null, "Please make sure you're only entering numbers.");
        }
    }
}

enum InvalidType {
    EMPTY_FIELD,
    NON_NUMERIC
}
