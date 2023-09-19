package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.storage.Storage;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void reader_NonExistingFile_NotOk() {
        String actual = "src/test/resources/non.csv";
        assertThrows(RuntimeException.class, () -> fileReaderService.readFromFileName(actual));
    }

    @Test
    void reader_EmptyFile_Ok() {
        List<String> actual = fileReaderService
                .readFromFileName("src/test/resources/emptyFile.csv");
        assertTrue(actual.isEmpty(), "This file requires empty lines");
    }

    @Test
    void reader_ValidFile_Ok() {
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService
                .readFromFileName("src/test/resources/validFile.csv");
        assertEquals(expected, actual);
    }
}
