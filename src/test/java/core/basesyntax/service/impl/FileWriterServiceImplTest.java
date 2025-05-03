package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterServiceImplTest {
    private static final String FILE_NAME_IS_EMPTY = "";
    private static final String REPORT_FILE_NAME = "src/test/resources/report.csv";
    private static final List<String> FULL_REPORT = new ArrayList<>(
            List.of("fruit,quantity",
                    "banana,152",
                    "apple,90"));
    @Rule
    public ExpectedException writeToFileExceptionRule = ExpectedException.none();
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void writeToFile_emptyFileName_notOk() {
        writeToFileExceptionRule.expect(RuntimeException.class);
        writeToFileExceptionRule.expectMessage("Can't write data to file: "
                + FILE_NAME_IS_EMPTY);
        fileWriterService.writeToFile(FULL_REPORT, FILE_NAME_IS_EMPTY);
    }

    @Test
    public void writeToFile_fullReport_Ok() {
        writeToFile(FULL_REPORT, REPORT_FILE_NAME);
        assertEquals(FULL_REPORT, readFromFile(REPORT_FILE_NAME));
    }

    private void writeToFile(List<String> data, String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String string : data) {
                bufferedWriter.write(string + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file: " + fileName, e);
        }
    }

    private List<String> readFromFile(String fileName) {
        List<String> allLines;
        try {
            allLines = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file: " + fileName);
        }
        return allLines;
    }
}
