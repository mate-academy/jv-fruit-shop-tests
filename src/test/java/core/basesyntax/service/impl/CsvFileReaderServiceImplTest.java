package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileReaderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private static final String VALID_FILEPATH = "src/test/resources/validData.csv";
    private static final String INVALID_FILEPATH = "src/test/resourrces/validData.csv";
    private static final String PATH_OF_FILE_WITH_INVALID_FIRST_LINE =
            "src/test/resources/dataWithInvalidFirstLine.csv";
    private static final String VALID_FIRST_LINE = "type,fruit,quantity";
    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();

    @Test
    void read_validFile_Ok() {
        String actual = csvFileReaderService.read(VALID_FILEPATH);
        String expected = "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,10";
        assertEquals(expected, actual);

    }

    @Test
    void read_nullFilePath_throwsRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> csvFileReaderService.read(null));
        assertEquals("Input filePath is null!", runtimeException.getMessage());
    }

    @Test
    void read_invalidFilePath_throwsRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> csvFileReaderService.read(INVALID_FILEPATH));
        assertEquals("Something wrong with file's path " + INVALID_FILEPATH,
                runtimeException.getMessage());
    }

    @Test
    void read_invalidFirstLine_throwsRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> csvFileReaderService.read(PATH_OF_FILE_WITH_INVALID_FIRST_LINE));
        String firstLine = "";
        try (BufferedReader reader = new BufferedReader(
                new FileReader(PATH_OF_FILE_WITH_INVALID_FIRST_LINE))) {
            firstLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Something wrong with file's path "
                    + PATH_OF_FILE_WITH_INVALID_FIRST_LINE, e);
        }
        assertEquals("Your first line should be " + VALID_FIRST_LINE + ", but " + "yours is "
                + firstLine, runtimeException.getMessage());
    }
}
