/*
 * Ferdinand Brand, Noah Erthel
 * 
 * Quellen:
 * für Logo: https://deepai.org/machine-learning-model/logo-generator
 */
package de.buw.se4de;

// Importing Stuff
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

// CVS Manipulation
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

// JavaFX
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

// ---------------------------------------------------------------------------------------

public class App extends Application {
	
// ---------------------------------------------------------------------------------------
	// Constants:
	private static final String accountCsvFile = "src/main/resources/accounts.csv";
	private static final String foodItemCsvFile = "src/main/resources/food_item.csv";
	private static final int screenSizeX = 1000;
	private static final int screenSizeY = 800;
	private static final int topPanelSizeY = 100;
	private static final String mainPaneColor = "-fx-background-color: rgba(252, 255, 255, 1);";
	private static final String topPaneColor = "-fx-background-color: rgba(181, 245, 157, 1);";
	private static final int normalVBoxSpacingY = 30;
	
	// -----------------------------------------------------------------------------------

	@Override
    public void start(Stage stage) {
		// Creates Panes
		VBox mainMenuPane = new VBox(normalVBoxSpacingY);
		VBox mainMenuPaneTop = new VBox(normalVBoxSpacingY);
		VBox mainMenuPaneBot = new VBox(normalVBoxSpacingY);
		mainMenuPane.getChildren().addAll(mainMenuPaneTop, mainMenuPaneBot);
		mainMenuPaneTop.setPrefSize(300, topPanelSizeY);
		mainMenuPaneTop.setAlignment(Pos.CENTER);
		mainMenuPaneBot.setAlignment(Pos.CENTER);
		
		VBox accountPane = new VBox(normalVBoxSpacingY);
		VBox accountPaneTop = new VBox(normalVBoxSpacingY);
		VBox accountPaneBot = new VBox(normalVBoxSpacingY);
		accountPane.getChildren().addAll(accountPaneTop, accountPaneBot);
		accountPaneTop.setPrefSize(300, topPanelSizeY);
		accountPaneTop.setAlignment(Pos.CENTER);
		accountPaneBot.setAlignment(Pos.CENTER);

		VBox homePane = new VBox(normalVBoxSpacingY);
		VBox homePaneTop = new VBox(normalVBoxSpacingY);
		VBox homePaneBot = new VBox(normalVBoxSpacingY);
		homePane.getChildren().addAll(homePaneTop, homePaneBot);
		homePaneTop.setPrefSize(300, topPanelSizeY);
		homePaneTop.setAlignment(Pos.CENTER);
		homePaneBot.setAlignment(Pos.CENTER);

		VBox recipiesPane = new VBox(normalVBoxSpacingY);
		VBox recipiesPaneTop = new VBox(normalVBoxSpacingY);
		VBox recipiesPaneBot = new VBox(normalVBoxSpacingY);
		recipiesPane.getChildren().addAll(recipiesPaneTop, recipiesPaneBot);
		recipiesPaneTop.setPrefSize(300, topPanelSizeY);
		recipiesPaneTop.setAlignment(Pos.CENTER);
		recipiesPaneBot.setAlignment(Pos.CENTER);


		// Changes pane colors 
		mainMenuPane.setStyle(mainPaneColor);
		mainMenuPaneTop.setStyle(topPaneColor);
		
		accountPane.setStyle(mainPaneColor);
		accountPaneTop.setStyle(topPaneColor);

		homePane.setStyle(mainPaneColor);
		homePaneTop.setStyle(topPaneColor);

		recipiesPane.setStyle(mainPaneColor);
		recipiesPaneTop.setStyle(topPaneColor);

		
		// Create Scenes
		Scene mainMenuScene = new Scene(mainMenuPane, screenSizeX, screenSizeY);
        Scene accountScene = new Scene(accountPane, screenSizeX, screenSizeY);
        Scene homeScene = new Scene(homePane, screenSizeX, screenSizeY);
        Scene recipiesScene = new Scene(recipiesPane, screenSizeX, screenSizeY);
        
        // -----------------------------------------------------------------------------------
		// Create scene contents for mainMenuScene
        // Buttons:
		Button switchSceneMainMenu2AccountBtn = new Button("Switch Scene to Account");
		switchSceneMainMenu2AccountBtn.setOnAction(e->{
			stage.setScene(accountScene);
		});
		// Labels:
		Label mainMenuHeaderLabel = new Label("Main Menu");
		mainMenuHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
		
		mainMenuPaneTop.getChildren().addAll(mainMenuHeaderLabel);
		mainMenuPaneBot.getChildren().addAll(switchSceneMainMenu2AccountBtn);
		
        
		// -----------------------------------------------------------------------------------
        // Create scene contents for accountScene
		// Buttons:
		Button switchSceneAccounts2MainMenuBtn = new Button("Switch Scene to MainMenu");
		switchSceneAccounts2MainMenuBtn.setOnAction(e->{
			stage.setScene(mainMenuScene);
		});
		Button switchSceneAccount2HomeBtn = new Button("Switch Scene to Home");
		switchSceneAccount2HomeBtn.setOnAction(e->{
			stage.setScene(homeScene);
		});
		// Labels:
		Label accountsHeaderLabel = new Label("Accounts");
		accountsHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
		
        Label accountsAddingExplanation = new Label("Add new Account in this order:\n"
        		+ "Name, height, gender, weight, goal weight (seperated by commas)");
        
        TextField accountInput = new TextField("");
        accountInput.setOnAction(e->{
        	String inputValue = accountInput.getText();
        	addAccount(inputValue);
        });
        accountInput.setMaxWidth(400);
        accountPaneTop.getChildren().addAll(accountsHeaderLabel);
        accountPaneBot.getChildren().addAll(switchSceneAccounts2MainMenuBtn, switchSceneAccount2HomeBtn,
        		accountsAddingExplanation, accountInput);
        
        // -----------------------------------------------------------------------------------
        // Create scene contents for homeScene
        // Buttons:
        Button switchSceneHome2AccountsBtn = new Button("Switch Scene to Accounts");
        switchSceneHome2AccountsBtn.setOnAction(e->{
			stage.setScene(accountScene);
		});
        Button switchSceneHome2RecipiesBtn = new Button("Switch Scene to Recipies");
        switchSceneHome2RecipiesBtn.setOnAction(e->{
			stage.setScene(recipiesScene);
		});
        // Labels:
        Label homeHeaderLabel = new Label("Home");
        homeHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
        
        homePaneTop.getChildren().addAll(homeHeaderLabel);
        homePaneBot.getChildren().addAll(switchSceneHome2AccountsBtn, switchSceneHome2RecipiesBtn);
		
        // -----------------------------------------------------------------------------------
        // Create scene contents for recipiesScene
        // Buttons:
        Button switchSceneRecipies2HomeBtn = new Button("Switch Scene to Home");
        switchSceneRecipies2HomeBtn.setOnAction(e->{
			stage.setScene(homeScene);
		});
        // Labels:
        Label recipiesHeaderLabel = new Label("Recipies");
        recipiesHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));

        
        recipiesPaneTop.getChildren().addAll(recipiesHeaderLabel);
        recipiesPaneBot.getChildren().addAll(switchSceneRecipies2HomeBtn);

        
        
        // Set scene
        stage.setScene(mainMenuScene);
        stage.setTitle("NutriBuddy");
        stage.show();
	}

	// -----------------------------------------------------------------------------------

    public static void main(String[] args) {
        launch();
    }
    
    // -----------------------------------------------------------------------------------
	
 	public static String readAccount() {
 		// Returns all names from the csv file
 		String result = "";
 		try (Reader reader = Files.newBufferedReader(Paths.get(accountCsvFile));
 				@SuppressWarnings("deprecation")
 				CSVParser csvParser = new CSVParser(reader,
 						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
 			
 			// Reading
 			for (CSVRecord csvRecord : csvParser) {
 				String name = csvRecord.get("name");
 				result += "Hello " + name + "!\n";
 			}
 			
 			csvParser.close();
 			reader.close();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 		return result;
 	}
 	
 	// -----------------------------------------------------------------------------------

 	public static void addAccount(String account) {
 		// Adds a new account record to the accounts csv
 		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(accountCsvFile), StandardOpenOption.APPEND);
 				@SuppressWarnings("deprecation")
 				CSVPrinter csvPrinter = new CSVPrinter(writer,
 						CSVFormat.DEFAULT.withFirstRecordAsHeader());) {
 			
 			// Add record after removing leading and trailing spaces
 			account = account + ",0";
 			System.out.println(account);
 			String[] accountData = account.split(",");
 			csvPrinter.printRecord(accountData[0].strip(), accountData[1].strip(), accountData[2].strip(), accountData[3].strip(), accountData[4].strip(), accountData[5].strip());
 			
 			csvPrinter.close();
 			writer.close();
 			
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 	}
}
