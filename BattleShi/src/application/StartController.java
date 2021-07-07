package application;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class StartController {

@FXML
private Text idtext;

@FXML
private TextField idtextfield;

@FXML
private Label lblStatus ;

public void HomeScreen(ActionEvent event) throws Exception {
	boolean flag=true;
	String id = idtextfield.getText();
	  //player scenario
	  Main.player_scenario=new ReadFile();
	  Main.player_scenario.findFile("player",id);
	 
	  try{  
			 ex2.check_types(Main.player_scenario.type); 
			 Main.startStage.close();
		}
		catch(Exception m){
			lblStatus.setText("player-scenario: "+m);
			flag=false;
			//System.out.println("Exception occured on player-scenario: "+m);
			} 
		 //create ships
		for(int i=0; i<5; i++) {
			Main.player_ships[i] = new Ship(Main.player_scenario.type[i]);
		 }
	//enemy scenario
		Main.enemy_scenario=new ReadFile();
		 Main.enemy_scenario.findFile("enemy",id);
		 try{  
			 ex2.check_types(Main.enemy_scenario.type); 
		}
		catch(Exception m){
			lblStatus.setText("enemy-scenario: "+m);
			flag=false;
			//System.out.println("Exception occured on enemy-scenario: "+m);
			} 
	    //create ships
		for(int i=0; i<5; i++) {
			Main.enemy_ships[i] = new Ship(Main.enemy_scenario.type[i]);
		}  
		if(flag)
		Main.startStage.close();
		
 }
}

