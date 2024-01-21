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
// JavaFX
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;


// JavaFX




// ---------------------------------------------------------------------------------------

public class App extends Application {

    // ---------------------------------------------------------------------------------------
    // Constants:

    private static final int 	screenSizeX = 800;
    private static final int 	screenSizeY = 700;
    Font BTFont = new Font("Standard", 16);
    Font LAFont = new Font("Standard", 12);

    // -----------------------------------------------------------------------------------

    // Declare Panes

    @Override
    public void start(Stage stage)
    {

        AccountFile ACFile = new AccountFile("src/main/resources/accounts.csv");
        AccountData ACData = new AccountData();
        zweitesFoodfile FIFile = new zweitesFoodfile("src/main/resources/food_items.csv");
        FoodfactsFile FFFile = new FoodfactsFile();


        //Oberfläche für Main Menü
        //Declare Content for MainMenuScene

        BackgroundFill backroundfill = new BackgroundFill(Color.LIGHTGREEN, null, new Insets(100, 0, 0, 0));
        Background background = new Background(backroundfill);

        Label menuHeadlineLa = new Label("Main Menu");
        {
            menuHeadlineLa.setFont(Font.font("Standard", 24d));
        }

        Label menuTextLa = new Label("Select the Account you want to login with or create a new Account");
        {
            menuTextLa.setFont(BTFont);
        }

        Label menuExistingLa = new Label("Existing Accounts:");
        {
            menuExistingLa.setFont(BTFont);
        }



        ListView<String> menuListView = new ListView<>();
        {
            menuListView.setItems(ACFile.readAllAccounts("name"));
            menuListView.setPrefHeight(100);
            menuListView.setMaxWidth(200);
        }

        Button menuLoginBt = new Button("Login");
        {
            menuLoginBt.setFont(BTFont);
        }
        Label menuLoginFailLA = new Label();
        {
            menuLoginFailLA.setFont(BTFont);
        }

        VBox menuLoginVBox = new VBox(10, menuLoginBt, menuLoginFailLA);
        {
            menuLoginVBox.setAlignment(Pos.CENTER);
        }

        Button menuCreateBt = new Button("Create new Account");
        {
            menuCreateBt.setFont(BTFont);
        }

        /*
        HBox menuHBox = new HBox(50, menuLoginVBox, menuCreateBt);
        {
            menuHBox.setAlignment(Pos.TOP_CENTER);
            menuHBox.setTranslateY(15);
        }*/

        VBox menuVBox = new VBox(50, menuHeadlineLa, menuTextLa, menuExistingLa, menuListView, menuCreateBt, menuLoginVBox);
        {
            menuVBox.setAlignment(Pos.TOP_CENTER);
            menuVBox.setTranslateY(30);
        }

        StackPane menuPane = new StackPane(menuVBox);
        menuPane.setBackground(background);


        //Oberfläche für new Account
        //Declare Content for newAccountScene

        Label newAccHeadlineLa = new Label("New Account");
        {
            newAccHeadlineLa.setFont(Font.font("Standard", 24));
        }

        Label newAccTextLa = new Label("Add the details of your new Account");
        {
            newAccTextLa.setFont(BTFont);
        }

        Label newAccFailure = new Label("");
        {
            newAccFailure.setFont(BTFont);
        }

        Button newAccConfirmLA = new Button("Confirm");
        {
            newAccConfirmLA.setFont(BTFont);
        }

        Button newAccHomeMBt = new Button("Login Menu");
        {
            newAccHomeMBt.setFont(BTFont);
        }

        Label newAccNameLA = new Label("Name:");
        {
            newAccNameLA.setFont(BTFont);
        }
        TextField newAccNameTF = new TextField();
        {
            newAccNameTF.setMaxWidth(400);
            newAccNameTF.setPromptText("name");
        }
        Label newAccNameFailLA = new Label();

        Label newAccHeightLA = new Label("Height:");
        {
            newAccHeightLA.setFont(BTFont);
        }
        TextField newAccHeightTF = new TextField();
        {
            newAccHeightTF.setMaxWidth(400);
            newAccHeightTF.setPromptText("height in cm");
        }
        Label newAccHeightFailLA = new Label();

        Label newAccGenderLA = new Label("Gender:");
        {
            newAccGenderLA.setFont(BTFont);
        }
        TextField newAccGenderTF = new TextField();
        {
            newAccGenderTF.setMaxWidth(400);
            newAccGenderTF.setPromptText("gender: female or male");
        }
        Label newAccGenderFailLA = new Label();

        Label newAccWeightLA = new Label("Weight:");
        {
            newAccWeightLA.setFont(BTFont);
        }
        TextField newAccWeightTF = new TextField();
        {
            newAccWeightTF.setMaxWidth(400);
            newAccWeightTF.setPromptText("weight in kg");
        }
        Label newAccWeightFailLA = new Label();

        Label newAccGoalWeightLA = new Label("Goal Weight:");
        {
            newAccGoalWeightLA.setFont(BTFont);
        }
        TextField newAccGoalWeightTF = new TextField();
        {
            newAccGoalWeightTF.setMaxWidth(400);
            newAccGoalWeightTF.setPromptText("goal weight in kg");
        }
        Label newAccGoalWeightFailLA = new Label();


        VBox newAccNameVBox = new VBox( newAccNameLA, newAccNameTF, newAccNameFailLA);
        newAccNameVBox.setAlignment(Pos.CENTER);
        VBox newAccGenderVBox = new VBox( newAccGenderLA, newAccGenderTF, newAccGenderFailLA);
        newAccGenderVBox.setAlignment(Pos.CENTER);
        VBox newAccHeightVBox = new VBox(newAccHeightLA, newAccHeightTF, newAccHeightFailLA);
        newAccHeightVBox.setAlignment(Pos.CENTER);
        VBox newAccWeightVBox = new VBox(newAccWeightLA, newAccWeightTF, newAccWeightFailLA);
        newAccWeightVBox.setAlignment(Pos.CENTER);
        VBox newAccGoalWeightVBox = new VBox(newAccGoalWeightLA, newAccGoalWeightTF, newAccGoalWeightFailLA);
        newAccGoalWeightVBox.setAlignment(Pos.CENTER);

        VBox newAccInputVBox = new VBox(5, newAccNameVBox, newAccGenderVBox, newAccHeightVBox, newAccWeightVBox, newAccGoalWeightVBox);

        VBox newAccBodyVBox = new VBox(15, newAccTextLa, newAccInputVBox, newAccFailure, newAccConfirmLA, newAccHomeMBt);
        {
            newAccBodyVBox.setAlignment(Pos.CENTER);
        }

        VBox newAccVBox = new VBox(50, newAccHeadlineLa, newAccBodyVBox);
        {
            newAccVBox.setAlignment(Pos.TOP_CENTER);
            newAccVBox.setTranslateY(30);
        }

        //HBox newAccHBox = new HBox(newAccHomeMBt);{
        //newAccHBox.setAlignment(Pos.BOTTOM_LEFT);}

        StackPane newAccPane = new StackPane(newAccVBox);
        {
            newAccPane.setBackground(background);
        }


        //Oberfläche für Home
        //Declare Content for homeScene

        Label homeHeadlineLa = new Label("Home");
        {
            homeHeadlineLa.setFont(Font.font("Standard", 24));
        }

        Label homeBMILa = new Label();
        {
            homeBMILa.setFont(BTFont);
        }

        Label homeNutriLa = new Label();
        {
            homeNutriLa.setFont(BTFont);
        }

        Label homeStreakLa = new Label();
        {
            homeStreakLa.setFont(BTFont);
        }

        Label homefoodfactLa = new Label(FFFile.foodFact());
        {
            homefoodfactLa.setFont(Font.font("Standard", 16));
            homefoodfactLa.setTextFill(Color.DARKOLIVEGREEN);
        }

        Button home2settings = new Button("Settings");
        {
            home2settings.setFont(BTFont);
        }
        Button home2TrackCalories = new Button("Track Calories");
        {
            home2TrackCalories.setFont(BTFont);
        }
        Button home2recipes = new Button("Recipes");
        {
            home2recipes.setFont(BTFont);
        }

        Button home2menu = new Button("Logout");
        {
            home2menu.setFont(BTFont);
        }

        HBox homeHBox = new HBox(25, home2settings, home2recipes);
        {
            homeHBox.setAlignment(Pos.BOTTOM_CENTER);
            //homeHBox.setTranslateY(0);
        }

        VBox homeVBox = new VBox(40, homeHeadlineLa, homeBMILa, homeNutriLa, homeStreakLa, home2TrackCalories, homefoodfactLa, homeHBox, home2menu);
        {
            homeVBox.setAlignment(Pos.TOP_CENTER);
            homeVBox.setTranslateY(30);
        }

        StackPane homePane = new StackPane(homeVBox);
        homePane.setBackground(background);


        //Oberfläche für tracking
        //Declare Content for trackingScene


        Button tracking2homeBT = new Button("Home");
        {
            tracking2homeBT.setFont(BTFont);
        }
        Button trackingAddFoodBT = new Button("Track Calories");
        {
            trackingAddFoodBT.setFont(BTFont);
        }
        Label trackingHeadlineLA = new Label("Tracking Calories");
        {
            trackingHeadlineLA.setFont(Font.font("Standard", 24));
        }
        Label trackingChoosenFoodLA = new Label("I ate");
        {
            trackingChoosenFoodLA.setFont(BTFont);
        }

        ListView<String> trackingFoodItemLV = new ListView<>();
        {
            trackingFoodItemLV.setItems(FIFile.readAllFoodItems());
            trackingFoodItemLV.setPrefHeight(150);
            trackingFoodItemLV.setMaxWidth(400);
        }

        TextField trackingAmountTF = new TextField();
        {
            trackingAmountTF.setPromptText("amount of food eaten in grams");
            trackingAmountTF.setMaxWidth(400);
        }

        HBox trackingHBox = new HBox(tracking2homeBT);
        {
            trackingHBox.setAlignment(Pos.BOTTOM_CENTER);
        }

        VBox trackingVBox = new VBox(50, trackingHeadlineLA, trackingFoodItemLV, trackingChoosenFoodLA, trackingAmountTF, trackingAddFoodBT, trackingHBox);
        {
            trackingVBox.setAlignment(Pos.TOP_CENTER);
            trackingVBox.setTranslateY(30);
        }

        StackPane trackingPane = new StackPane(trackingVBox);
        trackingPane.setBackground(background);


        //Oberfläche für settingsScene
        //Declare Content for settingsScene
        Button settingsEdit2HomeBT = new Button("Home");
        settingsEdit2HomeBT.setFont(BTFont);

        Label settingsHeadlineLa = new Label("Settings");
        settingsHeadlineLa.setFont(Font.font("Standard", 24));

        Label settingsNameLa = new Label("Change your user data here:\n" +
                "Current name: " + ACFile.readAccount(ACData.getIndex_(), "name"));
        TextField settingsNameTF = new TextField();
        {
            settingsNameTF.setMaxWidth(300);
            settingsNameLa.setFont(BTFont);

        }//edit Name

        Label settingsHeightLa = new Label("Current height: " + ACFile.readAccount(ACData.getIndex_(), "height"));
        TextField settingsHeightTF = new TextField();
        {
            settingsHeightTF.setMaxWidth(300);
            settingsHeightLa.setFont(BTFont);

        }//edit height

        Label settingsGenderLa = new Label("Current gender: " + ACFile.readAccount(ACData.getIndex_(), "gender"));
        RadioButton settingsMaleRB = new RadioButton("male");
        RadioButton settingsFemaleRB = new RadioButton("female");
        ToggleGroup settingGenderTG = new ToggleGroup();
        {
            settingsGenderLa.setFont(BTFont);

            settingsMaleRB.setToggleGroup(settingGenderTG);


            settingsFemaleRB.setToggleGroup(settingGenderTG);
        }//edit gender

        Label settingsWeightLa = new Label("Current weight: " + ACFile.readAccount(ACData.getIndex_(), "weight"));
        TextField settingsWeightTF = new TextField();
        {
            settingsWeightTF.setMaxWidth(300);
            settingsWeightLa.setFont(BTFont);

        }//edit weight

        Label settingsGoalWeightLa = new Label("Current goal weight: " + ACFile.readAccount(ACData.getIndex_(), "goal_weight"));
        TextField settingsGoalWeightTF = new TextField();
        {
            settingsGoalWeightTF.setMaxWidth(300);
            settingsGoalWeightLa.setFont(BTFont);

        }//edit goal weight

        VBox settingNameVBox = new VBox(settingsNameLa, settingsNameTF);
        settingNameVBox.setAlignment(Pos.TOP_CENTER);
        VBox settingHeightVBox = new VBox(settingsHeightLa, settingsHeightTF);
        settingHeightVBox.setAlignment(Pos.TOP_CENTER);
        HBox settingGenderHBox = new HBox(settingsFemaleRB, settingsMaleRB);
        settingGenderHBox.setAlignment(Pos.TOP_CENTER);
        VBox settingGenderVBox = new VBox(settingsGenderLa, settingGenderHBox);
        settingGenderVBox.setAlignment(Pos.TOP_CENTER);
        VBox settingWeightVBox = new VBox(settingsWeightLa, settingsWeightTF);
        settingWeightVBox.setAlignment(Pos.TOP_CENTER);
        VBox settingGoalWeightVBox = new VBox(settingsGoalWeightLa, settingsGoalWeightTF);
        settingGoalWeightVBox.setAlignment(Pos.TOP_CENTER);

        VBox settingsVBox = new VBox(50, settingsHeadlineLa, settingNameVBox, settingHeightVBox, settingGenderVBox, settingWeightVBox, settingGoalWeightVBox, settingsEdit2HomeBT);
        settingsVBox.setAlignment(Pos.TOP_CENTER);
        settingsVBox.setTranslateY(30);

        StackPane editPane = new StackPane(settingsVBox);
        editPane.setBackground(background);

        //Recipe scene
        Label recipeHeadlineLa = new Label("Recipes");
        {
            recipeHeadlineLa.setFont(Font.font("Standard", 24));
        }

        ListView recipeListView = new ListView<>();
        {
            recipeListView.setItems(FIFile.readAllFoodItems());
            recipeListView.setPrefHeight(100);
            recipeListView.setMaxWidth(400);
        }
/*
        Label recipeNewFoodLa = new Label("""
                Add new food item in this order:
                [name], [kcal per 100g] (seperated by commas)
                this is meant for basic ingredients, not recipes""");
        {
            recipeNewFoodLa.setFont(BTFont);
        }

        TextField recipeNewFoodTF = new TextField("");
        {
            recipeNewFoodTF.setMaxWidth(400);
        }

       */
        /*
        Label recipeNewFoodNameLA = new Label("Name:");
        {
            recipeNewFoodNameLA.setFont(BTFont);
        }
        TextField recipeNewFoodNameTF = new TextField();
        {
            recipeNewFoodNameTF.setMaxWidth(400);
            recipeNewFoodNameTF.setPromptText("name");
        }
        Label recipeNewFoodNameFail = new Label();

        Label recipeNewFoodKcalLA = new Label("Kcal:");
        {
            recipeNewFoodKcalLA.setFont(BTFont);
        }
        TextField recipeNewFoodKcalTF = new TextField();
        {
            recipeNewFoodKcalTF.setMaxWidth(400);
            recipeNewFoodKcalTF.setPromptText("kcal per 100g");
        }
        Label recipeNewFoodKcalFail = new Label();

*/

        Label recipeNewRecipeNameLA = new Label("Name:");
        {
            recipeNewRecipeNameLA.setFont(BTFont);
        }
        TextField recipeNewRecipeNameTF = new TextField();
        {
            recipeNewRecipeNameTF.setMaxWidth(400);
            recipeNewRecipeNameTF.setPromptText("name");
        }
        Label recipeNewRecipeNameFail = new Label();

        Label recipeNewRecipeKcalLA = new Label("Kcal:");
        {
            recipeNewRecipeKcalLA.setFont(BTFont);
        }
        TextField recipeNewRecipeKcalTF = new TextField();
        {
            recipeNewRecipeKcalTF.setMaxWidth(400);
            recipeNewRecipeKcalTF.setPromptText("kcal per 100g");
        }
        Label recipeNewRecipeKcalFail = new Label();

        Label recipeNewRecipeIngLA1 = new Label("Ingredients:");
        Label recipeNewRecipeIngLA2 = new Label("Can be left empty when adding an elementary food item");
        Label recipeNewRecipeIngLA3 = new Label("Please enter your list by separating\nthe name from the amount '_'\nthe different ingredients with ' '");
        {
            recipeNewRecipeIngLA1.setFont(BTFont);
            recipeNewRecipeIngLA2.setFont(LAFont);
            recipeNewRecipeIngLA3.setFont(LAFont);
        }
        TextField recipeNewRecipeIngTF = new TextField();
        {
            recipeNewRecipeIngTF.setMaxWidth(400);
            recipeNewRecipeIngTF.setPromptText("[name of ingredient]_[amount in grams] [...");
        }

        Label recipeNewRecipeFailLA = new Label();
        {
            recipeNewRecipeFailLA.setFont(BTFont);
        }


        Button recipeAddBt = new Button("Confirm");
        {
            recipeAddBt.setFont(BTFont);
        }
        /*
        Label recipeNewRecipeLa = new Label("""
                Add new recipe in this order:
                [name], [kcal per 100g of this meal],
                [name of the ingredient Nr.1]_[ammount in grams] ...
                (name, kcal, ingredientlist seperated by commas)
                (ingredients seperated by blank)
                this is meant for recipes""");
        {
            recipeNewRecipeLa.setFont(BTFont);
        }
        TextField recipeNewRecipeTF = new TextField("");
        {
            recipeNewRecipeTF.setMaxWidth(400);
        }
*/
        Button recipe2homeBT = new Button("Home Menu");
        {
            recipe2homeBT.setFont(BTFont);
        }

/*
        VBox recipeNewFoodNameVBox = new VBox(recipeNewFoodNameLA, recipeNewFoodNameTF, recipeNewFoodNameFail);
        {
            recipeNewFoodNameVBox.setAlignment(Pos.CENTER);
        }
        VBox recipeNewFoodKcalVBox = new VBox(recipeNewFoodKcalLA, recipeNewFoodKcalTF, recipeNewFoodKcalFail);
        {
            recipeNewFoodKcalVBox.setAlignment(Pos.CENTER);
        }

        VBox recipeNewFoodInputVBox = new VBox(5, recipeNewFoodNameVBox, recipeNewFoodKcalVBox );
*/


        VBox recipeNewRecipeNameVBox = new VBox(recipeNewRecipeNameLA, recipeNewRecipeNameTF, recipeNewRecipeNameFail);
        {
            recipeNewRecipeNameVBox.setAlignment(Pos.CENTER);
        }
        VBox recipeNewRecipeKcalVBox = new VBox(recipeNewRecipeKcalLA, recipeNewRecipeKcalTF, recipeNewRecipeKcalFail);
        {
            recipeNewRecipeKcalVBox.setAlignment(Pos.CENTER);
        }
        VBox recipeNewRecipeIngVBox = new VBox(recipeNewRecipeIngLA1, recipeNewRecipeIngLA2, recipeNewRecipeIngLA3, recipeNewRecipeIngTF);
        {
            recipeNewRecipeIngVBox.setAlignment(Pos.CENTER);
        }

        VBox recipeNewRecipeInputVBox = new VBox(5, recipeNewRecipeNameVBox, recipeNewRecipeKcalVBox, recipeNewRecipeIngVBox ,recipeNewRecipeFailLA );
        {
            recipeNewRecipeInputVBox.setAlignment(Pos.CENTER);
        }


      //  HBox recipeAddingHbox = new HBox(50, recipeNewFoodInputVBox, recipeNewRecipeInputVBox);
/*
        VBox recipeVBox = new VBox(35, recipeHeadlineLa, recipeListView, recipeNewFoodLa, recipeNewFoodTF, recipeNewRecipeLa, recipeNewRecipeTF, recipe2homeBT);
        recipeVBox.setAlignment(Pos.TOP_CENTER);
        recipeVBox.setTranslateY(30);
*/
        VBox recipeVBox = new VBox(40, recipeHeadlineLa, recipeListView, recipeNewRecipeInputVBox, recipeAddBt, recipe2homeBT);
        recipeVBox.setAlignment(Pos.TOP_CENTER);
        recipeVBox.setTranslateY(30);

        StackPane recipePane = new StackPane(recipeVBox);
        recipePane.setBackground(background);


//Scenes
        Scene trackingScene = new Scene(trackingPane, screenSizeX, screenSizeY);
        Scene homeScene = new Scene(homePane, screenSizeX, screenSizeY);
        Scene newAccountScene = new Scene(newAccPane, screenSizeX, screenSizeY);
        Scene mainMenuScene = new Scene(menuPane, screenSizeX, screenSizeY);
        Scene editScene = new Scene(editPane, screenSizeX, screenSizeY);
        Scene recipeScene = new Scene(recipePane, screenSizeX, screenSizeY);

//Textfield

        settingsHeightTF.setOnAction(e -> {
            try {
                float height = Float.parseFloat(settingsHeightTF.getText());
                ACFile.editAccount(settingsHeightTF.getText(), 1, ACData.getIndex_());
                ACData.update_edit(ACFile, settingsNameLa, settingsHeightLa, settingsGenderLa, settingsWeightLa, settingsGoalWeightLa);
                settingsHeightTF.setText("");
            }
            catch(NumberFormatException n){
                settingsHeightTF.setText("Die Eingabe muss numerisch sein.");
            }
        });

        settingsNameTF.setOnAction(e -> {
            ACFile.editAccount(settingsNameTF.getText(), 0, ACData.getIndex_());
            ACData.update_edit(ACFile, settingsNameLa, settingsHeightLa, settingsGenderLa, settingsWeightLa, settingsGoalWeightLa);
            settingsNameTF.setText("");
        });

        settingsGoalWeightTF.setOnAction(e -> {
            try{
                float weight = Float.parseFloat(settingsGoalWeightTF.getText());
                ACFile.editAccount(settingsGoalWeightTF.getText(), 4, ACData.getIndex_());
                ACData.update_edit(ACFile,settingsNameLa, settingsHeightLa, settingsGenderLa, settingsWeightLa, settingsGoalWeightLa);
                settingsGoalWeightTF.setText("");
            }
            catch(NumberFormatException n){
                settingsGoalWeightTF.setText("Die Eingabe muss numerisch sein.");
            }
        });

        settingsWeightTF.setOnAction(e -> {
            try{
                float weight = Float.parseFloat(settingsWeightTF.getText());
                ACFile.editAccount(settingsWeightTF.getText(), 3, ACData.getIndex_());
                ACData.update_edit(ACFile,settingsNameLa, settingsHeightLa, settingsGenderLa, settingsWeightLa, settingsGoalWeightLa);
                settingsWeightTF.setText("");
            }
            catch(NumberFormatException n){
                settingsWeightTF.setText("Die Eingabe muss numerisch sein.");
            }
        });

        /*
        recipeNewFoodTF.setOnAction(e -> {
            FIFile.addFoodItem(recipeNewFoodTF.getText());
            recipeNewFoodTF.setText("");
            recipeListView.setItems(FIFile.readAllFoodItems());
        });
        recipeNewRecipeTF.setOnAction(e -> {
            FIFile.addFoodItem(recipeNewRecipeTF.getText());
            recipeNewRecipeTF.setText("");
            recipeListView.setItems(FIFile.readAllFoodItems());
        });

         */

//Radiobuttons

        settingsMaleRB.setOnAction(e -> {
            ACFile.editAccount(settingsMaleRB.getText(), 2, ACData.getIndex_());
            ACData.update_edit(ACFile, settingsNameLa, settingsHeightLa, settingsGenderLa, settingsWeightLa, settingsGoalWeightLa);
        });

        settingsFemaleRB.setOnAction(e -> {
            ACFile.editAccount(settingsFemaleRB.getText(), 2, ACData.getIndex_());
            ACData.update_edit(ACFile, settingsNameLa, settingsHeightLa, settingsGenderLa, settingsWeightLa, settingsGoalWeightLa);
        });

//Buttons
        menuLoginBt.setOnAction(e -> {
            menuLoginFailLA.setText("");
            String inputValue = menuListView.getSelectionModel().getSelectedItem();
            if(inputValue == null){
                menuLoginFailLA.setText("Please select an account");
            }
            else{
                stage.setScene(homeScene);
                int index = ACFile.searchAccount(inputValue);
                ACData.setIndex(index);
                ACData.update_account(ACFile, homeBMILa, homeNutriLa, homeStreakLa);

                ACData.update_streak(ACFile);
                ACData.update_kcal(ACFile,0);
                ACData.update_account(ACFile, homeBMILa, homeNutriLa, homeStreakLa);
            }

        });

        menuCreateBt.setOnAction(e -> stage.setScene(newAccountScene));

        recipe2homeBT.setOnAction(e -> stage.setScene(homeScene));
        settingsEdit2HomeBT.setOnAction(e -> {
            stage.setScene(homeScene);
            ACData.update_account(ACFile, homeBMILa, homeNutriLa, homeStreakLa);
        });

        recipeAddBt.setOnAction(e ->{
            recipeNewRecipeFailLA.setText("");
            recipeNewRecipeNameFail.setText("");
            recipeNewRecipeKcalFail.setText("");

            boolean correctInput = true;
            try{
                float kcal = Float.parseFloat(recipeNewRecipeKcalTF.getText());
                if(kcal < 0) {
                    recipeNewRecipeKcalFail.setText("Input must be positive");
                    correctInput = false;
                }
            }
            catch(NumberFormatException n){
                recipeNewRecipeKcalFail.setText("Input must be numeric");
                correctInput = false;
            }

            if(correctInput){


                String inputValue = recipeNewRecipeNameTF.getText() + "," + recipeNewRecipeKcalTF.getText() + "," + recipeNewRecipeIngTF.getText();
                int status = FIFile.addFoodItem(inputValue);
                recipeNewRecipeNameTF.setText("");
                recipeNewRecipeKcalTF.setText("");
                recipeNewRecipeIngTF.setText("");
                recipeListView.setItems(FIFile.readAllFoodItems());

                if (status == -1) {
                    recipeNewRecipeNameFail.setText("Food item or recipe with that name already exists!");
                } else {
                    recipeNewRecipeNameTF.setText("");
                    recipeNewRecipeKcalTF.setText("");
                    recipeNewRecipeIngTF.setText("");

                    recipeNewRecipeFailLA.setText("Food item or recipe added sucessfully");
                }
            }

        });

        tracking2homeBT.setOnAction(e -> {
            stage.setScene(homeScene);
            ACData.update_account(ACFile, homeBMILa, homeNutriLa, homeStreakLa);
        });
        trackingAddFoodBT.setOnAction(e -> {
            try{
                float amount = Float.parseFloat(trackingAmountTF.getText());
                String fooditem_line = trackingFoodItemLV.getSelectionModel().getSelectedItem();
                int index = fooditem_line.indexOf(" ");
                String fooditem = (index != -1)
                           ? fooditem_line.substring(0, index)
                           : fooditem_line;
                float kcal_value = Float.parseFloat(FIFile.get_Nutritions(fooditem))/100;
                System.out.println(kcal_value);
                float consumed_amount = amount * kcal_value;
                ACData.update_kcal(ACFile, consumed_amount);
                trackingAmountTF.setText("");
            }
            catch(NumberFormatException n){
                trackingAmountTF.setText("Die Eingabe muss numerisch sein");
            }
        });

        home2TrackCalories.setOnAction(e -> stage.setScene(trackingScene));//tun
        home2settings.setOnAction(e -> {
            stage.setScene(editScene);
            ACData.update_edit(ACFile, settingsNameLa, settingsHeightLa, settingsGenderLa, settingsWeightLa, settingsGoalWeightLa);
        });

        home2recipes.setOnAction(e -> {
            stage.setScene(recipeScene);
        });

        home2menu.setOnAction(e -> {
            stage.setScene(mainMenuScene);
        });

        newAccConfirmLA.setOnAction(e -> {
            newAccFailure.setText("");
            newAccNameFailLA.setText("");
            newAccHeightFailLA.setText("");
            newAccGenderFailLA.setText("");
            newAccWeightFailLA.setText("");
            newAccGoalWeightFailLA.setText("");

            boolean correctInput = true;
            try{
                float height = Float.parseFloat(newAccHeightTF.getText());
                if(height < 0) {
                    newAccHeightFailLA.setText("Input must be positive");
                    correctInput = false;
                }
            }
            catch(NumberFormatException n){
                newAccHeightFailLA.setText("Input must be numeric");
                correctInput = false;
            }
            try{
                float weight = Float.parseFloat(newAccWeightTF.getText());
                if(weight < 0) {
                    newAccWeightFailLA.setText("Input must be positive");
                    correctInput = false;
                }
            }
            catch(NumberFormatException n){
                newAccWeightFailLA.setText("Input must be numeric");
                correctInput = false;
            }
            try{
                float goalWeight = Float.parseFloat(newAccGoalWeightTF.getText());
                if(goalWeight < 0) {
                    newAccGoalWeightFailLA.setText("Input must be positive");
                    correctInput = false;
                }
            }
            catch(NumberFormatException n){
                newAccGoalWeightFailLA.setText("Input must be numeric");
                correctInput = false;
            }
            if(!(newAccGenderTF.getText().equals("male")) && !(newAccGenderTF.getText().equals("female"))){
                newAccGenderFailLA.setText("Gender must be 'male' or 'female'");
                correctInput = false;
            }

            if(correctInput){
                String inputValue = newAccNameTF.getText() + "," + newAccHeightTF.getText() + "," + newAccGenderTF.getText() +"," + newAccWeightTF.getText() +"," + newAccGoalWeightTF.getText();
                int status = ACFile.addAccount(inputValue);

                if (status == -1) {
                    newAccNameFailLA.setText("Account with that name already exists!");
                } else {
                    newAccNameTF.setText("");
                    newAccHeightTF.setText("");
                    newAccGenderTF.setText("");
                    newAccWeightTF.setText("");
                    newAccGoalWeightTF.setText("");

                    newAccFailure.setText("Added account sucessfully");
                }
            }
        });

        newAccHomeMBt.setOnAction(e -> {
            menuListView.setItems(ACFile.readAllAccounts("name"));
            stage.setScene(mainMenuScene);
        });//tun


        // Set scene
        stage.setScene(mainMenuScene);
        stage.setTitle("NutriBuddy");
        stage.show();
    }
}