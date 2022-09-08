package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderFileServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderFileServiceTest {
    private static final String FULL_FILE = "src/test/java/resources/before.csv";
    private static final String EMPTY_FILE = "src/test/java/resources/empty.csv";
    private static final String NOT_EXIST_FILE = "";
    private static FileReaderService fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderFileServiceImpl();
    }

    @Test
    public void readOfFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("[type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50]");
        List<String> actual = fileReader.read(FULL_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readOfEmptyFile_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReader.read(EMPTY_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readOfFile_NotOk() {
        fileReader.read(NOT_EXIST_FILE);
    }

}
