package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String PURPOSE_FILE = "src/test/resources/writerTestFile.csv";
    private static final String TEST_FILE = "src/test/resources/testFile.csv";
    private static String report;
    private static FileWriter writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
        StringBuilder builder = new StringBuilder();
        report = builder.append("type,fruit,quantity")
                .append(System.lineSeparator())
                .append("b,apple,50")
                .append(System.lineSeparator())
                .append("s,apple,25")
                .toString();
    }

    @Test(expected = RuntimeException.class)
    public void writerDataToFile_toNotValidFile_notOk() {
        writer.writerDataToFile(report, "");
    }

    @Test
    public void writerDataToFile_emptyData_ok() {
        writer.writerDataToFile("", PURPOSE_FILE);
        List<String> expected = readFromFile(EMPTY_FILE);
        List<String> actual = readFromFile(PURPOSE_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void writerDataToFile_report_ok() {
        writer.writerDataToFile(report, PURPOSE_FILE);
        List<String> expected = readFromFile(TEST_FILE);
        List<String> actual = readFromFile(PURPOSE_FILE);
        assertEquals(expected, actual);
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + filePath);
        }
    }
}
