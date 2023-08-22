package core.basesyntax.dao;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileReadServiceImplTest {
    private static final String INPUT_FILE = "src/test/resources/InputFileTest.csv";
    private static final String WRONG_INPUT_FILE = "src/test/resources/InputFileTest2.csv";
    private FileReadService fileReadService = new FileReadServiceImpl();

    @Test
    void readDataFromFile_existingFile_Ok() {
        List<String> actual = fileReadService.readDataFromFile(INPUT_FILE);
        assertTrue(actual.size() > 0);
    }

    @Test
    void readDataFromFile_notExistingFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReadService.readDataFromFile(WRONG_INPUT_FILE);
        });
    }

    @Test
    void readDataFromFile_null_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReadService.readDataFromFile(null);
        });
    }
}
