package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TEST_FILE = "src/test/java/resources/testFile.csv";
    private static final String FILE_REPORT = "src/main/resources/fileReport.csv";
    private static final String WRONG_PATH = "wrong/testFile.csv";
    private FileWriter fileWriter = new FileWriterImpl();
    private String report;

    @Test
    public void writeToFile_correctReport_ok() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        report = builder.toString();
        fileWriter.writeToFile(TEST_FILE, report);
        List<String> expectedReport = Files.readAllLines(Path.of(FILE_REPORT));
        List<String> actualReport = Files.readAllLines(Path.of(TEST_FILE));
        assertEquals(expectedReport, actualReport);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPath_notOk() {
        report = "report";
        fileWriter.writeToFile(WRONG_PATH, report);
    }
}
