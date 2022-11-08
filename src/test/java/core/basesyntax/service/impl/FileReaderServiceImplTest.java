package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String DATA_PATH = "src/test/resources/test_file.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/empty_file.csv";
    private static final String PATH_TO_NON_EXISTENT_FILE = "file/not/exist";
    private static FileReaderService fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_validFile_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = fileReader.readFromFile(DATA_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReader.readFromFile(PATH_TO_EMPTY_FILE);
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nonExistentFile_notOk() {
        fileReader.readFromFile(PATH_TO_NON_EXISTENT_FILE);
    }
}
