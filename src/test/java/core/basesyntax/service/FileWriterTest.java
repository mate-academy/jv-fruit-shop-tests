package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FileWriterTest {
    private static final String WRITER_FILE_PATH = "src/test/resources/output.csv";
    private static final String WRONG_PATH = "src/test/resources";
    private static final FileWriter fileWriter = new FileWriterImpl();
    private final String report = "I'm not a Superman" + System.lineSeparator() + "...";

    @Test
    public void test_writingToFile_OK() {
        fileWriter.writeToFile(report, WRITER_FILE_PATH);
        List<String> expected = new ArrayList<>();
        try {
            String [] reportSplitted = report.split(System.lineSeparator());
            expected.addAll(Arrays.asList(reportSplitted));
            List<String> actual = Files.readAllLines(Path.of(WRITER_FILE_PATH));
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file " + e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void test_readingFromFile_Not_OK() {
        fileWriter.writeToFile(report, WRONG_PATH);
    }
}
