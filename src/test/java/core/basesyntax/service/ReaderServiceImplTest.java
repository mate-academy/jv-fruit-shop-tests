package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReaderServiceImplTest {
    private String pathToFile;
    private List<String> dataFromFile;
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
        dataFromFile = new ArrayList<>();
    }

    @Test
    void readerService_invalidFIlePath_notOk() {
        pathToFile = "src/test/resources-invalidPath";
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(pathToFile));
        Assertions.assertEquals("It is not possible to read data from the file via"
                + " this path - " + pathToFile, runtimeException.getMessage());
    }

    @Test
    void readerService_nullPath_notOk() {
        pathToFile = null;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(pathToFile));
        Assertions.assertEquals("File path cannot be null", exception.getMessage());
    }

    @Test
    void readerService_pathIsEmpty_notOk() {
        pathToFile = "";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(pathToFile));
        Assertions.assertEquals("It is not possible to read data from the file via"
                + " this path - " + pathToFile, exception.getMessage());
    }

    @Test
    void readerService_validPath_Ok() {
        pathToFile = "src/test/resources/input_data";
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        dataFromFile.add("r,apple,10");
        dataFromFile.add("p,apple,20");
        dataFromFile.add("p,banana,5");
        dataFromFile.add("s,banana,50");
        List<String> resultList = readerService.readFromFile(pathToFile);
        Assertions.assertEquals(resultList.size(), dataFromFile.size());
        Assertions.assertEquals(resultList.get(0).trim(), dataFromFile.get(0));
        Assertions.assertEquals(resultList.get(resultList.size() / 2).trim(),
                dataFromFile.get(dataFromFile.size() / 2));
        Assertions.assertEquals(resultList.get(resultList.size() - 1).trim(),
                dataFromFile.get(dataFromFile.size() - 1));
    }
}