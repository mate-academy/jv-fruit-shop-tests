package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static final String HEADER = "fruit,quantity";
    private final FruitTransactionParser fruitTransactionParser = new FruitTransactionParser();

    @Test
    void parseFruitTransaction_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));

        List<String> transactions = new ArrayList<>();
        transactions.add(HEADER);
        transactions.add("b,banana,20");
        transactions.add("b,apple,100");
        transactions.add("s,banana,100");

        List<FruitTransaction> actual = fruitTransactionParser.parseTransaction(transactions);

        assertTrue(isListsEquals(expected, actual));
    }

    private static boolean isListsEquals(List<FruitTransaction> expected,
                                         List<FruitTransaction> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }

        for (int i = 0; i < expected.size(); i++) {
            if (expected.get(i).getOperation().equals(actual.get(i).getOperation())
                    && expected.get(i).getFruit().equals(actual.get(i).getFruit())
                    && expected.get(i).getQuantity() == (actual.get(i).getQuantity()) == true) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    @Test
    void parseFruitTransactionWithWrongOperation_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(HEADER);
        transactions.add("c,banana,20");
        transactions.add("b,apple,100");
        transactions.add("s,banana,100");

        assertThrows(IllegalArgumentException.class, () ->
                fruitTransactionParser.parseTransaction(transactions));
    }

    @Test
    void parseFruitTransactionWithAmountWithWrongImplements_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(HEADER);
        transactions.add("b,banana,fg");
        transactions.add("p,banana,100");

        assertThrows(RuntimeException.class, () ->
                fruitTransactionParser.parseTransaction(transactions));
    }
}
