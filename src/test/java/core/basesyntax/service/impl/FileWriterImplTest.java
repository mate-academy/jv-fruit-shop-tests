package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String PURPOSE_FILE = "src/test/resources/writerTestFile.csv";
    private static final String TEST_FILE = "src/test/resources/testFile.csv";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static String report;
    private static FileWriter writer;
    private static FileReader reader;

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
        reader = new FileReaderImpl();
        StringBuilder builder = new StringBuilder();
        report = builder.append("type,fruit,quantity")
                .append(LINE_SEPARATOR)
                .append("b,apple,50")
                .append(LINE_SEPARATOR)
                .append("s,apple,25")
                .toString();
    }

    @Test(expected = RuntimeException.class)
    public void writeToNotValidFile_notOk() {
        writer.writerDataToFile(report, "");
    }

    @Test
    public void writeEmptyData_ok() {
        writer.writerDataToFile("", PURPOSE_FILE);
        List<String> expected = reader.readFromFile(Paths.get(EMPTY_FILE));
        List<String> actual = reader.readFromFile(Paths.get(PURPOSE_FILE));
        assertEquals(expected, actual);
    }

    @Test
    public void writeReport_ok() {
        writer.writerDataToFile(report, PURPOSE_FILE);
        List<String> expected = reader.readFromFile(Paths.get(TEST_FILE));
        List<String> actual = reader.readFromFile(Paths.get(PURPOSE_FILE));
        assertEquals(expected, actual);
    }

}
