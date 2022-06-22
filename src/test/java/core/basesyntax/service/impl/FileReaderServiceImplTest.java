package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String INPUT_FILE_PATH_TEST =
            "src/test/resources/fruit_shop_input_file.csv";
    private static final String EMPTY_FILE_TEST =
            "src/test/resources/fruit_shop_empty_file_test.csv";
    private static final String INPUT_INVALID_FILE_PATH_TEST =
            "src/test/resources/fruit_shop_input_file_invalid.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFile_ok() {
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
        List<String> actual = fileReaderService.readFile(INPUT_FILE_PATH_TEST);
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.readFile(EMPTY_FILE_TEST);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_invalidFile_notOk() {
        fileReaderService.readFile(INPUT_INVALID_FILE_PATH_TEST);
    }
}
