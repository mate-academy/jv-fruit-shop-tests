package core.basesyntax.service.parser;

import static core.basesyntax.TestConstants.APPLE;
import static core.basesyntax.TestConstants.BUY_HUNDRED_APPLES_LINE;
import static core.basesyntax.TestConstants.FRUIT_QUANTITY;
import static core.basesyntax.TestConstants.INCORRECT_PATH;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.exception.CsvReaderException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.reader.CsvReader;
import core.basesyntax.service.reader.CsvReaderImpl;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvParserImplTest {
    private CsvParser csvParser;
    private CsvReader csvReader;

    @BeforeEach
    void setUp() {
        csvParser = new CsvParserImpl();
        csvReader = new CsvReaderImpl();
    }

    @Test
    void parseFruits_emptyList_shouldReturnEmptyList_isOk() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        List<FruitTransaction> fruitTransactions = csvParser.parseFruits(new ArrayList<>());
        assertEquals(fruitTransactionList, fruitTransactions);
    }

    @Test
    void parseFruits_singleValidEntry_shouldReturnListWithOneEntry() {
        List<FruitTransaction> expectedList = Arrays.asList(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE.toLowerCase(), FRUIT_QUANTITY)
        );
        List<FruitTransaction> actualList = csvParser.parseFruits(
                Arrays.asList(BUY_HUNDRED_APPLES_LINE));
        assertEquals(expectedList, actualList);
    }

    @Test
    void readFile_incorrectPath_shouldThrowExceptionWithErrorMessage() {
        Path inputFilePath = Path.of(INCORRECT_PATH);
        CsvReaderException exception = assertThrows(CsvReaderException.class,
                () -> csvReader.readFile(inputFilePath));
        String expectedErrorMessage = "Error reading file: " + INCORRECT_PATH;
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
