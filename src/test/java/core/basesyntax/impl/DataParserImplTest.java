package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParser;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class DataParserImplTest {
    private final DataParser dataParser = new DataParserImpl();
    private final List<String> fileContent = List.of("b,banana,20", "s,apple,100");
    private final List<String> invalidFileContent = List.of("g,banana,30", "l,apple,50");

    @Test
    void parse_correctAmountOfFruitTransactions_Ok() {
        List<FruitTransaction> fruitTransactions = dataParser.parse(fileContent);
        int expected = 2;
        int actual = fruitTransactions.size();
        assertEquals(expected, actual);
    }

    @Test
    void parse_correctData_Ok() {
        List<FruitTransaction> actualFruitTransactions = dataParser.parse(fileContent);
        List<FruitTransaction> expectedFruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100));
        assertEquals(expectedFruitTransactions, actualFruitTransactions);
    }

    @Test
    void parse_invalidOperation_NotOk() {
        assertThrows(NoSuchElementException.class, () -> dataParser.parse(invalidFileContent));
    }
}
