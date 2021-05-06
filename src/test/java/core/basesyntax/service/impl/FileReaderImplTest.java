package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String INPUT_FILE_PATH =
            "src/test/resources/resources/fileReaderTestFile.csv";
    private static final String WRONG_FILE_PATH = "file.csv";
    private static FileReaderService fileReaderService;
    private static List<String> expected;
    private static List<String> actual;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderImpl();
        expected = new ArrayList<>();
        actual = new ArrayList<>();
    }

    @Test
    public void getDataToList_Ok() {
        actual.add("line1");
        actual.add("line2");
        expected = fileReaderService.readFromFile(INPUT_FILE_PATH);
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void getDataToListWithWrongFile_Path_NotOk() {
        fileReaderService.readFromFile(WRONG_FILE_PATH);
    }
}
