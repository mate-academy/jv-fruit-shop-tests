package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String EXPECTED_FILE_PATH =
            "src/test/java/core/basesyntax/resources/write.csv";
    private static final String ACTUAL_FILE_PATH =
            "src/test/java/core/basesyntax/resources/read.csv";
    private static final String FILE_NOT_WRITE = "directory/file.csv";
    private static FileWriter fileWriter;
    private static StringBuilder testReport;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        testReport = new StringBuilder();
        testReport.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
    }

    @Test(expected = RuntimeException.class)
    public void writeWrongFile_notOk() {
        fileWriter.writeToFile(testReport.toString(), FILE_NOT_WRITE);
    }

    @Test
    public void writeToFile_ok() throws IOException {
        fileWriter.writeToFile(testReport.toString(), EXPECTED_FILE_PATH);
        List<String> expected = Files.readAllLines(Path.of(ACTUAL_FILE_PATH));
        List<String> actual = Files.readAllLines(Path.of(EXPECTED_FILE_PATH));
        Assert.assertEquals(expected, actual);
    }
}
