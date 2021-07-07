package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {
    private static final String WRITER_FILE_PATH = "src/test/resources/output.csv";
    private static final String WRONG_PATH = "src/test/resources";
    private static final String REPORT = "I'm not a Superman" + System.lineSeparator() + "...";
    private FileWriter fileWriter;

    @Before
    public void start() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void test_WritingToFile_OK() {
        fileWriter.writeToFile(REPORT, WRITER_FILE_PATH);
        List<String> expected = new ArrayList<>();
        try {
            String [] reportSplitted = REPORT.split(System.lineSeparator());
            expected.addAll(Arrays.asList(reportSplitted));
            List<String> actual = Files.readAllLines(Path.of(WRITER_FILE_PATH));
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file " + e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void test_WritingToWrongFile_NotOK() {
        fileWriter.writeToFile(REPORT, WRONG_PATH);
    }
}
