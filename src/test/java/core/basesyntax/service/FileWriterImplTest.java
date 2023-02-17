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
    private static final String FILE_WRITE_TEST =
            "src/test/java/core/basesyntax/resources/write.csv";
    private static final String FILE_READ_TEST =
            "src/test/java/core/basesyntax/resources/read.csv";
    private static final String FILE_NOT_WRITE = "directory/file.csv";
    private static FileWriter fileWriterTest;
    private static StringBuilder testReport;

    @BeforeClass
    public static void setUp() {
        fileWriterTest = new FileWriterImpl();
        testReport = new StringBuilder();
        testReport.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
    }

    @Test(expected = RuntimeException.class)
    public void writeWrongFile_notOk() {
        fileWriterTest.writeToFile(testReport.toString(), FILE_NOT_WRITE);
    }

    @Test
    public void writeToFile_ok() throws IOException {
        fileWriterTest.writeToFile(testReport.toString(), FILE_WRITE_TEST);
        List<String> expected = Files.readAllLines(Path.of(FILE_WRITE_TEST));
        List<String> actual = Files.readAllLines(Path.of(FILE_READ_TEST));
        Assert.assertEquals(expected, actual);
    }
}
