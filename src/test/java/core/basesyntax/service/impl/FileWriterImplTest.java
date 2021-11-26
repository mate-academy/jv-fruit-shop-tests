package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String VALID_FILE_PATH
            = "src/test/resources/resultFruits-logTest.csv";
    private static StringBuilder builder = new StringBuilder();

    @BeforeClass
    public static void setUp() {
        builder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana 100");
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writer_Writer_ok() {
        String report = builder.toString();
        fileWriter.write(report, VALID_FILE_PATH);
        List<String> expected = List.of("fruit,quantity", "banana 100");
        List<String> actual = read(VALID_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writer_ReportIsNull_notOk() {
        fileWriter.write(null, VALID_FILE_PATH);
    }

    @Test(expected = NullPointerException.class)
    public void writer_PathIsNull_notOk() {
        fileWriter.write("fruit,type", null);
    }

    @Test(expected = RuntimeException.class)
    public void writer_PathIsEmpty_notOk() {
        fileWriter.write("fruit,type", "");
    }

    @Test
    public void writer_ReportIsEmpty_Ok() {
        fileWriter.write("", VALID_FILE_PATH);
    }

    private static List<String> read(String filePath) {
        List<String> records;
        try {
            records = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can not read data from the file or file is wrong");
        }
        return records;
    }
}
