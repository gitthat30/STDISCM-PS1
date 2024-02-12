import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLayout {
    static JFrame MainFrame = new JFrame();

    static JPanel MainPanel = new JPanel();

    //Panels
    static ParticleArea particlePanel = new ParticleArea();

    //This is where the JPanel containing the UI for adding particles is
    static Dimension particlePanelDimension = new Dimension(300, 200);

    static JPanel particleGenerationContainer = new JPanel();
    static JLabel particleGenerationLabel = new JLabel("Particle Generation");

    //Adding from point
    static JPanel pointPanel = new JPanel();
    static JLabel pointLabel = new JLabel("Create Particles between Start and End points");
    static JLabel pointLabelFiller = new JLabel("");
    static JLabel pointNumText = new JLabel("Number of Particles");
    static JTextField pointNumField = new JTextField();
    static JLabel pointStartXText = new JLabel("Start X: ");
    static JTextField pointStartXField = new JTextField();
    static JLabel pointStartYText = new JLabel("Start Y: ");
    static JTextField pointStartYField = new JTextField();
    static JLabel pointEndXText = new JLabel("End X: ");
    static JTextField pointEndXField = new JTextField();
    static JLabel pointEndYText = new JLabel("End Y: ");
    static JTextField pointEndYField = new JTextField();
    static JButton pointGenerateParticleButton = new JButton("Generate Particle(s)");

    //Adding from angle
    static JPanel anglePanel = new JPanel();
    static JLabel angleLabel = new JLabel("Create Particles between Start and End angles");
    static JLabel angleNumText = new JLabel("Number of Particles");
    static JTextField angleNumField = new JTextField();
    static JLabel angleStartAngleText = new JLabel("Start Angle: ");
    static JTextField angleStartAngleField = new JTextField();
    static JLabel angleEndAngleText = new JLabel("End Angle: ");
    static JTextField angleEndAngleField = new JTextField();
    static JButton angleGenerateParticleButton = new JButton("Generate Particle(s)");

    //Adding from Velocities
    static JPanel velocityPanel = new JPanel();
    static JLabel velocityLabel = new JLabel("Create Particles with Velocities");
    static JLabel velocityNumText = new JLabel("Number of Particles");
    static JTextField velocityNumField = new JTextField();
    static JLabel velocityStartVelocityText = new JLabel("Start Velocity: ");
    static JTextField velocityStartVelocityField = new JTextField();
    static JLabel velocityEndVelocityText = new JLabel("End Velocity: ");
    static JTextField velocityEndVelocityField = new JTextField();
    static JButton velocityGenerateParticleButton = new JButton("Generate Particle(s)");

    //Adding Walls
    static JPanel wallPanel = new JPanel();
    static JLabel wallLabel = new JLabel("Wall Generation");
    static JLabel wallStartXText = new JLabel("Start X: ");
    static JTextField wallStartXField = new JTextField();
    static JLabel wallStartYText = new JLabel("Start Y: ");
    static JTextField wallStartYField = new JTextField();
    static JLabel wallEndXText = new JLabel("End X: ");
    static JTextField wallEndXField = new JTextField();
    static JLabel wallEndYText = new JLabel("End Y: ");
    static JTextField wallEndYField = new JTextField();
    static JButton wallGenerateWallButton = new JButton("Generate Wall");

    //For the grid layouts. Since the Panels for adding particles are of dimension (7,2) there are times when you only have one component on one row, so this is to add a filler label for those cases.
    public static void addFiller(JPanel panel) {
        JLabel filler = new JLabel();
        panel.add(filler);
    }

    public static void initializeAnglePanel() {
        anglePanel.setLayout(new GridLayout(7, 2));
        anglePanel.setPreferredSize(particlePanelDimension);

        anglePanel.add(angleLabel);
        addFiller(anglePanel);

        anglePanel.add(angleNumText);
        anglePanel.add(angleNumField);

        anglePanel.add(angleStartAngleText);
        anglePanel.add(angleStartAngleField);

        anglePanel.add(angleEndAngleText);
        anglePanel.add(angleEndAngleField);

        addFiller(anglePanel);
        anglePanel.add(angleGenerateParticleButton);
    }

    public static void initializePointPanel() {
        pointPanel.setLayout(new GridLayout(7, 2));
        pointPanel.setPreferredSize(new Dimension(particlePanelDimension));

        pointPanel.add(pointLabel);
        addFiller(pointPanel);

        pointPanel.add(pointNumText);
        pointPanel.add(pointNumField);

        pointPanel.add(pointStartXText);
        pointPanel.add(pointStartXField);

        pointPanel.add(pointStartYText);
        pointPanel.add(pointStartYField);

        pointPanel.add(pointEndXText);
        pointPanel.add(pointEndXField);

        pointPanel.add(pointEndYText);
        pointPanel.add(pointEndYField);

        addFiller(pointPanel);
        pointPanel.add(pointGenerateParticleButton);

    }

    public static void initializeVelocityPanel() {
        velocityPanel.setLayout(new GridLayout(7, 2));
        velocityPanel.setPreferredSize(new Dimension(particlePanelDimension));

        velocityPanel.add(velocityLabel);
        addFiller(velocityPanel);

        velocityPanel.add(velocityNumText);
        velocityPanel.add(velocityNumField);

        velocityPanel.add(velocityStartVelocityText);
        velocityPanel.add(velocityStartVelocityField);

        velocityPanel.add(velocityEndVelocityText);
        velocityPanel.add(velocityEndVelocityField);

        addFiller(velocityPanel);
        velocityPanel.add(velocityGenerateParticleButton);
    }

    public static void initializeWallPanel() {
        wallPanel.setLayout(new GridLayout(5, 2));
        wallPanel.setPreferredSize(new Dimension(800, 80));

        wallPanel.add(wallStartXText);
        wallPanel.add(wallStartXField);

        wallPanel.add(wallStartYText);
        wallPanel.add(wallStartYField);

        wallPanel.add(wallEndXText);
        wallPanel.add(wallEndXField);

        wallPanel.add(wallEndYText);
        wallPanel.add(wallEndYField);

        addFiller(wallPanel);
        wallPanel.add(wallGenerateWallButton);
    }

    public static void newRow() {
        JLabel spacerLabel = new JLabel();
        spacerLabel.setPreferredSize(new Dimension(9999, 0));
        MainPanel.add(spacerLabel);
    }

    public static Timer createTimer(int t, ActionListener r) {
        Timer tempTimer = new Timer(t, r);
        tempTimer.setRepeats(false);
        return tempTimer;
    }

    public static void initializeGUI() {
        MainFrame.setPreferredSize(new Dimension(1920,1080));
        MainPanel.setPreferredSize(new Dimension(1920,1080));
        MainPanel.setLayout(new FlowLayout());

        MainPanel.add(particlePanel);
        particlePanel.setPreferredSize(new Dimension(1280,720));

        newRow();

        //Generating as a grid of 1 row and 3 columns. There are 3 ways to add particles, so that's why there are 3 columns
        particleGenerationContainer.setLayout(new GridLayout(1, 3));
        particleGenerationContainer.setPreferredSize(new Dimension(1800, 115));

        initializePointPanel();
        initializeAnglePanel();
        initializeVelocityPanel();
        initializeWallPanel();

        particleGenerationContainer.add(pointPanel);
        particleGenerationContainer.add(anglePanel);
        particleGenerationContainer.add(velocityPanel);

        MainPanel.add(particleGenerationLabel);
        newRow();

        MainPanel.add(particleGenerationContainer);
        newRow();

        MainPanel.add(wallLabel);
        newRow();

        MainPanel.add(wallPanel);

        JScrollPane scroll = new JScrollPane(MainPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        MainFrame.getContentPane().add(scroll);

        angleGenerateParticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("WOPRKING");
            }
        });

        MainFrame.pack();
    }


    public static void main(String[] args) {


        initializeGUI();

        Timer timer1 = createTimer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                particlePanel.addParticle(new Particle(0, 0));
            }
        });
        timer1.start();

        Timer timer2 = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                particlePanel.addParticle(new Particle(1000, 710));
            }
        });
        timer2.setRepeats(false);
        timer2.start();

        Timer timer3 = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                particlePanel.changeParticleLocation(0, 1270, 710);
                particlePanel.repaint();
            }
        });
        timer3.setRepeats(false);
        timer3.start();

        MainFrame.setVisible(true);

    }
}
