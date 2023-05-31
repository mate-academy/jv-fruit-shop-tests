package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static Reader reader;

    @BeforeClass
    public static void init() {
        reader = new ReaderImpl();
    }

    @Test
    public void read_someDataFromFile_ok() {
        File testCsvReaderFile = new File("src/test/resources/reader_ValidData.csv");
        String content = "hello " + "Denik 11" + System.lineSeparator()
                + "testing " + "some..,," + System.lineSeparator()
                + "guffi goes... ther1e" + System.lineSeparator() + "";
        try {
            Files.write(testCsvReaderFile.toPath(), Collections.singleton(content));
        } catch (IOException e) {
            throw new RuntimeException("Can`t write test data to file "
                    + testCsvReaderFile.getName(), e);
        }
        List<String> expected = new ArrayList<>();
        expected.add("hello Denik 11");
        expected.add("testing some..,,");
        expected.add("guffi goes... ther1e");
        expected.add("");
        Assert.assertEquals(expected, reader.readFromFile(testCsvReaderFile.getPath()));
    }

    @Test(expected = RuntimeException.class)
    public void read_nullInputValue_notOk() {
        reader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_unValidFilePathInput_notOk() {
        reader.readFromFile("some/unValid/path");
    }

    @Test
    public void read_emptyFile_ok() {
        File testEmptyFile = new File("src/test/resources/reader_emptyFile.csv");
        try {
            testEmptyFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can`t create file " + testEmptyFile.getName(), e);
        }
        List<String> expected = new ArrayList<>();
        Assert.assertEquals(expected, reader.readFromFile(testEmptyFile.getPath()));
    }
}
