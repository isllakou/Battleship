package application;



import java.util.List;

import application.Board.Cell;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;


public class Popup {
   
	
	public static void EnemyShips()
	{
	Stage popupwindow=new Stage();
	      
	popupwindow.initModality(Modality.APPLICATION_MODAL);
	popupwindow.setTitle("Enemy Ships");
	Text txt = new Text("Enemy Ships");
	txt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
	
	VBox layout= new VBox(10);
	layout.getChildren().addAll(txt);

	String name[]=new String[5];
	String state[]=new String[5];
	for(int i=0; i<5; i++) {
		name[i]=Main.enemy_ships[i].name;
	    state[i]=Main.enemy_ships[i].state;
	}
	for(int i=0; i<5; i++) {
	 Text text=new Text(name[i]+"--->"+state[i]);
	 layout.getChildren().addAll(text);
	}

	      
	layout.setAlignment(Pos.TOP_CENTER);
	      
	Scene scene1= new Scene(layout, 300, 250);
	      
	popupwindow.setScene(scene1);
	      
	popupwindow.showAndWait();
	       
}
	
public static void ShowLastShots(List<Integer> list_x, List<Integer> list_y,Board board, String shots)
{
Stage popupwindow=new Stage();
      
popupwindow.initModality(Modality.APPLICATION_MODAL);
popupwindow.setTitle(shots);
Text txt = new Text(shots);
txt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

Label label1=new Label("");
Label label2=new Label("");
Label label3=new Label("");
Label label4= new Label("");
Label label5=new Label("");

VBox layout= new VBox(10);
layout.getChildren().addAll(txt);

if(!list_x.isEmpty()) {
	int x=list_x.size();
	int y=list_y.size();

	label1.setText("(x,y)=(" + list_x.get(x-1) + list_y.get(y-1)+ ")," + ExtraInfo(list_x.get(x-1),list_y.get(y-1),board));
	layout.getChildren().addAll(label1);
	
    if(x-1>0) {
	   label2.setText("(x,y)=(" + list_x.get(x-2) + list_y.get(y-2)+ ")," + ExtraInfo(list_x.get(x-2),list_y.get(y-2),board));
	   layout.getChildren().addAll(label2);
     
       if(x-2>0) {
	   label3.setText("(x,y)=(" + list_x.get(x-3) + list_y.get(y-3)+ ")," + ExtraInfo(list_x.get(x-3),list_y.get(y-3),board));
	   layout.getChildren().addAll(label3);
 
       if(x-3>0) {
	   label4.setText("(x,y)=(" + list_x.get(x-4) + list_y.get(y-4)+ ")," + ExtraInfo(list_x.get(x-4),list_y.get(y-4),board));
	   layout.getChildren().addAll(label4);
       
       if(x-4>0) {
	    label5.setText("(x,y)=(" + list_x.get(x-5) + list_y.get(y-5)+ ")," + ExtraInfo(list_x.get(x-4),list_y.get(y-4),board));
	    layout.getChildren().addAll(label5);
       }
       }
       }
    }
}

      
layout.setAlignment(Pos.TOP_CENTER);
      
Scene scene1= new Scene(layout, 300, 250);
      
popupwindow.setScene(scene1);
      
popupwindow.showAndWait();
       
}

public static String ExtraInfo(int x, int y,Board board) {
	Cell cell = board.getCell(y, x);
	if(cell.ship!=null) {
		return cell.ship.name+ " is hitted";
	}
	else {
		return "unsuccesful shot";
	}
		
}

public static void ExceptionOccured(String exception)
{
Stage popupwindow=new Stage();
      
popupwindow.initModality(Modality.APPLICATION_MODAL);
popupwindow.setTitle("Exception Occured");
Label lbl = new Label(exception);
lbl.setWrapText(true);
//Setting the maximum width of the label
lbl.setMaxWidth(300);
//txt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

Label lbl2 = new Label("Press Application-->Load to insert new scenario");
lbl.setWrapText(true);
lbl.setMaxWidth(300);

VBox layout= new VBox(10);
layout.getChildren().addAll(lbl);
layout.getChildren().addAll(lbl2);

      
layout.setAlignment(Pos.TOP_CENTER);
      
Scene scene1= new Scene(layout, 300, 250);
      
popupwindow.setScene(scene1);
      
popupwindow.showAndWait();
       
} 

public static void EndGame(String endgame)
{
Stage popupwindow=new Stage();
      
popupwindow.initModality(Modality.APPLICATION_MODAL);
popupwindow.setTitle("End Game");
Label lbl = new Label(endgame);
lbl.setWrapText(true);

//Setting the maximum width of the label
lbl.setMaxWidth(300);
lbl.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));


VBox layout= new VBox(10);
layout.getChildren().addAll(lbl);

      
layout.setAlignment(Pos.TOP_CENTER);
      
Scene scene1= new Scene(layout, 300, 250);
      
popupwindow.setScene(scene1);
      
popupwindow.showAndWait();
       
} 

}
