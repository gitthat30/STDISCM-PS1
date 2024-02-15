import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    public static void initializeActionListeners() {
        MainLayout.pointGenerateParticleButton.addActionListener(e -> {
            InvalidType invalidResult = invalidFields(MainLayout.pointPanel);
            if(invalidResult != null) {
                showErrorDialog(invalidResult);
            }
            else {
                //Parsing Input
                double startX = Double.parseDouble(MainLayout.pointStartXField.getText());
                double endX = Double.parseDouble(MainLayout.pointEndXField.getText());
                double startY = 720 - Double.parseDouble(MainLayout.pointStartYField.getText());
                double endY = 720 - Double.parseDouble(MainLayout.pointEndYField.getText());
                int numParticles = Integer.parseInt(MainLayout.pointNumField.getText());
                Double velocity = Double.parseDouble(MainLayout.pointVelocityField.getText());
                Double angle = Double.parseDouble(MainLayout.pointAngleField.getText());

                //Get Distance from start and end
                double distanceX = Math.abs(startX - endX);
                double distanceY = Math.abs(startY - endY);

                //Get increment
                double incrementX = distanceX / (numParticles + 1);
                double incrementY = distanceY / (numParticles + 1);

                //Pointer starts at start
                double pointerX = startX + incrementX;
                double pointerY = startY - incrementY;

                for(int x = 0;x < numParticles;x++) {
                    pointerX = Math.min(pointerX, 1270);
                    pointerY = Math.min(pointerY, 710);

                    Main.commandQueue.add(new Command(pointerX, pointerY, velocity, angle));

                    pointerX += incrementX;
                    pointerY -= incrementY;
                }
            }

        });

        MainLayout.angleGenerateParticleButton.addActionListener(e -> {
            InvalidType invalidResult = invalidFields(MainLayout.anglePanel);
            if(invalidResult != null) {
                showErrorDialog(invalidResult);
            }
            else {
                double angleStart = Double.parseDouble(MainLayout.angleStartAngleField.getText());
                double angleEnd = Double.parseDouble(MainLayout.angleEndAngleField.getText());
                double x = Double.parseDouble(MainLayout.angleStartXField.getText());
                double y = 720 - Double.parseDouble(MainLayout.angleStartYField.getText());
                double velocity = Double.parseDouble(MainLayout.angleVelocityField.getText());

                int numParticles = Integer.parseInt(MainLayout.angleNumField.getText());
                double increment = (angleEnd - angleStart) / numParticles;

                for(int i = 0; i < numParticles; i++) {
                    double newAngle = angleStart + (i * increment);
                    Main.commandQueue.add(new Command(x, y, velocity, newAngle));
                }
            }
        });

        MainLayout.velocityGenerateParticleButton.addActionListener(e -> {
            InvalidType invalidResult = invalidFields(MainLayout.velocityPanel);
            if(invalidResult != null) {
                showErrorDialog(invalidResult);
            }
            else {
                double velocityStart = Double.parseDouble(MainLayout.velocityStartVelocityField.getText());
                double velocityEnd = Double.parseDouble(MainLayout.velocityEndVelocityField.getText());
                double x = Double.parseDouble(MainLayout.velocityStartXField.getText());
                double y = 720 - Double.parseDouble(MainLayout.velocityStartYField.getText());
                double angle = Double.parseDouble(MainLayout.velocityAngleField.getText());

                int numParticles = Integer.parseInt(MainLayout.velocityNumField.getText());
                double increment = (velocityEnd - velocityStart) / numParticles;

                for(int i = 0; i < numParticles; i++) {
                    double newVelocity = velocityStart + (i * increment);
                    System.out.println(newVelocity);
                    Main.commandQueue.add(new Command(x, y, newVelocity, angle));
                }
            }
        });

        MainLayout.wallGenerateWallButton.addActionListener(e -> {
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
