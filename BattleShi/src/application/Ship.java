package application;

import javafx.scene.Parent;

/**
 * Date: 9/3/2021
 * This is class Ship with basic characteristics of ship and 3 functions which change some states of ship.
 * @author Ioanna Slakou
 * @version 1.0
 * 
 */
public class Ship extends Parent{
/**
 * String for name of the ship. 
 * It's public because it is used by other classes 
 */
public String name;
 /**
 * int value for ship type.
 * It's public because it is used by other classes 
 */
public int ship_type;
 /**
 * int value for ship space.It represents the length of the ship in the board.
 *  It's public because it is used by other classes
 */
public int ship_space;
 /**
 * int value for accuracy points.If a ship is hitted this score is added to the points of player.
 *  It's public because it is used by other classes
 */
public int apt_shot;
 /**
 * int value for bonus accuracy points. If the ship sank, this score is added to the points of player. 
 * It's public because it is used by other classes
 */
public int sinking_bonus;
 
 /**
 * int value health. It represents how many squares of the ship in the board are not hitted.
 */
private int health;
 /**
 * int value state. The ship can be Not hit, Hitted and sunken.
 *  It's public because it is used by other classes.
 */
public String state;
 
 /**
 * This is the constructor of class ship.
 * @param ship_type is type of the ship. By this argument ship is initialized with specific characteristics
 * It's public because it is used by other classes.
 */
public Ship(int ship_type) {
     this.ship_type = ship_type;
     state="Not hit"; //anepafo
  
     if(ship_type == 1) {
    	name="Carrier";
     	ship_space = 5 ;
     	apt_shot = 350 ;
     	sinking_bonus = 1000;
     	health = 5;
     }	
     else if(ship_type == 2) {
    	name="Battleship";
     	ship_space = 4 ;
     	apt_shot = 250 ;
     	sinking_bonus = 500;
     	health = 4;
     }
     else if(ship_type == 3) {
    	name="Cruiser";
    	ship_space = 3 ;
      	apt_shot = 100 ;
      	sinking_bonus = 250;
      	health = 3;
     }
     else if(ship_type == 4) {
    	 name="Submarine ";
    	ship_space = 3 ;
       	apt_shot = 100 ;
       	sinking_bonus = 0;
       	health = 3;
     }
     else if(ship_type == 5) {
    	 name="Destroyer";
     	ship_space = 2 ;
        	apt_shot = 50 ;
        	sinking_bonus = 0;
        	health = 2;
      }
	 
    
 }

 /**
 * This is a method which is called when a ship is hitted. It changes the characteristics health and state of ship.
 * It's public because it is used by other classes.
 */
public void hit() {
     health--;
     state="Hitted";
 }
 

 /**
 * This method check if a ship is sunken.
 * @return true if is not sunken, but just hitted in some rectangles or not hitted
 */
public boolean isAlive() {
     return health > 0;
 }
}
