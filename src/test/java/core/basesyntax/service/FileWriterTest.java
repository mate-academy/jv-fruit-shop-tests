package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {
    private static final String OUTPUT_FILE_OK = "src/test/resources/report_ok.csv";
    private static final String OUTPUT_FILE_DOES_NOT_EXIST = "";
    private static final String REPORT = "fruit,quantity\n"
            + "banana,152\n" + "apple,90";
    private FileWriter fileWriter;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test(expected = NullPointerException.class)
    public void write_nullFile_NotOk() {
        fileWriter.write(null, REPORT);
    }

    @Test(expected = NullPointerException.class)
    public void write_nullContent_NotOk() {
        fileWriter.write(OUTPUT_FILE_OK, null);
    }

    @Test(expected = RuntimeException.class)
    public void write_fileDoesNotExist_NotOk() {
        fileWriter.write(OUTPUT_FILE_DOES_NOT_EXIST, REPORT);
    }

    @Test
    public void write_fileExists_Ok() {
        fileWriter.write(OUTPUT_FILE_OK, REPORT);
    }

    @Test
    public void write_content_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        fileWriter.write(OUTPUT_FILE_OK, REPORT);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE_OK));
        } catch (IOException e) {
            throw new RuntimeException("no such file");
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void read_differentContent_NotOk() {
        List<String> expected = new ArrayList<>();
        expected.add("this is a");
        expected.add("different");
        expected.add("content");
        fileWriter.write(OUTPUT_FILE_OK, REPORT);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE_OK));
        } catch (IOException e) {
            throw new RuntimeException("no such file");
        }
        Assert.assertNotEquals(expected, actual);
    }
}

