package game.model;

//class that represents the model of a object
public class ObjectModel {

	//Coordinates of the object
	private double x, y; 
	//speed vectors
	private double velocityX, velocityY; 
	private boolean isMarked; 
	//if true the object is being modified
	private boolean isModified;
   
	//constructor
   protected ObjectModel(double x, double y) {
      this.x = x;
      this.y = y;
      isMarked = false;
   }
   
   //returns the x coordinate
   public double getX() {
      return x;
   }
   
   //sets the x coordinate
   public void setX(double x) {
      this.x = x;
   }

   //gets the y coordinate
   public double getY() {
      return y;
   }

   //sets the y coordinate
   public void setY(double y) {
      this.y = y;
   }

   //returns if the object has been marked or not
   public boolean isMarked() {
      return isMarked;
   }
   
   //returns the vector velocity for X axis 
   public double getVelocityX(){
      return velocityX;
   }
   
   //sets the X axis vector velocity
   public void setVelocityX(double x) {
      velocityX = x;
   }
   
   //returns the vector velocity for Y axis
   public double getVelocityY(){
      return velocityY;
   }
   
   //sets the vector velocity for Y axis
   public void setVelocityY(double y) {
      velocityY = y;
   }
   
   //marks the object
   public void mark() {
      isMarked = true;
   }
   
   //method that returns if the object is being modified or not
   public boolean isModified() {
      return isModified;
   }
   
   //method that sets the state for the object
   public void setModifyState(boolean isModified) {
      this.isModified = isModified;
   }
}

