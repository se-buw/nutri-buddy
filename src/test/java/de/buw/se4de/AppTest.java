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
        //setup our csv files properly
        Platform.startup(() -> {
        });
        AccountFile setupAF = new AccountFile("src/test/resources/editTest.csv");
        setupAF.addAccount("Tom,179,male,30,80");
        setupAF.addAccount("Dan,130,male,70,80");

        zweitesFoodfile setupFF = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        setupFF.addFoodItem("banana, 90");
    }

    @Test
    void readAccountMissingFile() {
        AccountFile af = new AccountFile("missing");
        String result = af.readAccount(0, "weight");
        //assertThrows(FileNotFoundException.class, () ->af.readAccount(0, "weight"));
        assertEquals("Error, Account 0 with data weightnot found", result);
    }

    @Test
    void readAccountEmptyFile() {
        AccountFile af = new AccountFile("src/test/resources/empty.csv");
        String result = af.readAccount(0, "weight");
        assertEquals("Error, Account 0 with data weightnot found", result);
    }

    @Test
    void readAccountEntry() {
        AccountFile af = new AccountFile("src/test/resources/editTest.csv");
        String result = af.readAccount(0, "height");
        assertEquals("179", result);
    }
    @Test
    void editAccountExistingEntry() {
        AccountFile af = new AccountFile("src/test/resources/editTest.csv");
        af.editAccount("female", 2, 1);
        String result = af.readAccount(1, "gender");
        assertEquals("female", result);

    }
    @Test
    void addNewAccount(){
        AccountFile af = new AccountFile("src/test/resources/editTest.csv");
        String accountInformation = "Max,140,female,70,80";
        int result = af.addAccount(accountInformation);
        assertEquals(1, result);

    }
    @Test
    void addExistingAccount() {
        AccountFile af = new AccountFile("src/test/resources/editTest.csv");
        String existingAccount = "Dan,130,male,70,80";
        int result = af.addAccount(existingAccount);
        assertEquals(-1, result);

    }


    @Test
    void addAccountWithInvalidData() {
        AccountFile af = new AccountFile("src/test/resources/editTest.csv");
        String invalidAccountData = ",,,,,";
        int result = af.addAccount(invalidAccountData);
        assertEquals(-1, result);
    }


    @Test
    void readAllAccountsMissingFile(){
        AccountFile af = new AccountFile("missing");
        ObservableList<String> result = af.readAllAccounts("weight");
        //assertThrows(FileNotFoundException.class, () ->af.readAllAccounts("weight"));
        assertEquals("Error, Accounts data weightnot found", result.get(0));
    }
    @Test
    void readAllAccountsWrongKey(){
        AccountFile af = new AccountFile("src/test/resources/editTest.csv");
        assertThrows(IllegalArgumentException.class, () ->af.readAllAccounts("nutritions"));
    }
    @Test
    void readAllAccountsWrongPath(){
        AccountFile af = new AccountFile("src/test/resources/testFoodItems.csv");
        assertThrows(IllegalArgumentException.class, () ->af.readAllAccounts("weight"));
    }
    @Test
    void addFoodItem() {
        zweitesFoodfile ff = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        String foodItem = "apple,52";
        int result = ff.addFoodItem(foodItem);
        assertEquals(1, result);
    }

    @Test
    void readAllFoodItems() {
        zweitesFoodfile ff = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        ObservableList<String> result = ff.readAllFoodItems();
        assertEquals("banana | Kcal: 90", result.get(0)); //name,nutritions,ingredients is at 0 and apple,52 is at 1.
    }


    @Test
    void searchFoodItemNotFound(){
        zweitesFoodfile ff = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        int result = ff.searchFoodItem("cucumber");
        assertEquals(-1, result);
    }

    @Test
    void searchFoodItemFound(){
        zweitesFoodfile ff = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        //ff.addFoodItem("banana, 90");
        int result = ff.searchFoodItem("Banana");
        assertEquals(0, result);
    }


    @AfterAll
    static void cleanup() throws FileNotFoundException {

        //cleanup to clear the csv files
        String entryToKeep = "name,height,gender,weight,goal_weight,nutritions_day,login,streak";
        String foodEntryToKeep = "name,nutritions,ingredients";
        try (PrintWriter pw = new PrintWriter("src/test/resources/editTest.csv")) {
            pw.println(entryToKeep);
        }
        try (PrintWriter pw = new PrintWriter("src/test/resources/testFoodItems.csv")) {
            pw.println(foodEntryToKeep);
        }
    }

}