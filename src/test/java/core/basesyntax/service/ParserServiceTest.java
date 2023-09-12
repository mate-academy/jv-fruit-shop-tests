package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParserServiceTest {
    private ParserService parserService = new ParserServiceImpl();

    @Test
    void getRecords_emptyInput_Ok() {
        assertTrue(parserService.getTransactions(new ArrayList<>()).isEmpty());
    }

    @Test
    void getRecords_validInput_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));

        List<FruitTransaction> actualTransactions = parserService.getTransactions(lines);

        assertEquals(lines.size() - 1, actualTransactions.size());
        assertEquals(expectedTransactions, actualTransactions);
    }
}
