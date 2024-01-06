package de.buw.se4de;

import javafx.application.Platform;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private App app;

    @BeforeEach
    void setUp() {
        Platform.startup(() -> {
        });
    }
    @AfterEach
    void tearDown() {
        // Shutdown the JavaFX toolkit after each test
        Platform.exit();
    }

    @Test
    void readAccountMissingFile() {
        app = new App();
        String empty = "";
        empty = app.readAccount(0, "weight", "missing");
        assertFalse(empty.isEmpty());
    }
    /*
    @Test
    void readAccountEmptyFile() {
        app = new App();
        String empty = "";
        //empty = app.readAccount(0, "weight", "scr/test/resources/empty.csv");
        assertTrue(empty.isEmpty());
    }

     */
}