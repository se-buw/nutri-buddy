package de.buw.se4de;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private App app;
    @BeforeAll
    static void setup() {

        Platform.startup(() -> {
        });
        App setupApp = new App();
        String existingAccount1 = "Tom,179,male,30,80,3276.0,2023-12-10,2";
        setupApp.addAccount(existingAccount1, "src/test/resourcesTest/editTest.csv");
        String existingAccount2 = "Dan,130,male,70,80,3276.0,2023-12-10,2";
        setupApp.addAccount(existingAccount2, "src/test/resourcesTest/editTest.csv");
    }
    @Test
    void readAccountMissingFile() {

        app = new App();
        String result = app.readAccount(0, "weight", "missing");
        assertThrows(FileNotFoundException.class, () ->app.readAccount(0, "weight", "missing"));
        assertEquals("Error, Account 0 with data weightnot found", result);
    }

    @Test
    void readAccountEmptyFile() {
        app = new App();
        String result = app.readAccount(0, "weight", "src/test/resourcesTest/empty.csv");
        assertEquals("Error, Account 0 with data weightnot found", result);
    }
    @Test
    void readAccountEntry() {
        app = new App();
        String result = app.readAccount(0, "height", "src/test/resourcesTest/editTest.csv");
        assertEquals("179", result);
    }
    @Test
    void editAccountExistingEntry() {
        app = new App();
        app.loggedInAccountIndex = 1;
        app.editAccount("female", 2,"src/test/resourcesTest/editTest.csv" );
        String result = app.readAccount(1, "gender", "src/test/resources/editTest.csv");
        assertEquals("female", result);

    }
    @Test
    void addNewAccount(){
        app = new App();
        String accountInformation = "Max,140,female,70,80,3276.0,2023-12-10,2";
        int result = app.addAccount(accountInformation, "src/test/resourcesTest/editTest.csv");
        assertEquals(1, result);

    }
    @Test
    void addExistingAccount() {
        app = new App();
        String existingAccount = "Dan,130,male,70,80,3276.0,2023-12-10,2";
        app.addAccount(existingAccount, "src/test/resourcesTest/editTest.csv");
        int result = app.addAccount(existingAccount, "src/test/resourcesTest/editTest.csv");
        assertEquals(-1, result);

    }


    @Test
    void addAccountWithInvalidData() {
        app = new App();
        String invalidAccountData = ",,,,,,,";
        int result = app.addAccount(invalidAccountData, "src/test/resourcesTest/editTest.csv");
        assertEquals(-1, result);
    }
    @Test
    void readAllAccountsMissingFile(){
        app = new App();
        ObservableList<String> result = app.readAllAccounts("weight", "missing");
        assertThrows(FileNotFoundException.class, () ->app.readAllAccounts("weight", "missing"));
        assertEquals("Error, Accounts data weightnot found", result.get(0));
    }
    @Test
    void readAllAccountsWrongKey(){
        app = new App();
        assertThrows(IllegalArgumentException.class, () ->app.readAllAccounts("nutritions", "src/test/resourcesTest/editTest.csv"));
    }
    @Test
    void readAllAccountsWrongPath(){
        app = new App();
        assertThrows(IllegalArgumentException.class, () ->app.readAllAccounts("nutritions", "src/test/resourcesTest/testFoodItems.csv"));
    }
    @Test
    void addFoodItem() {
        app = new App();
        String foodItem = "apple,52,";
        int result = app.addFoodItem(foodItem, "src/test/resourcesTest/testFoodItems.csv");
        assertEquals(1, result);
    }
    //following test will fail because addFoodItem doesn't function properly
    @Test
    void readAllFoodItems() {
        app = new App();
        app.addFoodItem("apple,10,","src/test/resourcesTest/testFoodItems.csv");
        ObservableList<String> result = app.readAllFoodItems("src/test/resourcesTest/testFoodItems.csv");
        assertEquals("apple,10,", result);
    }

    @Test
    void searchFoodItemNotFound(){
        app = new App();
        int result = app.searchFoodItem("Banana", "src/test/resourcesTest/testFoodItems.csv");
        assertEquals(-1, result);
    }
    //following test will fail because addFoodItem doesn't function properly
    @Test
    void searchFoodItemFound(){
        app = new App();
        app.addFoodItem("banana","src/test/resourcesTest/testFoodItems.csv");
        int result = app.searchFoodItem("Banana", "src/test/resourcesTest/testFoodItems.csv");
        assertEquals(1, result);
    }


    @AfterAll
    static void cleanup() throws FileNotFoundException {
        String entryToKeep = "name,height,gender,weight,goal_weight,nutritions_day,login,streak";
        String foodEntryToKeep = "name,nutritions,ingredients";
        try (PrintWriter pw = new PrintWriter("src/test/resourcesTest/editTest.csv")) {
            pw.println(entryToKeep);
        }
        try (PrintWriter pw = new PrintWriter("src/test/resourcesTest/testFoodItems.csv")) {
            pw.println(foodEntryToKeep);
        }
    }

}