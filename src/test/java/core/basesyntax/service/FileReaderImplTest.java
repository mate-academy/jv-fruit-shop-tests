package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String TEST_FILE_FOR_READER = "src/test/resources/FileForReaderTest.csv";
    private static final String WRONG_FILE_FOR_READER = "src/test/resoursec/FileForReaderTest.csv";
    private static FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void pathNameIsNull_notOk() {
        fileReaderService.readTheFruitsStorage(null);
    }

    @Test(expected = RuntimeException.class)
    public void pathNameIsWrong_notOk() {
        fileReaderService.readTheFruitsStorage(WRONG_FILE_FOR_READER);
    }

    @Test
    public void pathNameIsValid_isOk() {
        List<String> actual = fileReaderService.readTheFruitsStorage(TEST_FILE_FOR_READER);
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,mango,20");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readerIsWorking_isOk() {
        List<String> expected = List.of("type,fruit,quantity", "b,mango,20");
        List<String> actual = fileReaderService.readTheFruitsStorage(TEST_FILE_FOR_READER);
        Assert.assertEquals(expected, actual);
    }
}
