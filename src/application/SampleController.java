package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;

public class SampleController implements Initializable{
	@FXML
	TextField username;
	@FXML
	TextField passtext;
	@FXML
	PasswordField password;
	@FXML
	Button submitbtn;
	@FXML
	FontAwesomeIcon showhide;
	@FXML
	Label namealert;
	@FXML
	Label passalert;
	@FXML
	Label strength;
	@FXML
	Label strengthval;
	public ArrayList<Boolean> strengthCheck(String password) {
		Boolean charCheck = false;
		Boolean numCheck = false;
		Boolean capsCheck = false;
		Boolean speCheck = false;
		Boolean lenCheck = false;
		ArrayList<Boolean> op = new ArrayList<Boolean>(5);
		if(password.length()>=8) {
			lenCheck = true;
		}
		for(int i=0;i<password.length();i++)
		{
			if(Character.isLowerCase(password.charAt(i))) {
				charCheck = true;
			}
			else if(Character.isUpperCase(password.charAt(i))) {
				capsCheck = true;
			}
			else if(Character.isDigit(password.charAt(i))) {
				numCheck = true;
			}
			else {
				speCheck = true;
			}
		}
		op.add(lenCheck);
		op.add(charCheck);
		op.add(capsCheck);
		op.add(numCheck);
		op.add(speCheck);
		return op;
	}
	public void initialize(URL url, ResourceBundle rb) {
		String title = "GC Consulting";
		password.textProperty().bindBidirectional(passtext.textProperty());
		EventHandler<ActionEvent> subeve = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae1) {
				namealert.setText("");
				passalert.setText("");
				String passstrength = strengthval.getText();
				if(username.getText()=="")
				{
					namealert.setText("*Please Enter The Username");
					username.requestFocus();
					if(password.getText()=="")
					{
						passalert.setText("*Please Enter The Password");
					}
				}
				else if(password.getText()=="")
				{
					passalert.setText("*Please Enter The Password");
					password.requestFocus();
				}
				else if(passstrength==""||passstrength=="Bad"||passstrength=="Weak"||passstrength=="Medium"||passstrength=="Very Bad")
				{
					ArrayList<Boolean> stre = new ArrayList<Boolean>();
					stre = strengthCheck(password.getText());
					Alert a1 = new Alert(AlertType.ERROR);
					if(!stre.get(0)) {
						a1.setContentText("Password length should be more than 8!");
					}
					else if(!stre.get(1)) {
						a1.setContentText("Password must contain atleast one alphabet!");
					}
					else if(!stre.get(2)) {
						a1.setContentText("Password must contain atleast one alphabet in uppercase!");
					}
					else if(!stre.get(3)) {
						a1.setContentText("Password must contain atleast one numerical character!");
					}
					else if(!stre.get(4)) {
						a1.setContentText("Password must contain atleast one special character!\n(!@#$%^&*()-+)");
					}
					a1.setTitle(title);
					a1.setHeaderText("Password Strength is "+passstrength);
					a1.showAndWait();
				}
				else {
					Alert a2 = new Alert(AlertType.INFORMATION);
					a2.setTitle(title);
					a2.setHeaderText("WELCOME");
					a2.setContentText("Welcome "+username.getText());
					a2.showAndWait();
				}
				
			}
		};
	submitbtn.setOnAction(subeve);
	EventHandler<KeyEvent> passeve = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent keyeve) {
			if(keyeve.getCode()!=KeyCode.CAPS && keyeve.getCode()!=KeyCode.SHIFT) {
				if(password.getText()!="") {
					passalert.setText("");
				}
				if(password.getText()=="") {
					strength.setText("");
					strengthval.setText("");
				}
				else {
					ArrayList<Boolean> stre1 = new ArrayList<Boolean>();
					ArrayList<String> streval = new ArrayList<String>();
					streval.add("Very Bad");
					streval.add("Bad");
					streval.add("Weak");
					streval.add("Medium");
					streval.add("Strong");
					ArrayList<String> strecol = new ArrayList<String>();
					strecol.add("RED");
					strecol.add("DARKORANGE");
					strecol.add("ORANGE");
					strecol.add("YELLOW");
					strecol.add("LIGHTGREEN");
					stre1 = strengthCheck(password.getText());
					strength.setText("Strength:");
					int counter = 0;
					for(int i=0;i<5;i++)
					{
						if(stre1.get(i)) {
							counter++;
						}
					}
					strengthval.setText(streval.get(counter-1));
					strengthval.setTextFill(Color.web(strecol.get(counter-1)));
				}
			}
		}
	};
	password.setOnKeyTyped(passeve);
	passtext.setOnKeyTyped(passeve);
	EventHandler<KeyEvent> nameeve = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent keyeve2) {
			if(keyeve2.getCode()!=KeyCode.CAPS && keyeve2.getCode()!=KeyCode.SHIFT) {
				if(username.getText()!="") {
					namealert.setText("");
				}
			}
		}
	};
	username.setOnKeyTyped(nameeve);
	EventHandler<MouseEvent> eyecon = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent me2) {
			if(showhide.getGlyphName().equals("EYE")) {
				showhide.setGlyphName("EYE_SLASH");
				passtext.toFront();
			}
			else {
				showhide.setGlyphName("EYE");
				password.toFront();
			}
		}
	};
	showhide.setOnMousePressed(eyecon);
	}
}
