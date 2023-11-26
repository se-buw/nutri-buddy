/*
 * Ferdinand Brand, Noah Erthel
 * 
 * Sources:
 * for Logo: https:
 * //deepai.org/machine-learning-model/logo-generator
 * for Nutri Score:
 * https://www.online-trainer-lizenz.de/blog/kalorien-und-makros-berechnen/
 * https://www.weightwatchers.com/de/blog/abnehmen/kaloriendefizit-berechnen?g_acctid=134-402-0309&g_adid=0&g_adtype=search&g_campaign=Generic-Retargeting-Lapsed__qdstw_qobjc_qbudc_qaudl_qrtgn_qpma_qostz_qdevz_qlobr_qgeon_qkwn&g_campaignid=16913307293&g_keyword=&g_acctid=DEFAULT&g_adgroupid=&g_adid=&g_adtype=account&g_adtype=&g_campaign=DEFAULT&g_campaignid=17516152055&g_device=c&g_ifcreative=&g_ifproduct=&g_keyword=&g_keywordid=&g_locinterest=&g_locphysical=9042841&g_merchantid=&g_network=x&g_partition=&g_placement=&g_productchannel=&g_productid=&gad_source=1&gclid=CjwKCAiA04arBhAkEiwAuNOsIoObmn2riIhoo7zZyUnnb3pF0Jc6cFIyWTMobKqN9H7uqFIzzV_QjRoCNYQQAvD_BwE&gclsrc=aw.ds
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
	private static final int 	screenSizeX = 1000;
	private static final int 	screenSizeY = 800;
	private static final int 	topPanelSizeY = 100;
	private static final String mainPaneColor = "-fx-background-color: rgba(252, 255, 255, 1);";
	private static final String topPaneColor = "-fx-background-color: rgba(181, 245, 157, 1);";
	private static final int 	normalVBoxSpacingY = 30;
	private static final int 	normalHBoxSpacingX = 15;
	private static		 int	loggedInAccountIndex = -1;

	// -----------------------------------------------------------------------------------

	// Declare Panes
	VBox mainMenuPane = new VBox(normalVBoxSpacingY);
	VBox mainMenuPaneTop = new VBox(normalVBoxSpacingY);
	VBox mainMenuPaneCen = new VBox(normalVBoxSpacingY);
	HBox mainMenuPaneBot = new HBox(normalHBoxSpacingX);

	VBox accountPane = new VBox(normalVBoxSpacingY);
	VBox accountPaneTop = new VBox(normalVBoxSpacingY);
	VBox accountPaneCen = new VBox(normalVBoxSpacingY);
	HBox accountPaneBot = new HBox(normalHBoxSpacingX);

	VBox homePane = new VBox(normalVBoxSpacingY);
	VBox homePaneTop = new VBox(normalVBoxSpacingY);
	VBox homePaneCen = new VBox(normalVBoxSpacingY);
	HBox homePaneBot = new HBox(normalHBoxSpacingX);

	VBox recipiesPane = new VBox(normalVBoxSpacingY);
	VBox recipiesPaneTop = new VBox(normalVBoxSpacingY);
	VBox recipiesPaneCen = new VBox(normalVBoxSpacingY);
	HBox recipiesPaneBot = new HBox(normalHBoxSpacingX);
	
	// Declare Scenes
	Scene mainMenuScene = new Scene(mainMenuPane, screenSizeX, screenSizeY);
    Scene accountScene = new Scene(accountPane, screenSizeX, screenSizeY);
    Scene homeScene = new Scene(homePane, screenSizeX, screenSizeY);
    Scene recipiesScene = new Scene(recipiesPane, screenSizeX, screenSizeY);
	
    // Declare Content for MainMenuScene
	Button switchSceneMainMenu2AccountBtn = new Button();
 	Label mainMenuHeaderLabel = new Label();
 	Label loginInstructionsLabel = new Label();
	Label loginFeedbackLabel = new Label();
	Label loginLabel = new Label();
	Label bmiLabel = new Label();
	TextField accountLogin = new TextField();


    // Declare Content for AccountsScene
	Button switchSceneAccounts2MainMenuBtn = new Button();
	Button switchSceneAccounts2HomeBtn = new Button();
	Label accountsHeaderLabel = new Label();
    Label accountsAddingExplanation = new Label();
    TextField accountInput = new TextField();
    Label accountsAddingFeedback = new Label();

    // Declare Content for HomeScene
    Button switchSceneHome2AccountsBtn = new Button();
    Button switchSceneHome2RecipiesBtn = new Button();
    Label homeHeaderLabel = new Label();
    Label homeBMILabel = new Label();
    Label homeNutriLabel = new Label();

    // Declare Content for RecipiesScene
    Button switchSceneRecipies2HomeBtn = new Button();
    Label recipiesHeaderLabel = new Label();

	
	@Override
    public void start(Stage stage)
	{
		// Set Variables for Panes
		mainMenuPane.getChildren().addAll(mainMenuPaneTop, mainMenuPaneCen, mainMenuPaneBot);
		mainMenuPaneTop.setPrefSize(300, topPanelSizeY);
		mainMenuPaneTop.setAlignment(Pos.CENTER);
		mainMenuPaneCen.setAlignment(Pos.CENTER);
		mainMenuPaneBot.setAlignment(Pos.CENTER);
		mainMenuPane.setMargin(mainMenuPaneBot, new javafx.geometry.Insets(400, 0, 0, 0));

		accountPane.getChildren().addAll(accountPaneTop, accountPaneCen, accountPaneBot);
		accountPaneTop.setPrefSize(300, topPanelSizeY);
		accountPaneTop.setAlignment(Pos.CENTER);
		accountPaneCen.setAlignment(Pos.CENTER);
		accountPaneBot.setAlignment(Pos.CENTER);
		accountPane.setMargin(accountPaneBot, new javafx.geometry.Insets(400, 0, 0, 0));
		
		homePane.getChildren().addAll(homePaneTop, homePaneCen, homePaneBot);
		homePaneTop.setPrefSize(300, topPanelSizeY);
		homePaneTop.setAlignment(Pos.CENTER);
		homePaneCen.setAlignment(Pos.CENTER);
		homePaneBot.setAlignment(Pos.CENTER);
		homePane.setMargin(homePaneBot, new javafx.geometry.Insets(400, 0, 0, 0));
		
		recipiesPane.getChildren().addAll(recipiesPaneTop, recipiesPaneCen, recipiesPaneBot);
		recipiesPaneTop.setPrefSize(300, topPanelSizeY);
		recipiesPaneTop.setAlignment(Pos.CENTER);
		recipiesPaneCen.setAlignment(Pos.CENTER);
		recipiesPaneBot.setAlignment(Pos.CENTER);
		recipiesPane.setMargin(recipiesPaneBot, new javafx.geometry.Insets(400, 0, 0, 0));
		
		// Changes pane colors 
		mainMenuPane.setStyle(mainPaneColor);
		mainMenuPaneTop.setStyle(topPaneColor);
		
		accountPane.setStyle(mainPaneColor);
		accountPaneTop.setStyle(topPaneColor);

		homePane.setStyle(mainPaneColor);
		homePaneTop.setStyle(topPaneColor);

		recipiesPane.setStyle(mainPaneColor);
		recipiesPaneTop.setStyle(topPaneColor);

        
        // -----------------------------------------------------------------------------------
		// Create scene contents for mainMenuScene
        // Buttons:
		switchSceneMainMenu2AccountBtn.setText("Switch Scene to Account");
		switchSceneMainMenu2AccountBtn.setOnAction(e->
		{
			stage.setScene(accountScene);
		});
		switchSceneMainMenu2AccountBtn.setFont(Font.font("Standart", 16d));
		// Labels:
		mainMenuHeaderLabel.setText("Main Menu");
		mainMenuHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
		loginInstructionsLabel.setText("Please enter the name of the account you want to log in with");
		loginInstructionsLabel.setFont(Font.font("Standart", 16d));
		loginFeedbackLabel.setText("");
		loginFeedbackLabel.setFont(Font.font("Standart", 16d));

		// Input for login
		accountLogin.setFont(Font.font("Standart", 16d));
		accountLogin.setOnAction(e->
		{
        	String inputValue = accountLogin.getText();
        	int index = searchAccount(inputValue);
        	// Checks login status and outputs error or transitions to homeScene
        	if(index  == -1)
        	{
        		accountLogin.setText("");
        		loginFeedbackLabel.setText("Account not found");
        		loggedInAccountIndex = -1;
        		updateAccountScene();
        	}
        	else
        	{
        		accountLogin.setText("");
        		loggedInAccountIndex = index;
        		updateAccountScene();
        		updateHomeScene();
        		stage.setScene(homeScene);
        	}
        });
		accountLogin.setMaxWidth(400);
		
		mainMenuPaneTop.getChildren().addAll(mainMenuHeaderLabel);
		mainMenuPaneCen.getChildren().addAll(loginInstructionsLabel, accountLogin, loginFeedbackLabel);
		mainMenuPaneBot.getChildren().addAll(switchSceneMainMenu2AccountBtn);
        
		// -----------------------------------------------------------------------------------
        // Create scene contents for accountScene
		// Buttons:
		switchSceneAccounts2MainMenuBtn.setText("Switch Scene to MainMenu");
		switchSceneAccounts2MainMenuBtn.setOnAction(e->
		{
			stage.setScene(mainMenuScene);
		});
		switchSceneAccounts2MainMenuBtn.setFont(Font.font("Standart", 16d));
		switchSceneAccounts2HomeBtn.setText("Switch Scene to Home");
		switchSceneAccounts2HomeBtn.setOnAction(e->
		{
    		updateHomeScene();
			stage.setScene(homeScene);
		});
		switchSceneAccounts2HomeBtn.setFont(Font.font("Standart", 16d));
		switchSceneAccounts2HomeBtn.setVisible(false);
		// Labels:
		accountsHeaderLabel.setText("Accounts");
		accountsHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
        accountsAddingExplanation.setText("Add new Account in this order:\n"
        		+ "Name, height, gender, weight, goal weight (seperated by commas)");
        accountsAddingExplanation.setFont(Font.font("Standart", 16d));
        accountsAddingFeedback.setText("");
        accountsAddingFeedback.setFont(Font.font("Standart", 16d));
        // Input for account creation
        accountInput.setText("");
        accountInput.setFont(Font.font("Standart", 16d));
        accountInput.setOnAction(e->
        {
        	String inputValue = accountInput.getText();
        	int status = addAccount(inputValue);
        	accountInput.setText("");
        	if(status == -1)
        	{
        		accountsAddingFeedback.setText("Account already exists");
        	}
        	else
        	{
        		accountsAddingFeedback.setText("Added account sucessfully");
        	}
        });
        accountInput.setMaxWidth(400);
        accountPaneTop.getChildren().addAll(accountsHeaderLabel);
        accountPaneCen.getChildren().addAll(accountsAddingExplanation, accountInput, accountsAddingFeedback);
        accountPaneBot.getChildren().addAll(switchSceneAccounts2MainMenuBtn, switchSceneAccounts2HomeBtn);
        
        // -----------------------------------------------------------------------------------
        // Create scene contents for homeScene
        // Buttons:
        switchSceneHome2AccountsBtn.setText("Switch Scene to Accounts");
        switchSceneHome2AccountsBtn.setOnAction(e->
        {
			stage.setScene(accountScene);
		});
        switchSceneHome2AccountsBtn.setFont(Font.font("Standart", 16d));
        switchSceneHome2RecipiesBtn.setText("Switch Scene to Recipies");
        switchSceneHome2RecipiesBtn.setOnAction(e->
        {
			stage.setScene(recipiesScene);
		});
        switchSceneHome2RecipiesBtn.setFont(Font.font("Standart", 16d));
        // Labels:
        homeHeaderLabel.setText("Home");
        homeHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
        homeBMILabel.setText("");
        homeBMILabel.setFont(Font.font("Standart", 16d));
        homeNutriLabel.setText("");
        homeNutriLabel.setFont(Font.font("Standart", 16d));

        homePaneTop.getChildren().addAll(homeHeaderLabel);
        homePaneCen.getChildren().addAll(homeBMILabel, homeNutriLabel);
        homePaneBot.getChildren().addAll(switchSceneHome2AccountsBtn, switchSceneHome2RecipiesBtn);
		
        // -----------------------------------------------------------------------------------
        // Create scene contents for recipiesScene
        // Buttons:
        switchSceneRecipies2HomeBtn.setText("Switch Scene to Home");
        switchSceneRecipies2HomeBtn.setOnAction(e->
        {
			stage.setScene(homeScene);
		});
        switchSceneRecipies2HomeBtn.setFont(Font.font("Standart", 16d));
        // Labels:
        recipiesHeaderLabel.setText("Recipies");
        recipiesHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
        
        recipiesPaneTop.getChildren().addAll(recipiesHeaderLabel);
        recipiesPaneCen.getChildren().addAll();
        recipiesPaneBot.getChildren().addAll(switchSceneRecipies2HomeBtn);

        // Set scene
        stage.setScene(mainMenuScene);
        stage.setTitle("NutriBuddy");
        stage.show();
	}

	// -----------------------------------------------------------------------------------

    static void main(String[] args)
    {
        launch();
    }
    
    // -----------------------------------------------------------------------------------
    // Changes Variables in the account scene, normally called when switching to this scene
    public void updateAccountScene()
    {
    	// Makes the transition from account to home scene possible, only if the user is logged in
    	if(loggedInAccountIndex == -1)
    	{
    		switchSceneAccounts2HomeBtn.setVisible(false);
    		switchSceneAccounts2MainMenuBtn.setVisible(true);
    	}
    	if(loggedInAccountIndex >= 0)
    	{
    		switchSceneAccounts2HomeBtn.setVisible(true);
    		switchSceneAccounts2MainMenuBtn.setVisible(false);
    	}
		
    }
    
    // -----------------------------------------------------------------------------------
    // Changes Variables in the account scene, normally called when switching to this scene
    public void updateHomeScene()
    {
    	// Updates the BMI
    	float weight = Float.parseFloat(readAccount(loggedInAccountIndex, "weight"));
    	float height = Float.parseFloat(readAccount(loggedInAccountIndex, "height"));
    	float bmi = weight / (float)Math.pow(height/100.0f, 2);
    	homeBMILabel.setText("BMI: " + String.format("%.1f" ,bmi));
    	
    	// Updates the Nutri Score
    	float genderFactor;
    	if(readAccount(loggedInAccountIndex, "gender") == "male")
    	{
    		genderFactor = 1.0f;
    	}
    	else
    	{
    		genderFactor = 0.9f;

    	}
    	float dailyNutriKCalTotal = 24.0f * weight * 1.6f;
    	float dailyNutriKCalCurrent = Float.parseFloat(readAccount(loggedInAccountIndex, "nutritions_day"));
    	float dailyNutriKCalDelta = dailyNutriKCalTotal - dailyNutriKCalCurrent;
    	homeNutriLabel.setText("Kcal per day for you: " + String.format("%.0f", dailyNutriKCalTotal) +
    						 "\nKcal already consumed today: " + String.format("%.0f", dailyNutriKCalCurrent) +
    						 "\nKcal still left for today: " + String.format("%.0f", dailyNutriKCalDelta));
    }
    
    // -----------------------------------------------------------------------------------
	// Returns value of given data from account with given index from the csv file
  	public String readAccount(int index, String data)
 	{
 		// Returns all names from the csv file
 		String result = "Error, Account " + index + " with data " + data + "not found";
 		try (Reader reader = Files.newBufferedReader(Paths.get(accountCsvFile));
 				@SuppressWarnings("deprecation")
 				CSVParser csvParser = new CSVParser(reader,
 						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());)
 		{
 			// Reading
 			int i = 0;
 			for (CSVRecord csvRecord : csvParser)
 			{
 				if(i == index)
 				{
 					result = csvRecord.get(data);
 				}
 				++i;
 			}
 			
 			csvParser.close();
 			reader.close();
 		}
 		catch (IOException e)
 		{
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 		return result;
 	}
 	
 	// -----------------------------------------------------------------------------------
 	// Searches for Account by Name and returns index of found record; -1 if not found
 	public int searchAccount(String name)
 	{
 		// Returns all names from the csv file
 		try (Reader reader = Files.newBufferedReader(Paths.get(accountCsvFile));
 				@SuppressWarnings("deprecation")
 				CSVParser csvParser = new CSVParser(reader,
 						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());)
 		{
 			// Reading
 			int i = 0;
 			for (CSVRecord csvRecord : csvParser)
 			{
 				String nameCheck = csvRecord.get("name");
 				if(nameCheck.equals(name))
 				{
 					return i;
 				}
 				++i;
 			}
 			
 			csvParser.close();
 			reader.close();
 		}
 		catch (IOException e)
 		{
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 		return (-1);
 	}
 	
 	// -----------------------------------------------------------------------------------
 	
 	public int addAccount(String account)
 	{
 		// Adds a new account record to the accounts csv
 		int returnStatus = -1;
 		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(accountCsvFile), StandardOpenOption.APPEND);
 				@SuppressWarnings("deprecation")
 				CSVPrinter csvPrinter = new CSVPrinter(writer,
 						CSVFormat.DEFAULT.withFirstRecordAsHeader());)
 		{
 			// Add record after removing leading and trailing spaces
 			account = account + ",0";
 			System.out.println(account);
 			String[] accountData = account.split(",");
 			// Adds account only if it didn't already exist
 			if(searchAccount(accountData[0].strip()) == -1)
 			{
 	 			csvPrinter.printRecord(accountData[0].strip(), accountData[1].strip(), accountData[2].strip(), accountData[3].strip(), accountData[4].strip(), accountData[5].strip());
 	 			returnStatus = 1;
 			}
 			
 			csvPrinter.close();
 			writer.close();
 			
 		}
 		catch (IOException e)
 		{
 			e.printStackTrace();
 		}
 		return returnStatus;
 	}
}