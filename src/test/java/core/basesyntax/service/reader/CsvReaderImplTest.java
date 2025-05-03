package core.basesyntax.service.reader;

import static core.basesyntax.TestConstants.BUY_HUNDRED_APPLES_LINE;
import static core.basesyntax.TestConstants.BUY_TWENTY_BANANAS_LINE;
import static core.basesyntax.TestConstants.CORRECT_PATH;
import static core.basesyntax.TestConstants.INCORRECT_PATH;
import static core.basesyntax.TestConstants.TYPE_FRUIT_QUANTITY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.CsvReaderException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderImplTest {
    private CsvReader csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
    }

    @Test
    void readFile_isOk() {
        List<String> linesFromInputFile = new ArrayList<>();
        Path inputFilePath = Path.of(CORRECT_PATH);
        linesFromInputFile.add(TYPE_FRUIT_QUANTITY);
        linesFromInputFile.add(BUY_TWENTY_BANANAS_LINE);
        linesFromInputFile.add(BUY_HUNDRED_APPLES_LINE);
        List<String> actual = csvReader.readFile(inputFilePath);
        assertEquals(linesFromInputFile, actual);
    }

    @Test
    void readFile_incorrectPath_shouldThrowException() {
        Path inputFilePath = Path.of(INCORRECT_PATH);
        CsvReaderException exception = assertThrows(CsvReaderException.class,
                () -> csvReader.readFile(inputFilePath));
        assertEquals("Error reading file: " + INCORRECT_PATH, exception.getMessage());
    }
}
