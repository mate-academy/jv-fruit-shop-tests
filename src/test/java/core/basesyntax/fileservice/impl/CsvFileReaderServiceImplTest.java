package core.basesyntax.fileservice.impl;

import static org.junit.Assert.fail;

import core.basesyntax.fileservice.CsvFileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/readTest.csv";
    private static final String INCORRECT_PATH = "src/test/resources/test";
    private final File readerTestFile = new File(CORRECT_PATH);
    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();

    @Test
    public void wrongFilePath_NotOk() {
        try {
            csvFileReaderService.readFromFile(INCORRECT_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("In case when path to file is wrong exception should thrown");
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(CORRECT_PATH));
        } catch (IOException e) {
            return;
        }
        List<String> actual = csvFileReaderService.readFromFile(CORRECT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        readerTestFile.delete();
    }
}
