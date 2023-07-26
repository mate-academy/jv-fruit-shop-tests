package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
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
        File reportFile = new File("src/test/resources/report_correct_data.csv");
        writerServiceImpl.writeToFile(reportFile.getAbsolutePath(), reportData);
        String actualLines = Files.readString(reportFile.toPath());
        assertEquals(reportData, actualLines);
    }

    @Test
    void writeReportToEmptyFile() throws IOException {
        String reportData = "fruit,quantity\n"
                + "apple,5\n"
                + "banana,3\n";
        File reportFile = new File("src/test/resources/report_empty_file.csv");
        writerServiceImpl.writeToFile(reportFile.getAbsolutePath(), reportData);
        String actualLines = Files.readString(reportFile.toPath());
        assertEquals(reportData, actualLines);
    }
}
