package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvFileWriterServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/report.csv";
    private static FileWriterService writerService;

    @BeforeClass
    public static void beforeClass(){
        writerService = new CsvFileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullValue_notOk() {
        writerService.writeToFile(null, FILE_PATH);
    }

    @Test
    public void writeToFile_validValue_ok() {
        String report = "banana,30" + System.lineSeparator() + "apple,50";
        writerService.writeToFile(report, FILE_PATH);
        List<String> expected = new ArrayList<>();
        expected.add("banana,30");
        expected.add("apple,50");
        List<String> actual = readFromFile(FILE_PATH);
        Assert.assertEquals("Test failed!", expected, actual);
    }

    @Test
    public void writeToFile_emptyReport_ok() {
        String report = "";
        writerService.writeToFile(report, FILE_PATH);
        List<String> expected = Collections.emptyList();
        List<String> actual = readFromFile(FILE_PATH);
        Assert.assertEquals("Test failed!", expected, actual);
    }

    private List<String> readFromFile(String fileName) {
        File file = new File(fileName);
        List<String> transaction;
        try {
           transaction = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file " + fileName, e);
        }
        return transaction;
    }
}