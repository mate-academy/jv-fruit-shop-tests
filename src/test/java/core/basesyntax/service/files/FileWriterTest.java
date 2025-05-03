package core.basesyntax.service.files;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {
    private static final String FILE_HEAD = "fruit,quantity";
    private static final String TO_FILE_NAME = "src/test/resources/result.csv";
    private static final String INCORRECT_FILE_NAME = "";
    private static final String SEPARATOR = ",";
    private static String report;
    private static FileWriter toFileWriter;
    private static List<String> correctData;

    @Before
    public void setUp() throws Exception {
        correctData = new ArrayList<>();
        correctData.add(FILE_HEAD);
        correctData.add("banana,120");
        correctData.add("apple,60");
        toFileWriter = new FileWriter();
        report = new StringBuilder(FILE_HEAD)
                .append(System.lineSeparator())
                .append("banana")
                .append(SEPARATOR)
                .append(120)
                .append(System.lineSeparator())
                .append("apple")
                .append(SEPARATOR)
                .append(60)
                .toString();
    }

    @Test
    public void write_correctData_Ok() {
        toFileWriter.writeToCsv(report, TO_FILE_NAME);
        List<String> expected = correctData;
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(TO_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file");
        }
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void write_incorrectFileName_NotOk() {
        toFileWriter.writeToCsv(report, INCORRECT_FILE_NAME);
    }
}
