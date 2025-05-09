package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ReadDataFromFileException;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImpTest {
    @Test
    void read_shouldReturnCorrectList_whenFileIsValid() {
        ReaderService readerService = new ReaderServiceImp();
        List<String> actual = readerService.read("src/test/resourcesTest/test_input.csv");
        List<String> expected = List.of("s,apple,100", "p,apple,20");
        assertEquals(expected, actual);
    }

    @Test
    void read_shouldReturnNotCorrectList_whenFileIsValid() {
        ReaderService readerService = new ReaderServiceImp();
        List<String> actual = readerService.read("src/test/resourcesTest/test_input.csv");
        List<String> wrongExpected = List.of("x,banana,999");
        assertNotEquals(wrongExpected, actual);
    }

    @Test
    void read_shouldThrowException_whenFileIsNotValid() {
        ReaderService readerService = new ReaderServiceImp();
        assertThrows(ReadDataFromFileException.class, () -> {
            readerService.read("src/test/resourcesTest/missing_input.csv");
        });
    }
}
