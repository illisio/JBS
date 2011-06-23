package game.model;

import java.util.Collection;
import java.util.LinkedList;

//class that represents the model
public class GameModel {

   //public constant describing the state of the game
    public static final String IN_PROGRESS = "In progress",
            GAME_LOST = "Game over (lost)",
            GAME_WON = "Game over (won)";
    //width of the field
    public static final double FIELD_WIDTH = 1.0;
    //height
    public static final double FIELD_HEIGHT = 1.0;
   
    //constant that is the radius of a figure
    public static final double BODY = 0.05;
    // collections of objects such as squares, jewels and disks
    private Collection<ObjectModel> squares, disks, jewels;
    private String state; 
    private static final double TIME_STEP = 0.001; 
    //gravity constant
    private static final double G = -9.81; 
    private static final double VELOCITY_FACTOR = 10.0;

    //constructor
    public GameModel() {
        reset();
    }

    //method that resets the game to its initial state
    public void reset() {
        squares = new LinkedList<ObjectModel>();
        disks = new LinkedList<ObjectModel>();
        jewels = new LinkedList<ObjectModel>();
        generateInitialState();
        state = IN_PROGRESS;
    }

    //method that generates the game field 
    private void generateInitialState() {
        /*      squares.add(new ObjectModel(0.2, 0.2));
        squares.add(new ObjectModel(0.4, 0.3));
        disks.add(new ObjectModel(0.01, 0.01));
        disks.add(new ObjectModel(0.8, 0.1));
        disks.add(new ObjectModel(0.9, 0.9));
        jewels.add(new ObjectModel(0.4, 0.8));
        jewels.add(new ObjectModel(0.7, 0.7));
         */
        int squaresAmount = (int) (Math.random() * 10) + 1;
        int disksAmount = (int) (Math.random() * 4) + 1;
        int jewelsAmount = (int) (Math.random() * 10) + 1;
        int i;
        for (i = 0; i < squaresAmount; i++) {
            squares.add(new ObjectModel(Math.random(), Math.random()));
        }
        for (i = 0; i < disksAmount; i++) {
            disks.add(new ObjectModel(Math.random(), Math.random()));
        }
        for (i = 0; i < jewelsAmount; i++) {
            jewels.add(new ObjectModel(Math.random(), Math.random()));
        }
    }

    //method that returns the state of the game
    public String getState() {
        return state;
    }

    //returns the object squares
    public ObjectModel[] getSquares() {
        return squares.toArray(new ObjectModel[0]);
    }

    //return the array of object disks
    public ObjectModel[] getDisks() {
        return disks.toArray(new ObjectModel[0]);
    }

    //returns array of object jewels
    public ObjectModel[] getJewels() {
        return jewels.toArray(new ObjectModel[0]);
    }

    //method that checks if the player has won
    private boolean hasWon() {
        boolean hasntJewel = true;
        for (ObjectModel jewel : jewels) {
            if (!jewel.isMarked()) {
                hasntJewel = false;
                break;
            }
        }
        return hasntJewel;
    }

    //method that checks if the player lost
    private boolean hasLost() {
        boolean hasntDisk = true;
        for (ObjectModel disk : disks) {
            if (!disk.isMarked()) {
                hasntDisk = false;
                break;
            }
        }
        return hasntDisk;
    }

    //calculating the next position of the object
    //using formulas X = X0 + V0 * T + G * T^2 / 2 and Vy = Vy0 - G * T 
    private void nextPosition(ObjectModel o) {
        o.setX(o.getX() + o.getVelocityX() * TIME_STEP + G * TIME_STEP * TIME_STEP / 2.0);
        o.setY(o.getY() + o.getVelocityY() * TIME_STEP + G * TIME_STEP * TIME_STEP / 2.0);
        o.setVelocityY(o.getVelocityY() - G * TIME_STEP);
    }

    //method that changes the state of  object if it touches the borders of the field
    private void touchBound(ObjectModel o) {
        if (o.getX() < 0 || o.getX() > FIELD_WIDTH
                || o.getY() < 0 || o.getY() > FIELD_HEIGHT) {
            o.mark();
            System.out.println("STOP: border");
        }
    }

    //changes the state of objects if they touch a disk
    private void touchDisk(ObjectModel disk) {
        double dx, dy, r;
        for (ObjectModel d : disks) {
            if (!d.isMarked()) {
                continue;
            }
            dx = Math.abs(d.getX() - disk.getX());
            dy = Math.abs(d.getY() - disk.getY());
            r = Math.sqrt(dx * dx + dy * dy);
            if (r < 2 * BODY) {
                disk.mark();
                System.out.println("STOP: marked disk.");
            }
        }
    }

    //changes the state of objects if they touch a square
    private void touchSquare(ObjectModel disk) {
        double dx, dy, r;
        for (ObjectModel square : squares) {
            dx = Math.abs(square.getX() - disk.getX());
            dy = Math.abs(square.getY() - disk.getY());
            r = Math.sqrt(dx * dx + dy * dy);
            if (r < 2 * BODY) { // приблизительно, как окружности
                disk.mark();
                System.out.println("STOP: square");
            }
        }
    }

    //method that changes the state of the object if it touches a jewel
    private void touchJewel(ObjectModel disk) {
        double dx, dy;
        for (ObjectModel jewel : jewels) {
            if (jewel.isMarked()) {
                continue;
            }
            dx = Math.abs(jewel.getX() - disk.getX());
            dy = Math.abs(jewel.getY() - disk.getY());
            if (dx + dy < 2 * BODY) {
                jewel.mark();
                System.out.println("STOP: jewel!");
            }
        }
    }

    //method that calculates the next position in time for disks
    public void nextTime() {
        for (ObjectModel disk : disks) {
            if (disk.isMarked() || disk.isModified()) {
                continue;
            }
            nextPosition(disk);
            touchBound(disk);
            touchDisk(disk);
            touchSquare(disk);
            touchJewel(disk);
        }
        if (hasWon()) {
            state = GAME_WON;
        }
        if (hasLost()) {
            state = GAME_LOST;
        }
    }

    //method that allows to move selected object to a set position
    public void setPosition(ObjectModel disk, double x, double y) {
        disk.setX(x);
        disk.setY(y);
        disk.setVelocityX(0.0);
        disk.setVelocityY(0.0);
        touchBound(disk);
        touchDisk(disk);
        touchSquare(disk);
        touchJewel(disk);
    }

    //method that sets a new velocity vector 
    public void setVelocity(ObjectModel disk, double velocityX, double velocityY) {
        disk.setVelocityX(velocityX * VELOCITY_FACTOR);
        disk.setVelocityY(velocityY * VELOCITY_FACTOR);
    }
}
