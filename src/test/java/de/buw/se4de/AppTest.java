package de.buw.se4de;

import javafx.application.Platform;
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
    void readAccountTestTest() {
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
    @AfterAll
    static void cleanup() throws FileNotFoundException {
        String entryToKeep = "name,height,gender,weight,goal_weight,nutritions_day,login,streak";

        try (PrintWriter pw = new PrintWriter("src/test/resourcesTest/editTest.csv")) {
            pw.println(entryToKeep);
        }
    }

}