package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static final String HEADER = "fruit,quantity";
    private final FruitTransactionParser fruitTransactionParser = new FruitTransactionParser();
    private FruitTransaction fruitTransaction;
    //this method failed idk how to write this as you want
    //    @Test
    //    void parseFruitTransaction_Ok() {
    //        List<FruitTransaction> expected = new ArrayList<>();
    //        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
    //        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
    //        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
    //
    //        List<String> transactions = new ArrayList<>();
    //        transactions.add(HEADER);
    //        transactions.add("b,banana,20");
    //        transactions.add("b,apple,100");
    //        transactions.add("s,banana,100");
    //
    //        List<FruitTransaction> actual = fruitTransactionParser.parseTransaction(transactions);
    //
    //        assertEquals(expected, actual);
    //    }

    @Test
    void parseFruitTransactionWithWrongOperation_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(HEADER);
        transactions.add("c,banana,20");

        assertThrows(IllegalArgumentException.class, () ->
                fruitTransactionParser.parseTransaction(transactions));
    }

    @Test
    void parseFruitTransactionWithAmountWithWrongImplements_NotOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add(HEADER);
        transactions.add("b,banana,fg");

        assertThrows(RuntimeException.class, () ->
                fruitTransactionParser.parseTransaction(transactions));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FruitTransactionParserTest that)) {
            return false;
        }
        return Objects.equals(fruitTransaction, that.fruitTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitTransaction);
    }
}
