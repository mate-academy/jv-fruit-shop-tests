package core.basesyntax.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenerateReportTest {

    private static final String RESULT_FILE_NAME = "EndDayReport.csv";
    private static GenerateReport generateReport;

    @BeforeEach
    public void setUp() {
        generateReport = new GenerateReport(RESULT_FILE_NAME);
    }

    @Test
    public void generateReport_ShouldCreateFile_WhenDataIsProvided() {
        Map<String, Integer> fruitQuantity = Map.of(
                "apple", 10,
                "banana", 5,
                "cherry", 3
        );

        generateReport.generateReport(fruitQuantity);

        File file = new File(RESULT_FILE_NAME);
        Assertions.assertTrue(file.exists(), "File should be created");
        Assertions.assertTrue(file.length() > 0, "File should not be empty");
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            Assertions.assertTrue(lines.contains("fruit,quantity"), "File should contain header");
            Assertions.assertTrue(lines.contains("apple,10"), "File should contain apple data");
            Assertions.assertTrue(lines.contains("banana,5"), "File should contain banana data");
            Assertions.assertTrue(lines.contains("cherry,3"), "File should contain cherry data");
        } catch (IOException e) {
            Assert.fail("IOException occurred while reading the file");
        }
    }

    @Test
    public void generateReport_ShouldOverwriteFile_WhenFileExists() {
        Map<String, Integer> fruitQuantity = Map.of(
                "apple", 10,
                "banana", 5
        );
        File file = new File(RESULT_FILE_NAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            Assert.fail("Failed to create test file");
        }
        generateReport.generateReport(fruitQuantity);
        Assertions.assertTrue(file.exists(), "File should still exist after report generation");
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            Assertions.assertTrue(lines.contains("fruit,quantity"), "File should contain header");
            Assertions.assertTrue(lines.contains("apple,10"), "File should contain apple data");
            Assertions.assertTrue(lines.contains("banana,5"), "File should contain banana data");
        } catch (IOException e) {
            Assert.fail("IOException occurred while reading the file");
        }
    }

    @Test
    public void check_Name_File() {
        generateReport = new GenerateReport(RESULT_FILE_NAME);
        File file = new File(RESULT_FILE_NAME);
        Assertions.assertTrue(file.getName().contains(".csv"),
                "File should be created as cvs file");
    }
}
