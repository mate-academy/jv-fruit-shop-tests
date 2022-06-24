package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String NOT_EXISTING_FILE_PATH = "notExistingFile.csv";
    private static final String TEST_FILE_PATH = "src/test/resources/testFile.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_goodTestFile_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        List<String> actual = fileReaderService.readFromFile(TEST_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyFile_notOk() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.readFromFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistingFile_notOk() {
        fileReaderService.readFromFile(NOT_EXISTING_FILE_PATH);
    }
}
