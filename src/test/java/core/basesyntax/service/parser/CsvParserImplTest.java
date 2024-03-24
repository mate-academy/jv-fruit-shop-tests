package core.basesyntax.service.parser;

import static core.basesyntax.TestObjects.APPLE;
import static core.basesyntax.TestObjects.FRUIT_QUANTITY;
import static core.basesyntax.TestObjects.THIRD_LINE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvParserImplTest {
    private final CsvParser csvParser = new CsvParserImpl();

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
        List<FruitTransaction> actualList = csvParser.parseFruits(Arrays.asList(THIRD_LINE));
        assertEquals(expectedList, actualList);
    }
}
