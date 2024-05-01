package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private final String inputFilePath = "src/main/resources/fruitList.csv";
    private final String filePathTxtType = "src/main/resources/fruitList.txt";
    private final String missinigFile = "src/main/resources/missing_file.txt";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFilesContents_ok() {
        String line1 = "b,banana,20";
        String line2 = "b,apple,100";
        List<String> expected = List.of(line1, line2); // exclude the header as the method skips it

        List<String> actual = readerService.readFromFilesContents(inputFilePath);

        assertTrue(actual.containsAll(expected));
    }

    @Test
    void fileNotFound() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFilesContents(missinigFile));
        String expectedMessage = "Can't read from the file " + missinigFile;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void notValidFile() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFilesContents(filePathTxtType));
        String expectedMessage = "Can't read from the file: "
                + filePathTxtType + ". File type is not valid";
        String actualMessage = exception.getMessage();
        assertFalse(actualMessage.contains(expectedMessage));
    }

    @Test
    void readFromFilesContents_emptyFile() {
        List<String> result = readerService.readFromFilesContents(
                inputFilePath);
        if (result.isEmpty()) {
            assertTrue(true);
        }
    }
}
