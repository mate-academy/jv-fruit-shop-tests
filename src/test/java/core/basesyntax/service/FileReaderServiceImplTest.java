package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private ReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    void readFromNonExistingDirectory_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile("src/main/resources/wrongFile.txt"));
    }

    @Test
    void readFromExistingDirectory_Ok() {
        assertDoesNotThrow(
                () -> fileReaderService.readFromFile("src/main/resources/text.txt"));
        List<String> actual = fileReaderService.readFromFile("src/main/resources/text.txt");
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13","r,apple,10", "p,apple,20","p,banana,5","s,banana,50");
        assertEquals(expected, actual);
    }
}
