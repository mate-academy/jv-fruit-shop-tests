package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.services.FileReadService;
import java.util.List;
import org.junit.Test;

public class FileReadServiceImplTest {

    private static final String INPUT_FILE_PATH = "src/test/java/resources/fromFile";
    private static final String WRONG_FILE_PATH = "incorrect/path";
    private final FileReadService fileReadService = new FileReadServiceImpl();

    @Test
    public void readFromFile_Ok() {
        List<String> expectedList = List
                .of("type,fruit,quantity", "b,banana,20", "b,apple,100", "p,banana,13");
        List<String> list = fileReadService.readFromFile(INPUT_FILE_PATH);
        assertEquals(expectedList, list);
    }

    @Test
    public void readFromFileWithWrongPath_not_Ok() {
        assertThrows(RuntimeException.class, () -> fileReadService
                .readFromFile(WRONG_FILE_PATH));
    }

    @Test
    public void readFromFileWithNullPath_notOk() {
        assertThrows(NullPointerException.class, () -> fileReadService
                .readFromFile(null));
    }
}
