package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReadService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadServiceImplTest {
    private static ReadService readService;
    private static List<String> dataFromFile;
    private static final String INPUT_FILE = "src/main/java/core/basesyntax/files/input_file.csv";
    private static final String MASSAGE = "Should throw runtime exception.";

    @BeforeAll
    public static void init() {
        readService = new ReadServiceImpl();
        dataFromFile = new ArrayList<>();
    }

    @Test
    void reader_fileNotExist_notOk() {
        String nonExistingFile = "not_exist";
        assertThrows(RuntimeException.class,
                () -> readService.readFromFile(nonExistingFile),
                MASSAGE);
    }

    @Test
    void reader_fileIsEmpty_notOk() {
        dataFromFile = readService.readFromFile(INPUT_FILE);
        assertThrows(AssertionError.class,
                () -> assertTrue(dataFromFile.isEmpty()),
                MASSAGE);
    }

    @Test
    void reader_readFromFile_Ok() {
        dataFromFile = readService.readFromFile(INPUT_FILE);
        List<String> newList = new ArrayList<>();
        newList.add("b,banana,20");
        newList.add("b,apple,100");
        newList.add("s,banana,100");
        newList.add("p,banana,13");
        newList.add("r,apple,10");
        newList.add("p,apple,20");
        newList.add("p,banana,5");
        newList.add("s,banana,50");
        assertEquals(dataFromFile, newList);
    }
}
