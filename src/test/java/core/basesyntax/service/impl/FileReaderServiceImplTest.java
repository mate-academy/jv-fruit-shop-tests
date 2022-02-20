package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_SOURCE_FILE_PATH
            = "src/test/java/core/basesyntax/resource/testFile.csv";
    private static final String INVALID_SOURCE_FILE_PATH
            = "src/main/java/core/basesyntax/input.csv";
    private static final String TEST_STRING_ONE = "test,file";
    private static final String TEST_STRING_TWO = "testString,1";
    private static final String TEST_STRING_THREE = "testString,2";
    private static FileReaderService fileReaderService;
    private static List<String> testStringsList;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
        testStringsList = new ArrayList<>();
        testStringsList.add(TEST_STRING_ONE);
        testStringsList.add(TEST_STRING_TWO);
        testStringsList.add(TEST_STRING_THREE);
    }

    @Test(expected = RuntimeException.class)
    public void read_sourceFilePathNull_notOk() {
        fileReaderService.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_sourceFileInvalidPath_notOk() {
        fileReaderService.read(INVALID_SOURCE_FILE_PATH);
    }

    @Test
    public void read_validPath_ok() {
        List<String> actualList = fileReaderService.read(VALID_SOURCE_FILE_PATH);
        assertEquals(testStringsList, actualList);

    }
}
