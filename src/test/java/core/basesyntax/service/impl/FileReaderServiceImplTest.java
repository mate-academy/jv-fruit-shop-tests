package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String VALID_FILE_TO_READ = "src/main/resources/input.csv";
    private static final String WRONG_FILE_TO_READ = "1/1/1/input.csv";
    private static final List<String> EXPECTED_READER_RESULT = List.of(
            " type,fruit,quantity",
            "    b,banana,100",
            "    b,apple,100",
            "    s,banana,10",
            "    s,apple,10",
            "    r,banana,10",
            "    r,apple,10",
            "    p,banana,11",
            "    p,apple,11");
    private FileReaderService csvFileReaderService;

    @BeforeEach
    void setUp() {
        csvFileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void fileToRead_InputOk_EqualsTrue() {
        List<String> actual = csvFileReaderService.readFromFile(VALID_FILE_TO_READ);
        assertEquals(EXPECTED_READER_RESULT, actual);
    }

    @Test
    void fileToRead_InputWrong_ThrownException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> csvFileReaderService.readFromFile(WRONG_FILE_TO_READ));
        assertEquals("Can't read data from file: " + WRONG_FILE_TO_READ, exception.getMessage());
    }
}
