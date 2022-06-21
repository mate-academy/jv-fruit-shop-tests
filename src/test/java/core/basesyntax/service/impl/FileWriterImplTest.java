package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    public static final String VALID_PATH = "src/test/resources/reportFile.csv";
    public static final String INVALID_PATH = "src/test/invalid/game.csv";
    private static String report;
    private static FileWriterImpl fileWriter;
    private static List<String> excepted;

    @BeforeClass
    public static void beforeClass() {
        report = "fruit,quantity"
                + System.lineSeparator() + "banana,200"
                + System.lineSeparator() + "apple,90";
        excepted = new ArrayList<>();
        excepted.add("fruit,quantity");
        excepted.add("banana,200");
        excepted.add("apple,90");
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeDataToFile_isOk() {
        fileWriter.writerDataToFile(VALID_PATH, report);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(VALID_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't reade data from file: " + VALID_PATH);
        }
        Assert.assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writerDataToFile_invalidPath_notOk() {
        fileWriter.writerDataToFile(INVALID_PATH, report);
    }
}
