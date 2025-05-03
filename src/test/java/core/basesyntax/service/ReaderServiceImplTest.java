package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    public static void setUpBeforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_invalidFIlePath_notOk() {
        String pathToFile = "src/test/resources-invalidPath";
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(pathToFile));
        assertEquals("It is not possible to read data from the file via"
                + " this path - " + pathToFile, runtimeException.getMessage());
    }

    @Test
    void readFromFile_nullPath_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(null));
        assertEquals("File path cannot be null", exception.getMessage());
    }

    @Test
    void readFromFile_pathIsEmpty_notOk() {
        String pathToFile = "";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(pathToFile));
        assertEquals("It is not possible to read data from the file via"
                + " this path - " + pathToFile, exception.getMessage());
    }

    @Test
    void readFromFile_validPath_Ok() {
        String pathToFile = "src/test/resources/input_data";
        List<String> dataFromFile = List.of("type,fruit,quantity",
                ("b,banana,20"),
                ("b,apple,100"),
                ("s,banana,100"),
                ("p,banana,13"),
                ("r,apple,10"),
                ("p,apple,20"),
                ("p,banana,5"),
                ("s,banana,50"));
        List<String> resultList = readerService.readFromFile(pathToFile);
        assertEquals(resultList.size(), dataFromFile.size());
        assertEquals(resultList.get(resultList.size() - 1).trim(),
                dataFromFile.get(dataFromFile.size() - 1));
    }
}
