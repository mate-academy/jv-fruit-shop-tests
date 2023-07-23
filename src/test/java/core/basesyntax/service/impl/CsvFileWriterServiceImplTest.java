package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileWriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceImplTest {
    private static final String VALID_INPUT_STRING = "banana,107" + System.lineSeparator()
                                                    + "apple,90" + System.lineSeparator();
    private final CsvFileWriterService csvFileWriterService = new CsvFileWriterServiceImpl();

    @Test
    void write_validInputString_noExceptions() {
        csvFileWriterService.write(VALID_INPUT_STRING);
        String expected = "fruit,quantity" + System.lineSeparator()
                        + "banana,107" + System.lineSeparator()
                        + "apple,90" + System.lineSeparator();
        String actual = readOutputFile() + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    void write_nullInputString_throwRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> csvFileWriterService.write(null));
        assertEquals("Input report is null!", runtimeException.getMessage());
    }

    private String readOutputFile() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/main/resources/output.csv"))) {
            return bufferedReader
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file(");
        }
    }
}
