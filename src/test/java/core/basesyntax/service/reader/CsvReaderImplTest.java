package core.basesyntax.service.reader;

import static core.basesyntax.TestObjects.CORRECT_PATH;
import static core.basesyntax.TestObjects.FIRST_LINE;
import static core.basesyntax.TestObjects.INCORRECT_PATH;
import static core.basesyntax.TestObjects.LINES_FROM_INPUT_FILE;
import static core.basesyntax.TestObjects.SECOND_LINE;
import static core.basesyntax.TestObjects.THIRD_LINE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.CsvReaderException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvReaderImplTest {
    private final CsvReader csvReader = new CsvReaderImpl();

    @Test
    void readFile_isOk() {
        Path inputFilePath = Path.of(CORRECT_PATH);
        LINES_FROM_INPUT_FILE.add(FIRST_LINE);
        LINES_FROM_INPUT_FILE.add(SECOND_LINE);
        LINES_FROM_INPUT_FILE.add(THIRD_LINE);
        List<String> actual = csvReader.readFile(inputFilePath);
        assertEquals(LINES_FROM_INPUT_FILE, actual);
    }

    @Test
    void readFile_incorrectPath_shouldThrowException() {
        Path inputFilePath = Path.of(INCORRECT_PATH);
        assertThrows(CsvReaderException.class, () -> csvReader.readFile(inputFilePath));
    }
}
