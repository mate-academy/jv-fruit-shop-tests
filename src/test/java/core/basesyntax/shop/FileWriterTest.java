package core.basesyntax.shop;

import core.basesyntax.shop.service.FileWriterService;
import core.basesyntax.shop.service.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String OUTPUT_FILE_OK = "src/test/java/core/basesyntax"
            + "/resources/report_ok.csv";
    private static final String OUTPUT_FILE_DOES_NOT_EXIST = "";
    private static final String REPORT = "fruit,quantity\n"
            + "banana,152\n" + "pear,138\n" + "apple,90";
    private static FileWriterService fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterServiceImpl();
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
        expected.add("pear,138");
        expected.add("apple,90");
        fileWriter.write(OUTPUT_FILE_OK, REPORT);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE_OK));
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void read_differentContent_NotOk() {
        List<String> expected = new ArrayList<>();
        expected.add("notCorrect1");
        expected.add("notCorrect2");
        expected.add("notCorrect3");
        fileWriter.write(OUTPUT_FILE_OK, REPORT);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE_OK));
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
        Assert.assertNotEquals(expected, actual);
    }
}
