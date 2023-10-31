package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static TransactionParser transactionParser;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void getFruitTransaction_validData_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100));
        List<String> fileData = new ArrayList<>(List.of("type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"));
        assertEquals(expected, transactionParser.getFruitTransactions(fileData));
    }

    @Test
    void getFruitTransaction_inValidOperation_Ok() {
        List<String> fileData = new ArrayList<>(List.of("type,fruit,quantity",
                "x,banana,20"));
        assertThrows(IllegalArgumentException.class,
                () -> transactionParser.getFruitTransactions(fileData));
    }
}
