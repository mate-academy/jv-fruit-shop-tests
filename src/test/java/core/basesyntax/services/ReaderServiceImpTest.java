package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ReadDataFromFileException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImpTest {
    private static final String VALID_FILE_PATH = "src/test/resources/test_input.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/missing_input.csv";

    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImp();
    }

    @Test
    void read_shouldReturnCorrectList_whenFileIsValid() {
        List<String> actual = readerService.read(VALID_FILE_PATH);
        List<String> expected = List.of("s,apple,100", "p,apple,20");
        assertEquals(expected, actual);
    }

    @Test
    void read_shouldThrowException_whenFileIsNotValid() {
        assertThrows(ReadDataFromFileException.class, () -> {
            readerService.read(INVALID_FILE_PATH);
        });
    }
}
