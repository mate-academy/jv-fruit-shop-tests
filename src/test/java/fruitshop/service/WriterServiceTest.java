package fruitshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private WriterService writerService;
    private File testFile;

    @BeforeEach
    void setUp() {
        writerService = new fruitshop.service.impl.WriterServiceImpl();
        testFile = new File("src/test/resources/testFinalReport.csv");
    }

    @AfterEach
    void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void write_validReport_ok() {
        String reportContent = "fruit,quantity\napple,10\nbanana,20\n";

        writerService.write(reportContent, testFile.getPath());

        assertTrue(testFile.exists(), "Report file should be created.");

        try {
            String fileContent = new String(Files.readAllBytes(testFile.toPath()));
            assertEquals(reportContent, fileContent,
                    "The file content should match the expected report.");
        } catch (IOException e) {
            fail("An error occurred while reading the file.");
        }
    }

    @Test
    void write_emptyReport_ok() {
        String reportContent = "fruit,quantity\n";

        writerService.write(reportContent, testFile.getPath());

        assertTrue(testFile.exists(), "Report file should be created.");

        try {
            String fileContent = new String(Files.readAllBytes(testFile.toPath()));
            assertEquals(reportContent, fileContent,
                    "The file content should match the expected empty report.");
        } catch (IOException e) {
            fail("An error occurred while reading the file.");
        }
    }

    @Test
    void write_invalidFilePath_notOk() {
        String invalidPath = "invalid/path/to/report.csv";
        String reportContent = "fruit,quantity\napple,10\n";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            writerService.write(reportContent, invalidPath);
        });
        
        assertTrue(thrown.getMessage().contains("Error writing report to file"),
                "Expected exception message not found.");
    }
}
