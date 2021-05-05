package core.basesyntax.service.fileservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;
    private static final String NOT_EXIST_FILEPATH = "src\\test\\resources\\invalid.csv";
    private static final String EMPTY_FILEPATH = "src\\test\\resources\\empty.csv";
    private static final String VALID_FILEPATH = "src\\test\\resources\\valid.csv";

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void fileReaderService_nullFilePath_notOk() {
        fileReaderService.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void fileReaderService_filePathNotExist_notOk() {
        fileReaderService.read(NOT_EXIST_FILEPATH);
    }

    @Test
    public void fileReaderService_readEmptyFile_isOk() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.read(EMPTY_FILEPATH);
        assertEquals(expected, actual);
    }

    @Test
    public void fileReaderService_readFile_isOk() {
        List<String> expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100");
        List<String> actual = fileReaderService.read(VALID_FILEPATH);
        assertEquals(expected, actual);
    }
}
