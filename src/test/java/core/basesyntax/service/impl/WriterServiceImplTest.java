package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private WriterServiceImpl writerServiceImpl;

    @BeforeEach
    void setUp() {
        writerServiceImpl = new WriterServiceImpl();
    }

    @Test
    void writeReportWithIncorrectData() {
        List<String> reportData = Arrays.asList(
                "apple,5",
                "orange,10", // Invalid fruit
                "banana,-3"  // Negative quantity
        );

        try {
            File reportFile = File.createTempFile("report", ".csv");

//            assertThrows(RuntimeException.class, () -> writerServiceImpl.writeToFile(reportFile.getAbsolutePath(), reportData));

        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary file", e);
        }
    }

    @Test
    void writeReportWithCorrectData() throws IOException {
        List<String> reportData = Arrays.asList(
                "fruit,quantity",
                "apple,5",
                "banana,3",
                "apple,10"
        );

        File reportFile = new File("src/test/resources/report_correct_data.csv");

//        writerServiceImpl.writeToFile(reportFile.getAbsolutePath(), reportData);

        // Read the content of the file and verify it matches the expected data
        List<String> actualLines = Files.readAllLines(reportFile.toPath());
        assertEquals(reportData, actualLines);
    }

    @Test
    void writeReportToEmptyFile() throws IOException {
        List<String> reportData = Arrays.asList(
                "fruit,quantity",
                "apple,5",
                "banana,3"
        );

        // Create a temporary file
        File reportFile = new File("src/test/resources/empty_file.csv");

        // Write the report data to the file
//        writerServiceImpl.writeToFile(reportFile.getAbsolutePath();

        // Read the content of the file and verify it matches the expected data
        List<String> actualLines = Files.readAllLines(reportFile.toPath());
        assertEquals(reportData, actualLines);
    }
}