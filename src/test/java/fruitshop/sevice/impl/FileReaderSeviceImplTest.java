package fruitshop.sevice.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.sevice.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderSeviceImplTest {
    private FileReaderService fileReaderService = new FileReaderSeviceImpl();

    @Test
    void readFromNonExistingDirectory_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile("src/test/resources/wrongFile.csv"));
    }

    @Test
    void readFromExistingDirectory_Ok() {
        assertDoesNotThrow(
                () -> fileReaderService.readFromFile("src/test/resources/test.csv"));
        List<String> actual = fileReaderService.readFromFile("src/test/resources/test.csv");
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        assertEquals(expected, actual);
    }
}
