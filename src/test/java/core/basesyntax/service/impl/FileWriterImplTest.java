package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE = "src/test/java/core/basesyntax/resources/testFile.csv";
    private static final String FILE_REPORT = "src/main/java/core/basesyntax/db/report.csv";
    private static final String WRONG_PATH = "wrong/testFile.csv";
    private FileWriter fileWriter = new FileWriterImpl();
    private String report;

    @Test
    public void writeToFile_correctReport_ok() {
        List<String> expectedReport;
        List<String> actualReport;
        StringBuilder builder = new StringBuilder();
        builder.append("fruit, quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        report = builder.toString();
        fileWriter.writeToFile(report, TEST_FILE);
        try {
            expectedReport = Files.readAllLines(Path.of(TEST_FILE));
            actualReport = Files.readAllLines(Path.of(FILE_REPORT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void writeToFile_invalidPath_notOk() {
        report = "report";
        fileWriter.writeToFile(WRONG_PATH, report);
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(report, WRONG_PATH));
    }
}
