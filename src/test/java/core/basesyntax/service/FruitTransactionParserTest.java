package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static final String HEADER = "fruit,quantity";
    private final FruitTransactionParser fruitTransactionParser = new FruitTransactionParser();

    @Test
    void parseFruitTransaction_Ok() {
        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100)
        );

        List<String> transactions = Arrays.asList(
                HEADER,
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );

        List<FruitTransaction> actual = fruitTransactionParser.parseTransaction(transactions);

        assertEquals(expected, actual, "Parsed transactions do not match the expected results.");
    }

    @Test
    void parseFruitTransactionWithWrongOperation_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(HEADER);
        transactions.add("c,banana,20");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fruitTransactionParser.parseTransaction(transactions));

        assertEquals("c operation doesn't exist.", exception.getMessage());
    }

    @Test
    void parseFruitTransactionWithAmountWithWrongImplements_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(HEADER);
        transactions.add("b,banana,fg");

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fruitTransactionParser.parseTransaction(transactions));

        assertEquals("For input string: \"fg\"", exception.getMessage());
    }
}
