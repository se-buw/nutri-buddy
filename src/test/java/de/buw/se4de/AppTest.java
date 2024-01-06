package de.buw.se4de;

import javafx.application.Platform;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private App app;

    @Test
    void readAccountMissingFile() {
        Platform.startup(() -> {
        });
        app = new App();
        String result = app.readAccount(0, "weight", "missing");
        assertThrows(FileNotFoundException.class, () ->app.readAccount(0, "weight", "missing"));
        assertEquals("Error, Account 0 with data weightnot found", result);
    }

    @Test
    void readAccountEmptyFile() {
        app = new App();
        String result = app.readAccount(0, "weight", "src/test/resources/empty.csv");
        assertEquals("Error, Account 0 with data weightnot found", result);
    }
    @Test
    void readAccountTestTest() {
        app = new App();
        String result = app.readAccount(0, "height", "src/main/resources/accounts.csv");
        assertEquals("130", result);
        Platform.exit();
    }
}