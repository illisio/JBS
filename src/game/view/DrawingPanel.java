package game.view;

import java.awt.*;
import javax.swing.*;
import game.model.*;

//class that represents the playing field of the game
class DrawingPanel extends JPanel {
	
	
	//model of the game
    private GameModel gameModel;
    private Color squareColor = Color.GRAY;
    private Color jewelColor = Color.YELLOW; 
    private Color diskColor = Color.GREEN;
    private Color velocityColor = Color.BLACK;
    //scaling factor so the playing window would fit the screen
    private double scale;
    private double velocityX_begin, velocityY_begin, velocityX_end, velocityY_end;
    private int body; 
    
    //constructor
    public DrawingPanel(GameModel gameModel) {
        super();
        this.gameModel = gameModel;
    }

    //method that draws the game
    public void paintComponent(Graphics g) {
        
    
       // ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);
        //if the game is in the state IN_PROGRESS calculate the scale and draw the field
        if (GameModel.IN_PROGRESS.equals(gameModel.getState())) {
            double scaleX = (this.getWidth() - 2 * body) / gameModel.FIELD_WIDTH;
            double scaleY = (this.getHeight() - 2 * body) / gameModel.FIELD_HEIGHT;
            scale = scaleX < scaleY ? scaleX : scaleY;
            body = (int) (scale * GameModel.BODY);
            drawObjects(g);
        } else { 
            drawState(g);
        }
    }

    //method that draws the objects
    private void drawObjects(Graphics g) {
        ObjectModel[] disks = gameModel.getDisks();
        ObjectModel[] squares = gameModel.getSquares();
        ObjectModel[] jewels = gameModel.getJewels();
        int x, y;
        Polygon p = new Polygon();
        g.setColor(squareColor);
        for (ObjectModel square : squares) {
            x = (int) (scale * square.getX() + body);
            y = (int) (scale * square.getY() + body);
            g.fillRect(x - body, y - body, 2 * body, 2 * body);
        }
        g.setColor(jewelColor);
        for (ObjectModel jewel : jewels) {
            if (jewel.isMarked()) {
                continue;
            }
            x = (int) (scale * jewel.getX() + body);
            y = (int) (scale * jewel.getY() + body);
            p.reset();
            p.addPoint(x - body, y);
            p.addPoint(x, y - body);
            p.addPoint(x + body, y);
            p.addPoint(x, y + body);
            g.fillPolygon(p);
        }
        g.setColor(diskColor);
        for (ObjectModel disk : disks) {
            x = (int) (scale * disk.getX() + body);
            y = (int) (scale * disk.getY() + body);
            g.fillOval(x - body, y - body, 2 * body, 2 * body);
        }
        g.setColor(velocityColor);
        g.drawLine((int) (scale * velocityX_begin) + body,
                (int) (scale * velocityY_begin) + body,
                (int) (scale * velocityX_end) + body,
                (int) (scale * velocityY_end) + body);
    }

    //method that draws the string which represents tha games state
    private void drawState(Graphics g) {
        String str;
        int offset;
        str = String.format("Game state: %s", gameModel.getState());
        offset = g.getFontMetrics().stringWidth(str) / 2;
        g.drawString(str,
                g.getClipBounds().width / 2 - offset,
                g.getClipBounds().height - 40);
    }

    //method that sets the begining and end of the velocity vector
    public void setVelocity(double beginX, double beginY, double endX, double endY) {
        velocityX_begin = beginX;
        velocityY_begin = beginY;
        velocityX_end = endX;
        velocityY_end = endY;
    }

    public double getScale() {
        return scale;
    }
}
