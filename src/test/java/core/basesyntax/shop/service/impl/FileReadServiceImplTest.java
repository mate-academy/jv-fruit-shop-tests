package core.basesyntax.shop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.shop.service.FileReadService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReadServiceImplTest {
    private static FileReadService fileReadService;
    private static final String LINE_FIRST = "type,fruit,quantity";
    private static final String LINE_SECOND = "b,banana,20";
    private static final String LINE_THIRD = "p,banana,5";
    private static final String PATH_TO_FILE = "src/test/resources/databasetest.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/resources/wrong.csv";
    private static final List<String> expectedDataList = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() {
        fileReadService = new FileReadServiceImpl();
        expectedDataList.add(LINE_FIRST);
        expectedDataList.add(LINE_SECOND);
        expectedDataList.add(LINE_THIRD);
    }

    @Test
    public void readFromFile_ok() {
        List<String> actualFileData = fileReadService.read(PATH_TO_FILE);
        assertEquals(expectedDataList, actualFileData);
    }

    @Test
    public void readWithWrongPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReadService.read(WRONG_PATH_TO_FILE));
    }

}
