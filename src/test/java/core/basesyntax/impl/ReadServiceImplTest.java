package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReadService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadServiceImplTest {
    private ReadService readService;

    @BeforeEach
    void setUp() {
        readService = new ReadServiceImpl();
    }

    @Test
    void file_null_not_ok() {
        assertThrows(RuntimeException.class,
                () -> readService.read(null),
                "File can't be null");
    }

    @Test
    void readFile_ok() {
        String inputFilePath = "src/main/java/valiData.csv";
        List<String> expected = List.of("banana,152", "apple,90", "banana,50", "apple,30");
        List<String> actual;
        actual = readService.read(inputFilePath);
        assertEquals(expected, actual);
    }

    @Test
    void read_nonExistingFile_throwsRuntimeException() {
        String inputFile = "src/main/resources/nonexistingfile.txt";
        assertThrows(RuntimeException.class, () -> readService.read(inputFile));
    }
}
