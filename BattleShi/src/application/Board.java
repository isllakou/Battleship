package application;


import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Board extends Parent {
    private VBox rows = new VBox();
    private boolean enemy;
    public int ships = 5;
    public int points;
    public int succ_shots=0;
    public Board(boolean enemy) {
        this.enemy =enemy;
      
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                row.getChildren().add(c);
            }

            rows.getChildren().add(row);
        }
      
        
        getChildren().add(rows);
        addnumbers(rows);
    }

private void addnumbers(VBox vbox) {
	Text[] txt=new Text[10];
	for(int i=0; i<10; i++) {
		txt[i]= new Text(String.valueOf(i));
		txt[i].setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
	}
	HBox hb1 = new HBox(25,txt[0],txt[1],txt[2],txt[3],txt[4],txt[5],txt[6],txt[7],txt[8],txt[9]);
	
	hb1.setAlignment(Pos.CENTER);
	VBox hb2 = new VBox(10,hb1,vbox);
	
	getChildren().add(hb2);
	addnumbers2(hb2);
}

private void addnumbers2(VBox vbox) {
	Text[] txt=new Text[10];
	for(int i=0; i<10; i++) {
		txt[i]= new Text(String.valueOf(i));
		txt[i].setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
	}
	VBox hb1 = new VBox(19,txt[0],txt[1],txt[2],txt[3],txt[4],txt[5],txt[6],txt[7],txt[8],txt[9]);
	
	hb1.setAlignment(Pos.CENTER);
	HBox hb2 = new HBox(15,hb1,vbox);
	
	getChildren().add(hb2);
}


public void PlaceShip(Ship ship, int x, int y,int orientation) throws OversizeException,OverlapTilesException,AdjacentTilesException {
        int length = ship.ship_space;
        if (orientation==1) {
        	//cant place ship 
        	if(y+length-1>10 || x<0 || y<0 || x>10)
        		throw new OversizeException("A ship may not go beyond the limits of the dashboard.");
        	for (int i = y; i < y + length; i++) {
                Cell cell = getCell(i, x);
                if (cell.ship != null) {
                    throw new OverlapTilesException("A ship cannot be placed in a cell that already has another ship.");
                }
                for (Cell neighbor : getNeighbors(i, x)) {

                    if (neighbor.ship != null) 
                       throw new AdjacentTilesException("A ship may not be in vertical or horizontal contact with any other ship,\r\n"
                       		+ "even for a cell. This means that the ships are spaced apart\r\n"
                       		+ "at least one free cell.");                       
                }
            }
        	//not exception occurred so we can place the ship
 		   for (int i = y; i < y + length; i++) {
               Cell cell = getCell(i, x);
               cell.ship = ship;
               if (!enemy) {
                   cell.setFill(Color.WHITE);
                   cell.setStroke(Color.GREEN);
               }
           }
        	
        }
                
        else if(orientation==2) {
        	if(x+length-1>10 || x<0 || y<0 || y>10)
        		throw new OversizeException("A ship may not go beyond the limits of the dashboard.");
        	for (int i = x; i < x + length; i++) {
                Cell cell = getCell(y, i);
                if (cell.ship != null) {
                    throw new OverlapTilesException("A ship cannot be placed in a cell that already has another ship.");
                }
                for (Cell neighbor : getNeighbors(y, i)) {

                    if (neighbor.ship != null) 
                       throw new AdjacentTilesException("A ship may not be in vertical or horizontal contact with any other ship,\r\n"
                       		+ "even for a cell. This means that the ships are spaced apart\r\n"
                       		+ "at least one free cell.");                       
                }
            }

        	//not exception occurred so we can place the ship
        		   for (int i = x; i < x + length; i++) {
                       Cell cell = getCell(y, i);
                       cell.ship = ship;
                       if (!enemy) {
                           cell.setFill(Color.WHITE);
                           cell.setStroke(Color.GREEN);
                       }
                   }
        	}

        }

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    public Cell[] getNeighbors(int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Cell> neighbors = new ArrayList<Cell>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }

   

     

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    public static boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        public boolean is_shot=false;
        private Board board;

        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTBLUE);
            setStroke(Color.GREEN);
        }

        public void shoot() {
            setFill(Color.BLACK);
            if (ship != null) {
                ship.hit();
                board.succ_shots++;
                board.points=board.points+ship.apt_shot;//add points
                setFill(Color.RED);
                if (!ship.isAlive()) {//add extra points
                	ship.state="Sunken";//vithismeno
                	board.points=board.points+ship.sinking_bonus;
                    board.ships--;
                    if(board.ships==0) Main.EndGame1();
                }
            }
            is_shot=true;
        }
        
        public void enemyshoot(boolean enemy_shot) {
            setFill(Color.BLACK);
            if (ship != null) {
                ship.hit();
                board.succ_shots++;
                board.points=board.points+ship.apt_shot;//add points
                enemy_shot=true;
                setFill(Color.RED);
                is_shot=true;//prosthiki
                if (!ship.isAlive()) {//add extra points
                	ship.state="Sunken";//vithismeno
                	board.points=board.points+ship.sinking_bonus;
                    board.ships--;
                    if(board.ships==0) Main.EndGame1();
                }
            }
            else
            	enemy_shot=false;
           
        }
        
    }
}

