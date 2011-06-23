package game.view;

import java.awt.event.*;
import javax.swing.*;
import game.model.*;

//event handler
class MyEventListener
        implements Strings, ActionListener, MouseListener, MouseMotionListener {

    private GameModel gameModel; 
    private DrawingPanel drawingPanel; 
    //saved position of the mouse 
    private int mouseX, mouseY; 
    private Timer timer;

    //constructor
    public MyEventListener(GameModel gameModel, DrawingPanel drawingPanel) {
        this.gameModel = gameModel;
        this.drawingPanel = drawingPanel;
        timer = new Timer(40, this); // 40 ms = 25 fps
        timer.setActionCommand("next_time");
    }

    // begin ActionListener 
    public void actionPerformed(ActionEvent e) {
    //if the button was pushed then make a new game
        if (BUTTON_NEW_LABEL.equals(e.getActionCommand())) {
            gameModel.reset();
            drawingPanel.updateUI();
            timer.start();
            System.out.println("Game started!");
        }
        //if the timer worked then calculate next position of the objects
        if ("next_time".equals(e.getActionCommand())) {
            gameModel.nextTime();
            drawingPanel.updateUI();
            if (!GameModel.IN_PROGRESS.equals(gameModel.getState())) {
                timer.stop();
                System.out.println("Game stopped!");
            }
        }
    }

    // end ActionListener 
    // begin MouseMotionListener
    public void mouseDragged(MouseEvent e) {
        ObjectModel[] disks = gameModel.getDisks();
        double x, y, r;
        /**
        if (SwingUtilities.isLeftMouseButton(e)) {
            for (ObjectModel disk : disks) {
                if (disk.isModified() && !disk.isMarked()) { 
                    x = disk.getX() + (e.getX() - mouseX) / drawingPanel.getScale();
                    y = disk.getY(); 
                    gameModel.setPosition(disk, x, y);
                    drawingPanel.updateUI();
                    break;
                }
            }
        }*/
        //if left mouse button is pressed
        if (SwingUtilities.isLeftMouseButton(e)) { 
            for (ObjectModel disk : disks) {
            	//if disk is not marked and is being modified
                if (disk.isModified() && !disk.isMarked()) {
                    //set a new velocity
                    x = e.getX() / drawingPanel.getScale() - (GameModel.BODY);
                    y = e.getY() / drawingPanel.getScale() - (GameModel.BODY);
                    gameModel.setVelocity(disk, x - disk.getX(), y - disk.getY());
                    drawingPanel.setVelocity(disk.getX(), disk.getY(), x, y);
                    drawingPanel.updateUI();
                    break;
                }
            }
        }
        //save the mouse coordinates
        mouseX = e.getX();
        mouseY = e.getY();
    }

    
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // end MouseMoutionListener
    // begin MouseListener
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    //method that cahnges the disk to be modified if clicked on
    public void mousePressed(MouseEvent e) {
        double dx, dy, r;
        ObjectModel[] disks;
        disks = gameModel.getDisks();
        for (ObjectModel disk : disks) {
            if (disk.isMarked()) {
                continue;
            }
            dx = e.getX() / drawingPanel.getScale() - (disk.getX() + GameModel.BODY);
            dy = e.getY() / drawingPanel.getScale() - (disk.getY() + GameModel.BODY);
            r = Math.sqrt(dx * dx + dy * dy);
            if (r < GameModel.BODY) {
                disk.setModifyState(true);
                System.out.println("Disk is Modified!");
                break;
            }
        }
    }

    //method that changes the disk state to unModified
    public void mouseReleased(MouseEvent e) {
        ObjectModel[] disks = gameModel.getDisks();
        for (ObjectModel disk : disks) {
            if (disk.isModified()) {
                disk.setModifyState(false);
                drawingPanel.setVelocity(0, 0, 0, 0);
                System.out.println("Disk isn't Modified now!");
                break;
            }
        }
    }
    // end MouseListener
}
