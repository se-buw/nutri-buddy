/*
 * Group A
 * 
 * Sources:
 * https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/
 * https://www.geeksforgeeks.org/ways-to-read-input-from-console-in-java/
 * https://stackoverflow.com/questions/4397907/updating-specific-cell-csv-file-using-java
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
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

// CVS Manipulation
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

// JavaFX
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ListView;
import javafx.collections.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
// ---------------------------------------------------------------------------------------

public class App extends Application {
	
// ---------------------------------------------------------------------------------------
	// Constants:
	private static final String accountCsvFile = "src/main/resources/accounts.csv";
	private static final String foodItemCsvFile = "src/main/resources/food_items.csv";
	private static final String factsCsvFile = "src/main/resources/facts.csv";
	private static final int 	screenSizeX = 1000;
	private static final int 	screenSizeY = 900;
	private static final int 	topPanelSizeY = 100;
	private static final String mainPaneColor = "-fx-background-color: rgba(252, 255, 255, 1);";
	private static final String topPaneColor = "-fx-background-color: rgba(181, 245, 157, 1);";
	private static final int 	normalVBoxSpacingY = 30;
	private static final int 	smallVBoxSpacingY = 2;
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
	
	VBox accountEditPane = new VBox(normalVBoxSpacingY);
	VBox accountEditPaneTop = new VBox(normalVBoxSpacingY);
	VBox accountEditPaneCen = new VBox(smallVBoxSpacingY);
	HBox accountEditPaneGen = new HBox(normalHBoxSpacingX);
	HBox accountEditPaneBot = new HBox(normalHBoxSpacingX);

	VBox homePane = new VBox(normalVBoxSpacingY);
	VBox homePaneTop = new VBox(normalVBoxSpacingY);
	VBox homePaneCen = new VBox(normalVBoxSpacingY);
	HBox homePaneBot = new HBox(normalHBoxSpacingX);
	
	VBox foodAddPane = new VBox(normalVBoxSpacingY);
	VBox foodAddPaneTop = new VBox(normalVBoxSpacingY);
	VBox foodAddPaneCen = new VBox(normalVBoxSpacingY);
	HBox foodAddPaneBot = new HBox(normalHBoxSpacingX);

	VBox recipiesPane = new VBox(normalVBoxSpacingY);
	VBox recipiesPaneTop = new VBox(normalVBoxSpacingY);
	VBox recipiesPaneCen = new VBox(smallVBoxSpacingY);
	HBox recipiesPaneBot = new HBox(normalHBoxSpacingX);
	
	// Declare Scenes
	Scene mainMenuScene = new Scene(mainMenuPane, screenSizeX, screenSizeY);
    Scene accountScene = new Scene(accountPane, screenSizeX, screenSizeY);
    Scene accountEditScene = new Scene(accountEditPane, screenSizeX, screenSizeY);
    Scene homeScene = new Scene(homePane, screenSizeX, screenSizeY);
    Scene foodAddScene = new Scene(foodAddPane, screenSizeX, screenSizeY);
    Scene recipiesScene = new Scene(recipiesPane, screenSizeX, screenSizeY);
	
    // Declare Content for MainMenuScene
	Button switchSceneMainMenu2AccountBtn = new Button();
 	Label mainMenuHeaderLabel = new Label();
 	Label loginInstructionsLabel = new Label();
	Label loginFeedbackLabel = new Label();
	Label loginLabel = new Label();
	Label accountListExplanationLabel = new Label();
	TextField accountLogin = new TextField();
	ListView<String> accountsList = new ListView<String>();
	
    // Declare Content for AccountsScene
	Button switchSceneAccounts2MainMenuBtn = new Button();
	Label accountsHeaderLabel = new Label();
    Label accountsAddingExplanation = new Label();
    TextField accountInput = new TextField();
    Label accountsAddingFeedback = new Label();

    // Declare Content for AccountsEditScene
 	Button switchSceneAccountsEdit2HomeBtn = new Button();
 	Label accountsEditHeaderLabel = new Label();
    Label accountsEditAddingExplanation = new Label();
    Label accountsEditCurrentNameLabel = new Label();
    TextField accountEditInputName = new TextField();
    Label accountsEditAddingNameFeedback = new Label();
    Label accountsEditCurrentHeightLabel = new Label();
    TextField accountEditInputHeight = new TextField();
    Label accountsEditAddingHeightFeedback = new Label();
    Label accountsEditCurrentGenderLabel = new Label();
    TextField accountEditInputGender = new TextField();
    Label accountsEditAddingGenderFeedback = new Label();
    Label accountsEditCurrentWeightLabel = new Label();
    TextField accountEditInputWeight = new TextField();
    Label accountsEditAddingWeightFeedback = new Label();
    Label accountsEditCurrentGoalWeightLabel = new Label();
    TextField accountEditInputGoalWeight = new TextField();
    Label accountsEditAddingGoalWeightFeedback = new Label();
    RadioButton maleRBtn = new RadioButton("male");
    RadioButton femaleRBtn = new RadioButton("female");
    ToggleGroup genderGroup = new ToggleGroup();
    
    // Declare Content for HomeScene
    Button switchSceneHome2AccountsEditBtn = new Button();
    Button switchSceneHome2RecipiesBtn = new Button();
    Button switchSceneHome2eatenFoodBtn = new Button();
    Label homeHeaderLabel = new Label();
    Label homeBMILabel = new Label();
    Label homeNutriLabel = new Label();
    Label homeStreakLabel = new Label();
    Label homeHealthFactLabel = new Label();
    Label foodfactLabel = new Label();
    
    //Declare Content for Eaten Kcal Scene
    Button switchSceneFoodAdd2HomeBtn = new Button();
    Button addFoodBtn = new Button();
    Label foodEatHeaderLabel = new Label();
    ListView<String> foodItemList2 = new ListView<String>();
    Label choosenFoodLabel = new Label();
    TextField amountFood = new TextField();
    
    // Declare Content for RecipiesScene
    Button switchSceneRecipies2HomeBtn = new Button();
    Label recipiesHeaderLabel = new Label();
    ListView<String> foodItemList = new ListView<String>();
    Label recipyAddingFoodExplanationLabel = new Label();
    TextField recipyAddingFood = new TextField();
    Label recipyAddingFoodFeedbackLabel = new Label();
    Label recipyAddingRecipyExplanationLabel = new Label();
    TextField recipyAddingRecipy = new TextField();
    Label recipyAddingRecipyFeedbackLabel = new Label();

	// -----------------------------------------------------------------------------------

	@Override
    public void start(Stage stage)
	{
		// Set Variables for Panes
		mainMenuPane.getChildren().addAll(mainMenuPaneTop, mainMenuPaneCen, mainMenuPaneBot);
		mainMenuPaneTop.setPrefSize(300, topPanelSizeY);
		mainMenuPaneTop.setAlignment(Pos.CENTER);
		mainMenuPaneCen.setAlignment(Pos.CENTER);
		mainMenuPaneBot.setAlignment(Pos.CENTER);
		mainMenuPane.setMargin(mainMenuPaneBot, new javafx.geometry.Insets(200, 0, 0, 0));

		accountPane.getChildren().addAll(accountPaneTop, accountPaneCen, accountPaneBot);
		accountPaneTop.setPrefSize(300, topPanelSizeY);
		accountPaneTop.setAlignment(Pos.CENTER);
		accountPaneCen.setAlignment(Pos.CENTER);
		accountPaneBot.setAlignment(Pos.CENTER);
		accountPane.setMargin(accountPaneBot, new javafx.geometry.Insets(400, 0, 0, 0));
		
		accountEditPane.getChildren().addAll(accountEditPaneTop, accountEditPaneCen, accountEditPaneBot);
		accountEditPaneTop.setPrefSize(300, topPanelSizeY);
		accountEditPaneTop.setAlignment(Pos.CENTER);
		accountEditPaneCen.setAlignment(Pos.CENTER);
		accountEditPaneBot.setAlignment(Pos.CENTER);
		accountEditPaneGen.setAlignment(Pos.CENTER);
		accountEditPane.setMargin(accountEditPaneBot, new javafx.geometry.Insets(100, 0, 0, 0));
		
		homePane.getChildren().addAll(homePaneTop, homePaneCen, homePaneBot);
		homePaneTop.setPrefSize(300, topPanelSizeY);
		homePaneTop.setAlignment(Pos.CENTER);
		homePaneCen.setAlignment(Pos.CENTER);
		homePaneBot.setAlignment(Pos.CENTER);
		homePane.setMargin(homePaneBot, new javafx.geometry.Insets(400, 0, 0, 0));
		
		foodAddPane.getChildren().addAll(foodAddPaneTop, foodAddPaneCen, foodAddPaneBot);
		foodAddPaneTop.setPrefSize(300, topPanelSizeY);
		foodAddPaneTop.setAlignment(Pos.CENTER);
		foodAddPaneCen.setAlignment(Pos.CENTER);
		foodAddPaneBot.setAlignment(Pos.CENTER);
		foodAddPane.setMargin(foodAddPaneBot, new javafx.geometry.Insets(400, 0, 0, 0));
		
		recipiesPane.getChildren().addAll(recipiesPaneTop, recipiesPaneCen, recipiesPaneBot);
		recipiesPaneTop.setPrefSize(300, topPanelSizeY);
		recipiesPaneTop.setAlignment(Pos.CENTER);
		recipiesPaneCen.setAlignment(Pos.CENTER);
		recipiesPaneBot.setAlignment(Pos.CENTER);
		recipiesPane.setMargin(recipiesPaneBot, new javafx.geometry.Insets(0, 0, 0, 0));
		
		// Changes pane colors 
		mainMenuPane.setStyle(mainPaneColor);
		mainMenuPaneTop.setStyle(topPaneColor);
		
		accountPane.setStyle(mainPaneColor);
		accountPaneTop.setStyle(topPaneColor);
		
		accountEditPane.setStyle(mainPaneColor);
		accountEditPaneTop.setStyle(topPaneColor);
		
		homePane.setStyle(mainPaneColor);
		homePaneTop.setStyle(topPaneColor);

		foodAddPane.setStyle(mainPaneColor);
		foodAddPaneTop.setStyle(topPaneColor);
		
		recipiesPane.setStyle(mainPaneColor);
		recipiesPaneTop.setStyle(topPaneColor);

        
        // -----------------------------------------------------------------------------------
		// Create scene contents for mainMenuScene
        // Buttons:
		
	
		switchSceneMainMenu2AccountBtn.setText("Create new Account");
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
		accountListExplanationLabel.setText("Existing accounts:");
		accountListExplanationLabel.setFont(Font.font("Standart", 16d));

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
        	}
        	else
        	{
        		accountLogin.setText("");
        		loggedInAccountIndex = index;
        		updateHomeScene();
        		stage.setScene(homeScene);
        	}
        });
		accountLogin.setMaxWidth(400);
		// ListView
		accountsList.setItems(readAllAccounts("name"));
		accountsList.setMaxWidth(400);
		accountsList.setMaxHeight(100);

		mainMenuPaneTop.getChildren().addAll(mainMenuHeaderLabel);
		mainMenuPaneCen.getChildren().addAll(loginInstructionsLabel, accountLogin, loginFeedbackLabel, accountListExplanationLabel, accountsList);
		mainMenuPaneBot.getChildren().addAll(switchSceneMainMenu2AccountBtn);
        
		// -----------------------------------------------------------------------------------
        // Create scene contents for accountScene
		// Buttons:
		switchSceneAccounts2MainMenuBtn.setText("Login Menu");
		switchSceneAccounts2MainMenuBtn.setOnAction(e->
		{
			updateMainMenuScene();
			stage.setScene(mainMenuScene);
		});
		switchSceneAccounts2MainMenuBtn.setFont(Font.font("Standart", 16d));
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
        accountPaneBot.getChildren().addAll(switchSceneAccounts2MainMenuBtn);
        
        // -----------------------------------------------------------------------------------
        // Create scene contents for accountEditScene
		// Buttons:
		switchSceneAccountsEdit2HomeBtn.setText("Home Menu");
		switchSceneAccountsEdit2HomeBtn.setOnAction(e->
		{
    		updateHomeScene();
			stage.setScene(homeScene);
		});
		switchSceneAccountsEdit2HomeBtn.setFont(Font.font("Standart", 16d));
		// Labels:
		accountsEditHeaderLabel.setText("Settings");
		accountsEditHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
        accountsEditAddingExplanation.setText("Change your user data here:");
        accountsEditAddingExplanation.setFont(Font.font("Standart", 16d));
        
        String name = (readAccount(loggedInAccountIndex, "name"));
        accountsEditCurrentNameLabel.setText(name);
        accountsEditCurrentNameLabel.setFont(Font.font("Standart", 16d));
        String height = (readAccount(loggedInAccountIndex, "height"));
        accountsEditCurrentHeightLabel.setText(height + " m");
        accountsEditCurrentHeightLabel.setFont(Font.font("Standart", 16d));
        accountsEditCurrentWeightLabel.setText("");
        accountsEditCurrentWeightLabel.setFont(Font.font("Standart", 16d));
        String gender = (readAccount(loggedInAccountIndex, "gender"));
        accountsEditCurrentGenderLabel.setText(gender);
        accountsEditCurrentGenderLabel.setFont(Font.font("Standart", 16d));
        String goalweight = (readAccount(loggedInAccountIndex, "goal_weight"));
        accountsEditCurrentGoalWeightLabel.setText(goalweight+ " kg");
        accountsEditCurrentGoalWeightLabel.setFont(Font.font("Standart", 16d));
        
        accountsEditAddingNameFeedback.setText("");
        accountsEditAddingNameFeedback.setFont(Font.font("Standart", 16d));
        accountsEditAddingHeightFeedback.setText("");
        accountsEditAddingHeightFeedback.setFont(Font.font("Standart", 16d));
        accountsEditAddingGenderFeedback.setText("");
        accountsEditAddingGenderFeedback.setFont(Font.font("Standart", 16d));
        accountsEditAddingWeightFeedback.setText("");
        accountsEditAddingWeightFeedback.setFont(Font.font("Standart", 16d));
        accountsEditAddingGoalWeightFeedback.setText("");
        accountsEditAddingGoalWeightFeedback.setFont(Font.font("Standart", 16d));
        
        maleRBtn.setToggleGroup(genderGroup);
        maleRBtn.setFont(Font.font("Standart", 16d));
        femaleRBtn.setToggleGroup(genderGroup);
        femaleRBtn.setFont(Font.font("Standart", 16d));
        accountEditPaneGen.getChildren().addAll(maleRBtn,femaleRBtn);
        // Input for account creation
        accountEditInputName.setText("");
        accountEditInputName.setFont(Font.font("Standart", 16d));
        accountEditInputName.setOnAction(e->
        {
        	String inputValue = accountEditInputName.getText();
        	if(searchAccount(inputValue) != -1)
        	{
        		accountsEditAddingNameFeedback.setText("Account already exists!");
        	}
        	else
        	{
        		accountsEditAddingNameFeedback.setText("Done!");
        		editAccount(inputValue, 0);
        		updateAccountEditScene();
        		
        	}
        	
        	accountEditInputName.setText("");
        });
        accountEditInputName.setMaxWidth(400);
        accountEditInputHeight.setText("");
        accountEditInputHeight.setFont(Font.font("Standart", 16d));
        accountEditInputHeight.setOnAction(e->
        {
        	String inputValue = accountEditInputHeight.getText();
        	try
        	{
        		Float.parseFloat(inputValue);
        		editAccount(inputValue, 1);
        		accountsEditAddingHeightFeedback.setText("Done!");
        		updateAccountEditScene();
        	}
        	catch (NumberFormatException someError)
        	{
        		accountsEditAddingHeightFeedback.setText("Value not numerical!");
        	}
        	accountEditInputHeight.setText("");
        });
        accountEditInputHeight.setMaxWidth(400);
        maleRBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
        	editAccount("male", 2);
        	updateAccountEditScene();
        });
        femaleRBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
        	editAccount("female", 2);
        	updateAccountEditScene();
        });
        
        accountEditInputGender.setMaxWidth(400);
        
        accountEditInputWeight.setText("");
        accountEditInputWeight.setFont(Font.font("Standart", 16d));
        accountEditInputWeight.setOnAction(e->
        {
        	String inputValue = accountEditInputWeight.getText();
        	try
        	{
        		Float.parseFloat(inputValue);
            	editAccount(inputValue, 3);
        		accountsEditAddingWeightFeedback.setText("Done!");
        		updateAccountEditScene();
        	}
        	catch (NumberFormatException someError)
        	{
        		accountsEditAddingWeightFeedback.setText("Value not numerical!");
        	}
        	accountEditInputWeight.setText("");
        });
        accountEditInputWeight.setMaxWidth(400);
        
        accountEditInputGoalWeight.setText("");
        accountEditInputGoalWeight.setFont(Font.font("Standart", 16d));
        accountEditInputGoalWeight.setOnAction(e->
        {
        	String inputValue = accountEditInputGoalWeight.getText();
        	try
        	{
        		Float.parseFloat(inputValue);
            	editAccount(inputValue, 4);
        		accountsEditAddingGoalWeightFeedback.setText("Done!");
        		updateAccountEditScene();
        	}
        	catch (NumberFormatException someError)
        	{
        		accountsEditAddingGoalWeightFeedback.setText("Value not numerical!");
        	}
        	accountEditInputGoalWeight.setText("");
        });
        accountEditInputGoalWeight.setMaxWidth(400);
        
        accountEditPaneTop.getChildren().addAll(accountsEditHeaderLabel);// accountEditInputGender
        accountEditPaneCen.getChildren().addAll(accountsEditAddingExplanation, accountsEditCurrentNameLabel, accountEditInputName, accountsEditAddingNameFeedback,
        																	   accountsEditCurrentHeightLabel, accountEditInputHeight, accountsEditAddingHeightFeedback,
        																	   accountsEditCurrentGenderLabel,accountEditPaneGen, accountsEditAddingGenderFeedback,
        																	   accountsEditCurrentWeightLabel,accountEditInputWeight, accountsEditAddingWeightFeedback,
        																	   accountsEditCurrentGoalWeightLabel, accountEditInputGoalWeight, accountsEditAddingGoalWeightFeedback
        																	   );
        accountEditPaneBot.getChildren().addAll(switchSceneAccountsEdit2HomeBtn);
        
        // -----------------------------------------------------------------------------------
        // Create scene contents for homeScene
        // Buttons:
        switchSceneHome2AccountsEditBtn.setText("Settings");
        switchSceneHome2AccountsEditBtn.setOnAction(e->
        {
        	updateAccountEditScene();
			stage.setScene(accountEditScene);
		});
        switchSceneHome2AccountsEditBtn.setFont(Font.font("Standart", 16d));
        switchSceneHome2RecipiesBtn.setText("Recipies");
        switchSceneHome2RecipiesBtn.setOnAction(e->
        {
			stage.setScene(recipiesScene);
		});
        switchSceneHome2RecipiesBtn.setFont(Font.font("Standart", 16d));
        switchSceneHome2eatenFoodBtn.setText("Track Calories");
        switchSceneHome2eatenFoodBtn.setOnAction(e->
        {
        	stage.setScene(foodAddScene);
        }		
        );
        switchSceneHome2eatenFoodBtn.setFont(Font.font("Standart", 16d));
        // Labels:
        homeHeaderLabel.setText("Home");
        homeHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
        homeBMILabel.setText("");
        homeBMILabel.setFont(Font.font("Standart", 16d));
        homeNutriLabel.setText("");
        homeNutriLabel.setFont(Font.font("Standart", 16d));
        homeStreakLabel.setText("");
        homeStreakLabel.setFont(Font.font("Standart", 16d));
        foodfactLabel.setText(foodFact());
        foodfactLabel.setFont(Font.font("Standart", 16d));
        //foodfactLabel.setMaxSize(800, 150);
        foodfactLabel.setTextFill(Color.DARKVIOLET);
        homePaneTop.getChildren().addAll(homeHeaderLabel);
        homePaneCen.getChildren().addAll(homeBMILabel, homeNutriLabel,homeStreakLabel, switchSceneHome2eatenFoodBtn,foodfactLabel);
        homePaneBot.getChildren().addAll(switchSceneHome2AccountsEditBtn, switchSceneHome2RecipiesBtn);
		
        // -----------------------------------------------------------------------------------
        // Create scene contents for foodAddScene
        // Buttons:
        switchSceneFoodAdd2HomeBtn.setText("Home Menu");
        switchSceneFoodAdd2HomeBtn.setOnAction(e->
        {
        	stage.setScene(homeScene);
        });
        switchSceneFoodAdd2HomeBtn.setFont(Font.font("Standart", 16d));
        addFoodBtn.setText("Track Calories");
        addFoodBtn.setOnAction(e->
        {
        	String inputValue = amountFood.getText();
        	try
        	{
        		String choosenFood = new String("");
        		choosenFood = choosenFoodLabel.getText();
        		float weightfood = Float.parseFloat(inputValue);
        		String[] parts = choosenFood.split("\\|");
        		parts[1] = parts[1].trim();
        		//System.out.println(parts[1]);
        		String result = parts[1].substring(5, parts[1].length());
        	
        		float kcalper100gramm = Float.parseFloat(result);
        		float calculation = (weightfood/100) * (kcalper100gramm);
        		float dailyNutriKCal = Float.parseFloat(readAccount(loggedInAccountIndex, "nutritions_day"));
        		String dailyNutriKCalUpdatet = dailyNutriKCal +calculation + "";
        		editAccount(dailyNutriKCalUpdatet, 5);
        		
        		if(!readAccount(loggedInAccountIndex,"login").equals(LocalDate.now()+"")) {
        			editAccount(LocalDate.now()+"", 6);
        			int streak = Integer.parseInt(readAccount(loggedInAccountIndex, "streak")) + 1;
        			editAccount(streak + "", 7);
        		}
        		updateHomeScene();
        
        	}
        	catch (NumberFormatException someError)
        	{
        		
        	}
        	amountFood.setText("");
        });
        addFoodBtn.setFont(Font.font("Standart", 16d));
        //Labels:
        
        foodEatHeaderLabel.setText("Tracking Calories");
        foodEatHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
        choosenFoodLabel.setText("I ate ");
        choosenFoodLabel.setFont(Font.font("Standart", 16d));
        foodItemList2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Display the selected option in the Label
        	choosenFoodLabel.setText( newValue);
        });
        amountFood.setMaxWidth(400);
        
        // ListView
        foodItemList2.setItems(readAllFoodItems());
        foodItemList2.setMaxWidth(600);
        foodItemList2.setMaxHeight(250);
        
        foodAddPaneTop.getChildren().addAll(foodEatHeaderLabel);
        foodAddPaneCen.getChildren().addAll(foodItemList2, choosenFoodLabel, amountFood, addFoodBtn);
        foodAddPaneBot.getChildren().addAll(switchSceneFoodAdd2HomeBtn);
        // -----------------------------------------------------------------------------------
        // Create scene contents for recipiesScene
        // Buttons:
        switchSceneRecipies2HomeBtn.setText("Home Menu");
        switchSceneRecipies2HomeBtn.setOnAction(e->
        {
			stage.setScene(homeScene);
		});
        switchSceneRecipies2HomeBtn.setFont(Font.font("Standart", 16d));
        // Labels:
        recipiesHeaderLabel.setText("Recipies");
        recipiesHeaderLabel.setFont(Font.font("Standart", FontWeight.BOLD, 24d));
        recipyAddingFoodExplanationLabel.setText("Add new food item in this order:\n"
        								       + "name, nutritional value(seperated by commas)\n"
        								       + "this is meant for basic ingredients, not recipies");
        recipyAddingFoodExplanationLabel.setFont(Font.font("Standart", 16d));
        recipyAddingFoodFeedbackLabel.setText("");
        recipyAddingFoodFeedbackLabel.setFont(Font.font("Standart", 16d));
        
        recipyAddingRecipyExplanationLabel.setText("Add new recipy in this order:\n"
				   							   + "[name], [name of the ingredient Nr.1]-[ammount in grams], [name of the ingredient Nr.2]-[ammount in grams], ...(seperated by commas)\n"
		   									   + "this is meant for recipies");
        recipyAddingRecipyExplanationLabel.setFont(Font.font("Standart", 16d));
        recipyAddingRecipyFeedbackLabel.setText("");
        recipyAddingRecipyFeedbackLabel.setFont(Font.font("Standart", 16d));

        // Input for new food items
        recipyAddingFood.setText("");
        recipyAddingFood.setText("");
        recipyAddingFood.setFont(Font.font("Standart", 16d));
        recipyAddingFood.setOnAction(e->
        {
        	String inputValue = recipyAddingFood.getText();
        	try
        	{
        		inputValue += ",";
        		addFoodItem(inputValue);
            	recipyAddingFoodFeedbackLabel.setText("Done!");
        	}
        	catch (NumberFormatException someError)
        	{
        		recipyAddingFoodFeedbackLabel.setText("Error!");
        	}
        	recipyAddingFood.setText("");
        });
        recipyAddingFood.setMaxWidth(400);
        
        // Input for new recipies
        recipyAddingRecipy.setText("");
        recipyAddingRecipy.setText("");
        recipyAddingRecipy.setFont(Font.font("Standart", 16d));
        recipyAddingRecipy.setOnAction(e->
        {
        	String inputValue = recipyAddingRecipy.getText();
        	try
        	{
        		boolean ok = true;
        		String[] inputValues = inputValue.split(",");
        		float[] amountValues = new float[inputValues.length];
        		float totalKcal  = 0.0f;
        		float totalWeight = 0.0f;
        		for(int i = 1; i < inputValue.length(); ++i)
        		{
        			String[] inputValuesSplit = inputValues[i].split("-");
        			String foodItem = inputValuesSplit[0].strip();
        			String foodAmount = inputValuesSplit[1].strip();
        			if(searchFoodItem(foodItem) == -1)
        			{
        				ok = false;
                		recipyAddingRecipyFeedbackLabel.setText(inputValues[i] + "couldn't be found");
        				break;
        			}
        			else
        			{
        				totalWeight += Float.parseFloat(foodAmount);
        			}
        		}
        		for(int i = 1; i < inputValue.length(); ++i)
        		{
        			String[] inputValuesSplit = inputValues[i].split("-");
        			String foodItem = inputValuesSplit[0].strip();
        			String foodAmount = inputValuesSplit[1].strip();
        			
        			amountValues[i] = (100 / totalWeight) * Float.parseFloat(foodAmount);
        			
        			// TODO: 
        		}
        		if(ok)
        		{
        			addFoodItem(inputValue);
                	recipyAddingRecipyFeedbackLabel.setText("Done!");
        		}
        	}
        	catch (NumberFormatException someError)
        	{
        		recipyAddingRecipyFeedbackLabel.setText("Value not numerical!");
        	}
        	recipyAddingRecipy.setText("");
        });
        recipyAddingRecipy.setMaxWidth(400);
        
        // ListView
        foodItemList.setItems(readAllFoodItems());
        foodItemList.setMaxWidth(600);
        foodItemList.setMaxHeight(250);
        
        
        recipiesPaneTop.getChildren().addAll(recipiesHeaderLabel);
        recipiesPaneCen.getChildren().addAll(foodItemList, recipyAddingFoodExplanationLabel, recipyAddingFood, recipyAddingFoodFeedbackLabel,
        									 recipyAddingRecipyExplanationLabel, recipyAddingRecipy, recipyAddingRecipyFeedbackLabel);
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
    // Changes Calculations in the home scene, normally called when switching to this scene
    public void updateHomeScene()
    {
    	// Updates the BMI
    	float weight = Float.parseFloat(readAccount(loggedInAccountIndex, "weight"));
    	float height = Float.parseFloat(readAccount(loggedInAccountIndex, "height"));
    	float goal_weight = Float.parseFloat(readAccount(loggedInAccountIndex, "goal_weight"));
    	float bmi = weight / (float)Math.pow(height/100.0f, 2);
    	homeBMILabel.setText("Your BMI: " + String.format("%.1f" ,bmi));
    	if(bmi>=18.5 &&bmi<=24.9) {
    		homeBMILabel.setTextFill(Color.GREEN);
    	}else if(bmi<=16.5||bmi>=30){
    		homeBMILabel.setTextFill(Color.RED);
    	}else {
    		homeBMILabel.setTextFill(Color.ORANGE);
    	}
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
    	if(goal_weight>weight) {
    		dailyNutriKCalTotal += 500;
    	}else if(goal_weight<weight){
    		dailyNutriKCalTotal -= 500;
    	}
    	if(!readAccount(loggedInAccountIndex,"login").equals(LocalDate.now()+"")) {
    		editAccount("0", 5);
    	}
    	float dailyNutriKCalCurrent = Float.parseFloat(readAccount(loggedInAccountIndex, "nutritions_day"));
    	float dailyNutriKCalDelta = dailyNutriKCalTotal - dailyNutriKCalCurrent;
    	homeNutriLabel.setText("Kcal per day for you: " + String.format("%.0f", dailyNutriKCalTotal) +
    						 "\nKcal already consumed today: " + String.format("%.0f", dailyNutriKCalCurrent) +
    						 "\nKcal still left for today: " + String.format("%.0f", dailyNutriKCalDelta));
    	int streak = Integer.parseInt(readAccount(loggedInAccountIndex, "streak"));
    	homeStreakLabel.setText("Your Streak: "+ streak+ " days");
    	//homeStreakLabel.setText("You used this application on " + String.format("%.0f", streak)+ " different days already.");
    	
    }
    
    // -----------------------------------------------------------------------------------
    // Changes MainMenuScene
    
    // -----------------------------------------------------------------------------------
    public void updateMainMenuScene()
    {
    	// Updates the AccountsList
    	accountsList.setItems(readAllAccounts("name"));
    }
    // -----------------------------------------------------------------------------------
	// Changes AccountEditScene
    public void updateAccountEditScene() {
    	accountsEditCurrentWeightLabel.setText("Current weight " + readAccount(loggedInAccountIndex, "weight") + " kg");
    	accountsEditCurrentHeightLabel.setText("Current height " + readAccount(loggedInAccountIndex, "height") + " cm");
    	accountsEditCurrentGenderLabel.setText("Current Gender: " + readAccount(loggedInAccountIndex, "gender"));   	
    	accountsEditCurrentNameLabel.setText("Current name "+readAccount(loggedInAccountIndex, "name"));
    	accountsEditCurrentGoalWeightLabel.setText("Current goal weight " + readAccount(loggedInAccountIndex, "goal_weight") + " kg");
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
 			// Reading data from record
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
 			e.printStackTrace();
 		}
 		
 		return result;
 	}
  	
  	// -----------------------------------------------------------------------------------
	// Returns value of given data from all accounts from the csv file
  	
  	 // -----------------------------------------------------------------------------------
	// Returns value of given data from all accounts from the csv file
  	public ObservableList<String> readAllAccounts(String data)
 	{
 		// Returns all names from the csv file
  		 ObservableList<String> result = FXCollections.observableArrayList();
 		try (Reader reader = Files.newBufferedReader(Paths.get(accountCsvFile));
 				@SuppressWarnings("deprecation")
 				CSVParser csvParser = new CSVParser(reader,
 						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());)
 		{
 			// Reading data from record
 			for (CSVRecord csvRecord : csvParser)
 			{
				result.add(csvRecord.get(data));
 			}
 			
 			csvParser.close();
 			reader.close();
 		}
 		catch (IOException e)
 		{
 			result.add("Error, Accounts data " + data + "not found");
 			e.printStackTrace();
 		}
 		
 		return result;
 	}
  	
    // -----------------------------------------------------------------------------------
  	  	
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
 			
 			account = account + ",0,0,0";
 			String[] accountData = account.split(",");
 			// Adds account only if it didn't already exist
 			if(searchAccount(accountData[0].strip()) == -1)
 			{
 	 			csvPrinter.printRecord(accountData[0].strip(), accountData[1].strip(), accountData[2].strip(), accountData[3].strip(), accountData[4].strip(), accountData[5].strip(),accountData[6].strip(), accountData[7].strip());
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
 	
 	// -----------------------------------------------------------------------------------
 	// Changes the dataIndex from the currently logged in account to the newEntry
  	public void editAccount(String newEntry, int dataIndex)
  	{
		ArrayList<String[]> csvBody = new ArrayList<String[]>();

		try (Reader reader = Files.newBufferedReader(Paths.get(accountCsvFile));
  				@SuppressWarnings("deprecation")
 				CSVParser csvParser = new CSVParser(reader,
				CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());)
 		{
	  		// Copy whole file into String Array
 			for(CSVRecord csvRecord : csvParser)
 			{
 				csvBody.add(new String[] { csvRecord.get(0), csvRecord.get(1), csvRecord.get(2), csvRecord.get(3), csvRecord.get(4), csvRecord.get(5), csvRecord.get(6), csvRecord.get(7)});
 			}
 			
 			// Clear whole file
 			
 			
 			// Edit entry
 			String[] recordToEdit = csvBody.get(loggedInAccountIndex);
 			recordToEdit[dataIndex] = newEntry.strip();
 			csvBody.set(loggedInAccountIndex, recordToEdit);
 			
 			reader.close();
 		}
 		catch (IOException e)
 		{
 			e.printStackTrace();
 		}
  		
  		
  		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(accountCsvFile));
  				@SuppressWarnings("deprecation")
  				CSVPrinter csvPrinter = new CSVPrinter(writer,
				CSVFormat.DEFAULT.withFirstRecordAsHeader());)
  		{
  			// Print header row
  			csvPrinter.printRecord(("name"), ("height"), ("gender"), ("weight"), ("goal_weight"), ("nutritions_day"), ("login"), ("streak"));
  			
  			// Print whole String Array back into file
			for(int i = 0; i < csvBody.size(); ++i)
			{
 	 			csvPrinter.printRecord(csvBody.get(i)[0], csvBody.get(i)[1], csvBody.get(i)[2], csvBody.get(i)[3], csvBody.get(i)[4], csvBody.get(i)[5], csvBody.get(i)[6], csvBody.get(i)[7]);
			}
  			
  			csvPrinter.close();
  			writer.close();
  			
  		}
  		catch (IOException e)
  		{
  			e.printStackTrace();
  		}
  	}
  	
  	// -----------------------------------------------------------------------------------
	// Returns all food items from csv
  	public ObservableList<String> readAllFoodItems()
 	{
 		// Returns all names from the csv file
  		ObservableList<String> result = FXCollections.observableArrayList();
 		try (Reader reader = Files.newBufferedReader(Paths.get(foodItemCsvFile));
 				@SuppressWarnings("deprecation")
 				CSVParser csvParser = new CSVParser(reader,
 						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());)
 		{
 			// Reading data from record
 			for (CSVRecord csvRecord : csvParser)
 			{
 				if(csvRecord.get(2).isEmpty())
 				{
 					result.add(csvRecord.get(0) + " | Kcal: " +  csvRecord.get(1));
 				}
 				else
 				{
 					String ingredientsOutputString = "";
 					String ingredientsOneString = csvRecord.get(2);
 					String[] ingredientsArray = ingredientsOneString.split(" ");
 					for(int i = 0; i < ingredientsArray.length; ++i)
 					{
 						String[] ingredientAmountSep = ingredientsArray[i].split("_");
 						ingredientsOutputString += ", " + ingredientAmountSep[1] + "g " + ingredientAmountSep[0];
 					}
 					result.add(csvRecord.get(0) + " | Kcal: " +  csvRecord.get(1) + " | Ingredients: " + ingredientsOutputString);
 				}
 			}
 			
 			csvParser.close();
 			reader.close();
 		}
 		catch (IOException e)
 		{
 			result.add("Error");
 			e.printStackTrace();
 		}
 		
 		return result;
 	}
  	
  	// -----------------------------------------------------------------------------------
 	
  	public int addFoodItem(String foodItem)
  	{
  		// Adds a new account record to the accounts csv
  		int returnStatus = -1;
  		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(foodItemCsvFile), StandardOpenOption.APPEND);
  				@SuppressWarnings("deprecation")
  				CSVPrinter csvPrinter = new CSVPrinter(writer,
  						CSVFormat.DEFAULT.withFirstRecordAsHeader());)
  		{
  			// Add record after removing leading and trailing spaces
  			String[] foodData = foodItem.split(",");
  			// Adds account only if it didn't already exist
  			if(searchFoodItem(foodData[0].strip()) == -1)
  			{
  	 			csvPrinter.printRecord(foodData[0].strip(), foodData[1].strip(), foodData[2].strip(), foodData[3].strip(), foodData[4].strip(), foodData[5].strip());
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
  		  	
  	// -----------------------------------------------------------------------------------
  	// Searches for Food item by Name and returns index of found record; -1 if not found
  	public int searchFoodItem(String name)
  	{
  		// Returns all names from the csv file
  		try (Reader reader = Files.newBufferedReader(Paths.get(foodItemCsvFile));
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
  			e.printStackTrace();
  		}
  		
  		return (-1);
  	}
  	
  	// -------------------------------------------------------------------------------------
  	// Pick a random food Fact and return the String
  	public String foodFact() {
  	// Returns all names from the csv file
  		try (Reader reader = Files.newBufferedReader(Paths.get(factsCsvFile));
  				@SuppressWarnings("deprecation")
  				CSVParser csvParser = new CSVParser(reader,
  						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());)
  		{
  			// Reading
  			Random rand = new Random();
  			String index = rand.nextInt(18)+1+"";
  			System.out.println(index);
  			for (CSVRecord csvRecord : csvParser)
  			{
  				String indexCheck = csvRecord.get("index");
  				System.out.println(indexCheck);
  				if(indexCheck.equals(index))
  				{
  					return csvRecord.get(1);
  				}
  			}
  			
  			csvParser.close();
  			reader.close();
  		}
  		catch (IOException e)
  		{
  			e.printStackTrace();
  		}
  		return "couldnt generate a fact" ;
  	}
  	
}