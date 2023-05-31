package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReaderServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/inputTest.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private static FileReaderService readerService;

    @BeforeClass
    public static void init() {
        readerService = new CsvReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_fileNotFound_notOk() {
        String fileName = "notFound.csv";
        readerService.read(fileName);
    }

    @Test
    public void read_correctFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        Assert.assertEquals(expected, readerService.read(CORRECT_FILE_PATH));
    }

    @Test
    public void read_emptyFile_Ok() {
        Assert.assertEquals(Collections.emptyList(), readerService.read(EMPTY_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void read_inputStringNull_notOk() {
        readerService.read(null);
    }
}
