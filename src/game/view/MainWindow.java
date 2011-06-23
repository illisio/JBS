package game.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import game.model.GameModel;

//this class describes tha main window of the program
public class MainWindow extends JFrame implements Strings {

    private MyEventListener myEventListener;
    private GameModel gameModel; 
    private DrawingPanel drawingPanel;

    //constructor
    public MainWindow() {
        super(MAIN_WINDOW_TITLE);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationByPlatform(true);
        //super.setLocation(0, 0);
        //initializing the model of the game
        gameModel = new GameModel(); 
        //initializing the panel for drawing the game field
        this.getContentPane().add(initDrawingPanel()); 
        myEventListener = new MyEventListener(gameModel, drawingPanel); 
        drawingPanel.addMouseMotionListener(myEventListener);
        drawingPanel.addMouseListener(myEventListener);
        this.getContentPane().add(initStatusBar(), BorderLayout.SOUTH);
        this.pack();
    }

    //initializes the drawing panel for drawing the game field
    private JComponent initDrawingPanel() {
        drawingPanel = new DrawingPanel(gameModel);
        drawingPanel.setPreferredSize(new Dimension(300, 300));
        drawingPanel.setBackground(Color.white);
        return drawingPanel;
    }

    //initialisez the status bar where the button will be
    private JComponent initStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout());
        JButton bNew = new JButton(BUTTON_NEW_LABEL);

        statusBar.add(bNew);
        bNew.addActionListener(myEventListener);

        return statusBar;
    }

    //updates the drawing panel
    public void updateUI() {
        drawingPanel.updateUI();
    }
}
