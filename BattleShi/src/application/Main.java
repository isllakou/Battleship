package application;
	

import javafx.scene.Parent;
import javafx.scene.Scene;
import  javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.*;
import application.Board.Cell;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;


public class Main extends Application {
	private static Label instructions;
	private  VBox vBox;
	private static VBox game_box;
	public static boolean flag=true;
	public static Stage startStage;
	public static Stage stage2;
	public static Stage stage3;
	
	public static ReadFile enemy_scenario;
	public static ReadFile player_scenario;
	public static Ship[] player_ships = new Ship[5];
	public static Ship[] enemy_ships = new Ship[5];
	
	private static Board enemyBoard;
	private static Board playerBoard;
	
	private static int shot_x;
	private static int shot_y;
	private static TextField text_x;
	private static TextField text_y;
	
	public boolean enemy_shots=false;
	//save 5 final shots
	 private static List<Integer> enemy_savex=new ArrayList<Integer>();
	 private static List<Integer> enemy_savey=new ArrayList<Integer>();
	 private static List<Integer> player_savex=new ArrayList<Integer>();
	 private static List<Integer> player_savey=new ArrayList<Integer>();
	 //player info
	 private static Text txtplayer;
	 private static Text perc1 ;
	 private static Text score1 ;
	 private static Text pships;
	 //enemy info
	 private static Text txtenemy ;
	 private static Text perc2 ;
	 private static Text score2;
	 private static Text eships;
	 
	 private Random random = new Random();
	 private boolean enemyTurn;
	 
	 public static boolean flag_read=false;
	 
	 Cell[] cells1, cells2;
	    
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Application
	      Menu Application = new Menu("Application");
	    	      
	      MenuItem Load = new MenuItem("Load");
	      MenuItem Exit = new MenuItem("Exit");
	      MenuItem Start = new MenuItem("Start");
	      
	      Application.getItems().add(Start);
	      Application.getItems().add(Load);
	      Application.getItems().add(Exit); 
	      
	   //Details
	      Menu Details = new Menu("Details");
	      
	      MenuItem EnemyShips = new MenuItem("Enemy Ships");
	      MenuItem PlayerShots = new MenuItem("Player Shots");
	      MenuItem EnemyShots = new MenuItem("Enemy Shots");
	      
	      Details.getItems().add(EnemyShips);
	      Details.getItems().add(PlayerShots);
	      Details.getItems().add(EnemyShots);
	      
	      MenuBar menubar = new MenuBar();
	      menubar.getMenus().add(Application);
	      menubar.getMenus().add(Details);
	      vBox = new VBox(menubar);
	      
	      instructions= new Label("1.Go to Application-->Load 2.Type a number. 3.Application-->Start");
	      instructions.setWrapText(true);
	      instructions.setMaxWidth(150);
	      instructions.setAlignment(Pos.CENTER);
	      
	      
	      vBox.getChildren().add(instructions);
	      //VBox instuctions = new VBox();
	      game_box= new VBox();
	      Scene scene = new Scene(vBox,1500,800);
	      vBox.getChildren().add(game_box);
		  primaryStage.setTitle("MediaLab Battleship");
		  primaryStage.setScene(scene);
		  primaryStage.show();
		  
		  EventHandler<ActionEvent> action = openstart(vBox);
		  Start.setOnAction(action);
		  EnemyShots.setOnAction(action);
		  Load.setOnAction(action);
		  Exit.setOnAction(action);
		  EnemyShips.setOnAction(action);
		  PlayerShots.setOnAction(action);
		  
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	private EventHandler<ActionEvent> openstart(VBox vBox) {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String side = mItem.getText();
                if ("Start".equalsIgnoreCase(side)) {
                	createBoards(Main.game_box);
                }
                if("Load".equalsIgnoreCase(side)) {
                	startStage= new Stage();
                	try {
						createContent(startStage);
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                if("Exit".equalsIgnoreCase(side)){
                	Platform.exit();
                }
                
                if("Enemy Ships".equalsIgnoreCase(side)) {
                	Popup.EnemyShips();
                }
               if("Player Shots".equalsIgnoreCase(side)) {
            	   Popup.ShowLastShots(player_savex,player_savey,enemyBoard,"Player Shots");
                }
               if("Enemy Shots".equalsIgnoreCase(side)) {
                	Popup.ShowLastShots(enemy_savex,enemy_savey,playerBoard,"Enemy Shots");
                }
            }
        };
    }
	
	
	private void createContent(Stage startStage) throws Exception{
		game_box.getChildren().clear();
		enemy_savex.clear();
		enemy_savey.clear();
		player_savex.clear();
		player_savey.clear();
		instructions.setText("1.Go to Application-->Load 2.Type a number. 3.Application-->Start");
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		startStage.setTitle("Load");
		startStage.setScene(scene);
		startStage.show();
	}
    
	
	
