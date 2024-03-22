package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String validPath = "src/test/resources/test_data.csv";
    private static final String invalidPath = "non_existing_file.csv";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new FileReaderServiceImpl();
    }


    @Test
    void readFromFile_existingFile_success() {
        List<String> data = readerService.readFromFile(validPath);
        List<String> expected = List.of("b,banana,20",
                "b,grape,30",
                "b,apple,100",
                "s,banana,100",
                "p,grape,10",
                "p,banana,30",
                "p,apple,20",
                "r,grape,5",
                "r,apple,10",
                "p,banana,5",
                "s,banana,50");
        assertFalse(data.isEmpty());
        assertEquals(expected, data);
    }

    @Test
    void readFromFile_nonExistingFile_throwsException() {
        String expectedMessage = "Can`t read from file " + invalidPath;
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(invalidPath));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
