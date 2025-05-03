package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserServiceImplTest {
    private static FruitTransactionParserService parser;
    private static List<FruitTransaction> expected_transactions;

    @BeforeClass
    public static void beforeClass() {
        parser = new FruitTransactionParserServiceImpl();
        expected_transactions = new ArrayList<>();
        expected_transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 20));
        expected_transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        expected_transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 100));
        expected_transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 13));
        expected_transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("apple"), 10));
        expected_transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"), 20));
        expected_transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 5));
        expected_transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 50));
    }

    @Test
    public void parse_isValid() {
        List<FruitTransaction> actual = parser.parse(List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"));
        assertEquals(expected_transactions, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_null_isNotValid() {
        parser.parse(null);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongLetterOfOperation_isNotValid() {
        List<FruitTransaction> actual = parser.parse(List.of("type,fruit,quantity",
                "q,banana,20",
                "q,apple,100",
                "q,banana,100",
                "q,banana,13",
                "q,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"));
        assertEquals(expected_transactions, actual);
    }
}
