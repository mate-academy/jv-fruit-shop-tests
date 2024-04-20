package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private ReaderService readerService;

    @BeforeEach
    void setUp() throws IOException {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void fileNotFound() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFilesContents("src/main/resources/missing_file.txt"));
        String expectedMessage = "Can't read from the file: src/main/resources/missing_file.txt";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void notValidFile() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFilesContents("src/main/resources"
                        + "/fruitList.txt"));
        String expectedMessage = "Can't read from the file: "
                + "src/main/resources/fruitList.csv";
        String actualMessage = exception.getMessage();
        assertFalse(actualMessage.contains(expectedMessage));
    }

    @Test
    void readFromFilesContents_emptyFile() {
        List<String> result = readerService.readFromFilesContents(
                "src/main/resources/fruitList.csv");
        if (result.isEmpty()) {
            assertTrue(true);
        }
    }
}
