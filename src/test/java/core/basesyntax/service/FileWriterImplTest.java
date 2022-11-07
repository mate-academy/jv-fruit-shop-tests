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
    private static final String FILE_WRITE_PATH = "src/test/resources/output.csv";
    private static final String FILE_READ_PATH = "src/test/resources/output.csv";
    private static final String WRONG_PATH = "wrong/file.csv";
    private static FileWriter fileWriter;
    private static StringBuilder report;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        report = new StringBuilder();
        report.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,132")
                .append(System.lineSeparator())
                .append("apple,90");
    }

    @Test(expected = RuntimeException.class)
    public void writeDataToFile_WrongFile_notOk() {
        fileWriter.writeDataToFile(report.toString(), WRONG_PATH);
    }

    @Test
    public void writeDataToFile_CorrectFile_ok() throws IOException {
        fileWriter.writeDataToFile(report.toString(), FILE_WRITE_PATH);
        List<String> expected = Files.readAllLines(Path.of(FILE_WRITE_PATH));
        List<String> actual = Files.readAllLines(Path.of(FILE_READ_PATH));
        Assert.assertEquals(expected, actual);
    }
}