	private Parent createBoards(VBox vBox) {
		//reset for new game
		game_box.getChildren().clear();
		enemy_savex.clear();
		enemy_savey.clear();
		player_savex.clear();
		player_savey.clear();
		txtplayer = new Text("Player:");
		perc1 = new Text("Percentage of successful shots:");
		score1 = new Text("Player Score:0");
		pships = new Text("Player's ships in board: 5");
		 //enemy info
		txtenemy = new Text("Enemy:");
		perc2 = new Text("Percentage of successful shots:");
		score2 = new Text("Enemy Score:0");
		eships = new Text("Enemy's ships in board: 5");
		
		//create boards for new game
	    BorderPane game = new BorderPane();
		game.setPrefSize(800,800);
		game_box.getChildren().add(game);
		//create Boards
		enemyBoard= new Board(true);
		playerBoard= new Board(false);
		//place ships
		for(int i=0; i<5; i++) {
          	try {
          	playerBoard.PlaceShip(Main.player_ships[i], Main.player_scenario.x[i], Main.player_scenario.y[i],Main.player_scenario.orientation[i]);
              }
          	catch(Exception m){
          		Popup.ExceptionOccured("player-scenario: "+m);
          		game_box.getChildren().clear();
		  		return game_box;
              }
          }
		
		for(int i=0; i<5; i++) {
		  	  try {
		  		  enemyBoard.PlaceShip(Main.enemy_ships[i],Main.enemy_scenario.x[i],Main.enemy_scenario.y[i],Main.enemy_scenario.orientation[i]);
		  	  }
		  	  catch(Exception m){
		  		Popup.ExceptionOccured("enemy-scenario: "+m);
		  		game_box.getChildren().clear();
		  		return game_box; 		 
		        }
		    }
		 //show boards
	      HBox boards = new HBox(100, playerBoard, enemyBoard);
	      boards.setAlignment(Pos.CENTER);
	      //show info
	      VBox player = new VBox(10,txtplayer,pships , score1, perc1 );
	      VBox enemy = new VBox(10, txtenemy, eships , score2, perc2 );
	      HBox info  = new HBox(400,player, enemy);
	      info.setAlignment(Pos.BASELINE_CENTER);
	      
	      //cin x and y for player
	      Text intro=new Text("Type here your next (x,y) shot!");
	      Text x = new Text("x");
	      text_x= new TextField();
	      HBox h2 = new HBox(10,x,text_x);
	      
	      Text y = new Text("y");
	      text_y= new TextField();
	      HBox h3 =new HBox(10,y,text_y);
	      
	      Button button_shot=new Button("shot");
	      button_shot.setOnAction(e -> readxy());
	      VBox insert = new VBox(10,intro,h2,h3);
	      HBox cin = new HBox(10,insert,button_shot);
	      
	      cin.setAlignment(Pos.BASELINE_CENTER);
	      game.setCenter(boards);
	      game.setTop(info);
	      game.setBottom(cin);
	      startGame();
	     return game;
	     
	  }
	private void readxy() {
		shot_x=Integer.parseInt(text_x.getText()); 	
		shot_y=Integer.parseInt(text_y.getText());
		playerMove();
	}
	
	private void startGame() {	
		enemyTurn = random.nextBoolean();
	    if(enemyTurn) {
	  	  enemyMove();
	  	  instructions.setText("Enemy plays first");
	    }
	    else {
	    	 instructions.setText("You  play first");
	    }
	}
 
                    
private void enemyMove() {
		 int x = random.nextInt(10);
		 int y = random.nextInt(10);
		 
		  Cell cell = playerBoard.getCell(y, x);
		  
		  cell.enemyshoot(enemy_shots);
		  enemy_savex.add(x);
		  enemy_savey.add(y);
		  
		  score2.setText("Enemy Score:" + Integer.toString(playerBoard.points));
		  pships.setText("Player's ships in board:"+ Integer.toString(playerBoard.ships));
		
		  float percentage = (float)playerBoard.succ_shots/(float)enemy_savex.size();
		 
		  perc2.setText("Percentage of successful shots:"+ Float.toString(percentage*100)+ "%");
		
		    
		  if (playerBoard.ships == 0) {
		        System.out.println("YOU LOSE");
		        System.exit(0);
		    }
		   if(enemy_savex.size()==40)
		     EndGame1();	 
	
		
	
}



private void playerMove() { 
	    
		int x=shot_x;
		int y=shot_y;
		Cell cell = enemyBoard.getCell(y, x);
		if(!cell.is_shot) {
		cell.shoot();
		
		player_savex.add(x);
		player_savey.add(y);
		
   	    
   	  //update info
   	  score1.setText("Player Score:" + Integer.toString(enemyBoard.points));
   	  eships.setText("Enemy's ships in board:"+ Integer.toString(enemyBoard.ships));
   	  float percentage=(float)enemyBoard.succ_shots/(float)player_savex.size();
   	  
   	  perc1.setText("Percentage of successful shots:"+ Float.toString(percentage*100)+ "%");
   	     if(player_savex.size()==40) {
   	    	 EndGame1();
   	     }
         enemyMove();
		}
	  }
	
	public static void EndGame1() {
		  if(enemyBoard.ships==0) {
			  Popup.EndGame("You win!");
		  }
		  else if(playerBoard.ships==0) {
			  Popup.EndGame("You lose!");
		  }
		  else if(enemyBoard.points>playerBoard.points) {
			 Popup.EndGame("You win!");  
		  }
		  else {
			  Popup.EndGame("You Lose!") ;
		  }
		  
		  game_box.getChildren().clear();
		  enemy_savex.clear();
			enemy_savey.clear();
			player_savex.clear();
			player_savey.clear();
			instructions.setText("1.Go to Application-->Load 2.Type a number. 3.Application-->Start");
	}

	
	public void showEnemyShips(Stage startStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("enemyships.fxml"));
		Scene scene = new Scene(root);
		startStage.setTitle("Enemy Ships");
		startStage.setScene(scene);
		startStage.show();
	}
public void showPlayerShots(Stage startSatge) throws Exception{
	Parent root = FXMLLoader.load(getClass().getResource("playershots.fxml"));
	Scene scene = new Scene(root);
	startStage.setTitle("Enemy Ships");
	startStage.setScene(scene);
	startStage.show();
}
}
	  

