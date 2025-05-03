package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String EMPTY_PATH_TO_FILE = "";
    private static final String PATH_TO_NON_EXISTENT_FILE = "non-existent-file.txt";
    private static final String CORRECT_FILE_PATH = "src/main/resources/report.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/empty.csv";
    private static final List<String> dataFromFile = List.of("type,fruit,quantity",
            "b,banana,20", "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
            "p,apple,20", "p,banana,5", "s,banana,50");
    private static FileReaderService fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFromFile_NonExistFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.getDataFromFile(PATH_TO_NON_EXISTENT_FILE);
        });
    }

    @Test
    void readFromFile_CorrectFile_Ok() {
        List<String> actual = fileReader.getDataFromFile(CORRECT_FILE_PATH);
        List<String> expected = dataFromFile;
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_EmptyFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.getDataFromFile(PATH_TO_EMPTY_FILE);
        });
    }

    @Test
    void readFromFile_EmptyPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.getDataFromFile(EMPTY_PATH_TO_FILE);
        });
    }
}
