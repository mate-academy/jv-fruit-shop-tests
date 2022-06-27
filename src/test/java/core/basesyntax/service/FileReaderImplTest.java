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
    public void readDataFromStorage_pathNameIsNull_notOk() {
        fileReaderService.readDataFromStorage(null);
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromStorage_pathNameIsWrong_notOk() {
        fileReaderService.readDataFromStorage(WRONG_FILE_FOR_READER);
    }

    @Test
    public void readDataFromStorage_pathNameIsValid_isOk() {
        List<String> actual = fileReaderService.readDataFromStorage(TEST_FILE_FOR_READER);
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,mango,20");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readDataFromStorage_validDataAndPath_isOk() {
        List<String> expected = List.of("type,fruit,quantity", "b,mango,20");
        List<String> actual = fileReaderService.readDataFromStorage(TEST_FILE_FOR_READER);
        Assert.assertEquals(expected, actual);
    }
}
