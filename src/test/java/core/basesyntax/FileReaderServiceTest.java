package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String INPUT_FILE_EXIST = "src/main/resources/input.csv";
    private static final String INPUT_FILE_NOT_EXIST = "src/main/resources/notExistFile.csv";
    private static final List<String> TEST_LIST = List.of("b,banana,20", "b,apple,100",
            "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
            "s,banana,50");
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    public void readFromFile_ExistFile_Ok() {
        List<String> actual = fileReaderService.readFromFile(INPUT_FILE_EXIST);
        assertEquals(TEST_LIST, actual);
    }

    @Test
    public void readFromFile_NotExistFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.readFromFile(INPUT_FILE_NOT_EXIST);
        });
    }
}
