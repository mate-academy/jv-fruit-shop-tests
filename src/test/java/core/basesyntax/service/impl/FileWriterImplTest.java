package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String OUTPUT_FILE = "src/test/resources/outputFile.csv";
    private static final String TEST_OUTPUT_FILE = "src/test/resources/testOutputFile.csv";
    private static final String WRONG_PATH_OUTPUT_FILE = "src/test/resource/outputFile.csv";
    private static final String REPORT_OK = "fruit, amount" + System.lineSeparator() + "banana,20";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_emptyResultingReport_ok() throws IOException {
        String emptyReport = "";
        fileWriter.write(emptyReport, OUTPUT_FILE);
        compareFiles(EMPTY_FILE);
    }

    @Test
    void write_notEmptyResultingReport_ok() throws IOException {
        fileWriter.write(REPORT_OK, OUTPUT_FILE);
        compareFiles(TEST_OUTPUT_FILE);
    }

    @Test
    void write_wrongPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(REPORT_OK, WRONG_PATH_OUTPUT_FILE),
                "You should throw an IO exception if file path is wrong");
    }

    @Test
    void write_fileNameNull_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write(REPORT_OK, null),
                "You should throw an exception if output file name is null");
    }

    @Test
    void write_emptyFileName_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write(REPORT_OK, ""),
                "You should throw an IO exception if file name is empty");
    }

    private void compareFiles(String expectedFile) throws IOException {
        List<String> expectedFileContent = Files.readAllLines(Path.of(expectedFile));
        List<String> actualFileContent = Files.readAllLines(Path.of(OUTPUT_FILE));
        assertLinesMatch(expectedFileContent, actualFileContent);
    }
}
