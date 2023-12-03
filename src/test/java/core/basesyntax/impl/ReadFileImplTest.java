package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReadFileService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadFileImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/inputInfo.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String PATH_TO_NOT_EXISTING_FILE =
            "src/test/resources/notExistingFile.csv";
    private static ReadFileService readFileService;

    @BeforeAll
    static void beforeAll() {
        readFileService = new ReadFileImpl();
    }

    @Test
    void readFile_validInfo_ok() {
        List<String> expectedContent = List.of("type,fruit,quantity",
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100",
                "    p,banana,13",
                "    r,apple,10",
                "    p,apple,20",
                "    p,banana,5",
                "    s,banana,50");
        List<String> actualContent = readFileService.readFile(PATH_TO_FILE);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void readFile_emptyFile_ok() {
        List<String> expectedContent = new ArrayList<>();
        List<String> actualContent = readFileService.readFile(PATH_TO_EMPTY_FILE);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void readFile_notExistingFile_notOk() {
        assertThrows(RuntimeException.class, () -> readFileService
                .readFile(PATH_TO_NOT_EXISTING_FILE));
    }
}
