package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportToFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportToFileImplTest {
    private static final String TEST_FILE_NAME = "test_report.txt";
    private static final String HEADER = "fruit, quantity";
    private ReportToFile reportToFile;

    @BeforeEach
    void setUp() {
        reportToFile = new ReportToFileImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_NAME));
    }

    @Test
    void writeReportToFile_() {
        ArrayList<String> report = new ArrayList<>();
        report.add(HEADER);
        report.add("banana,50");
        report.add("orange,55");
        report.add("apple,30");

        reportToFile.writeReportToFile(report,TEST_FILE_NAME);
        List<String> writtenLines;
        try {
            writtenLines = Files.readAllLines(Path.of(TEST_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(report, writtenLines);
    }
}
