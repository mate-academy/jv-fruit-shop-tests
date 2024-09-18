package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static final String TEST_FILE_PATH = "src/main/java/resources/report.csv";
    private WriterImpl writer;

    @BeforeEach
    void setUp() {
        writer = new WriterImpl();
    }

    @Test
    void writeReport_validFilePath_success() throws IOException {
        String report = "fruit,quantity\nbanana,100\napple,50";

        writer.writeReport(TEST_FILE_PATH);

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists(), "File should be created");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.readLine();
            assertEquals(TEST_FILE_PATH, content, "File content should match the written content");
        }
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
