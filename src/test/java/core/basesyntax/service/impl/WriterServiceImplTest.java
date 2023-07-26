package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private final String reportCorrectDataPath = "src/test/resources/report_correct_data.csv";
    private final String reportEmptyPath = "src/test/resources/report_empty_file.csv";
    private final File reportCorrectDataFile = new File(reportCorrectDataPath);
    private final File reportEmptyFile = new File(reportEmptyPath);
    private WriterServiceImpl writerServiceImpl;

    @BeforeEach
    void setUp() {
        writerServiceImpl = new WriterServiceImpl();
    }

    @Test
    void writeReportWithCorrectData() throws IOException {
        String reportData = "fruit,quantity\n"
                + "apple,5\n"
                + "banana,3\n"
                + "apple,10\n";
        writerServiceImpl.writeToFile(reportCorrectDataFile.getAbsolutePath(), reportData);
        String actualLines = Files.readString(reportCorrectDataFile.toPath());
        assertEquals(reportData, actualLines);
    }

    @Test
    void writeReportToEmptyFile() throws IOException {
        String reportData = "fruit,quantity\n"
                + "apple,5\n"
                + "banana,3\n";
        writerServiceImpl.writeToFile(reportEmptyFile.getAbsolutePath(), reportData);
        String actualLines = Files.readString(reportEmptyFile.toPath());
        assertEquals(reportData, actualLines);
    }

    @Test
    void writeReportToInvalidFilePathShouldThrowRuntimeException() {
        String reportData = "fruit,quantity\napple,5\nbanana,3\n";
        String invalidFilePath = "\0:invalid/path.csv";

        Assertions.assertThrows(RuntimeException.class, () ->
                writerServiceImpl.writeToFile(invalidFilePath, reportData)
        );
    }
}
