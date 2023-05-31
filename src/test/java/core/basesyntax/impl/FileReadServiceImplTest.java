package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.services.FileReadService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadServiceImplTest {

    private static String INPUT_FILE_PATH;
    private static String WRONG_FILE_PATH;
    private static FileReadService fileReadService;

    @BeforeClass
    public static void initialize_var() {
        fileReadService = new FileReadServiceImpl();
        INPUT_FILE_PATH = "src/test/java/resources/fromFile";
        WRONG_FILE_PATH = "incorrect/path";
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expectedList = List
                .of("type,fruit,quantity", "b,banana,20", "b,apple,100", "p,banana,13");
        List<String> list = fileReadService.readFromFile(INPUT_FILE_PATH);
        assertEquals(expectedList, list);
    }

    @Test
    public void readFromFile_WithWrongPath_not_Ok() {
        assertThrows(RuntimeException.class, () -> fileReadService
                .readFromFile(WRONG_FILE_PATH));
    }
}
