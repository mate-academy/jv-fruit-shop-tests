package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileService;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static final String SOURCE_FILE = "src/test/resources/fruits_database_test.csv";
    private static final String DESTINATION_FILE = "src/test/resources/report_test.csv";
    private static final String INCORRECT_FILE_PATH = "/";
    private static FileService fileService;
    private static List<String> expectedStringList;
    private static File expectedFile;

    @BeforeAll
    static void beforeAll() {
        fileService = new FileServiceImpl();
        expectedStringList = new LinkedList<>();
    }

    @AfterEach
    void tearDown() {
        try {
            expectedFile.delete();
        } catch (Exception e) {
            return;
        }
    }

    @Test
    void readFromFile_incorrectPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileService.readFromFile(INCORRECT_FILE_PATH));
    }

    @Test
    void readFromFile_correctPath_Ok() {
        expectedStringList.add("type,fruit,quantity");
        expectedStringList.add("b,banana,20");
        assertEquals(expectedStringList, fileService.readFromFile(SOURCE_FILE));
    }

    @Test
    void writeToFile_incorrectPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(INCORRECT_FILE_PATH,""));
    }

    @Test
    void writeToFile_correctPath_Ok() {
        expectedFile = new File(DESTINATION_FILE); // чи поміняти місцями?
        fileService.writeToFile(DESTINATION_FILE, "");
        assertTrue(expectedFile.exists());
    }
}
