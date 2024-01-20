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
        String existingAccount1 = "Tom,179,male,30,80,3276.0,2023-12-10,2";
        String existingAccount2 = "Dan,130,male,70,80,3276.0,2023-12-10,2";
        setupAF.addAccount(existingAccount1);
        setupAF.addAccount(existingAccount2);
    }

    @Test
    void readAccountMissingFile() {

        AccountFile af = new AccountFile("missing");
        //String result = af.readAccount(0, "weight");
        assertThrows(FileNotFoundException.class, () ->af.readAccount(0, "weight"));
        //assertEquals("Error, Account 0 with data weightnot found", result);
    }
    //probleme bei dem String result und assertThrown
    //kein Error thrown

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
        //af.addAccount(existingAccount);
        int result = af.addAccount(existingAccount);
        assertEquals(-1, result);

    }


    @Test
    void addAccountWithInvalidData() {
        AccountFile af = new AccountFile("src/test/resources/editTest.csv");
        String invalidAccountData = ",,,,,,,";
        int result = af.addAccount(invalidAccountData);
        assertEquals(-1, result);
    }
    //1 statt -1


    @Test
    void readAllAccountsMissingFile(){
        AccountFile af = new AccountFile("missing");
        //ObservableList<String> result = af.readAllAccounts("weight");
        assertThrows(FileNotFoundException.class, () ->af.readAllAccounts("weight"));
        //assertEquals("Error, Accounts data weightnot found", result.get(0));
    }
    //kein Error thrown
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
    //Nothing was thrown
    @Test
    void addFoodItem() {
        zweitesFoodfile ff = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        String foodItem = "apple,52";
        int result = ff.addFoodItem(foodItem);
        assertEquals(1, result);
    }
    //kommt nichtmal zum assert fail, addFoodItem failed, weil index out of bounds

    //following test will fail because addFoodItem doesn't function properly
    @Test
    void readAllFoodItems() {
        zweitesFoodfile ff = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        ff.addFoodItem("cucumber,10");
        ObservableList<String> result = ff.readAllFoodItems();
        assertEquals("cucumber | Kcal: 10", result.get(0)); //name,nutritions,ingredients is at 0 and apple,52 is at 1.
    }
    //kommt nicht bis zu assert, addFoodItem failed


    @Test
    void searchFoodItemNotFound(){
        zweitesFoodfile ff = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        int result = ff.searchFoodItem("Banana");
        assertEquals(-1, result);
    }
    //following test will fail because addFoodItem doesn't function properly
    @Test
    void searchFoodItemFound(){
        zweitesFoodfile ff = new zweitesFoodfile("src/test/resources/testFoodItems.csv");
        ff.addFoodItem("banana, 90");
        int result = ff.searchFoodItem("Banana");
        assertEquals(1, result);
    }
    //kommt nicht zum assert, addFoodItem failed




//cleanup to clear the csv files
    @AfterAll
    static void cleanup() throws FileNotFoundException {

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