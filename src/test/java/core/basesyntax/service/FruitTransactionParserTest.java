package core.basesyntax.service;

import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.transactions.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class FruitTransactionParserTest {
    private static final String CORRECT_LINE = "b,banana,20";
    private static final FruitTransaction correctFruitTransaction =
            getFruitTransaction(Operation.BALANCE, "banana", 20);
    private static final List<FruitTransaction> FRUIT_TRANSACTIONS_ONE_LINE_LIST =
            List.of(correctFruitTransaction);
    private static final List<String> CORRECT_EXAMPLE_LIST = List.of(
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static final List<FruitTransaction> FRUIT_TRANSACTION_CORRECT_LIST =
            List.of(
                    getFruitTransaction(Operation.BALANCE, "apple", 100),
                    getFruitTransaction(Operation.SUPPLY, "banana", 100),
                    getFruitTransaction(Operation.PURCHASE, "banana", 13),
                    getFruitTransaction(Operation.RETURN, "apple", 10),
                    getFruitTransaction(Operation.PURCHASE, "apple", 20),
                    getFruitTransaction(Operation.PURCHASE, "banana", 5),
                    getFruitTransaction(Operation.SUPPLY, "banana", 50));

    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void beforeAll() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void parse_correctList_ok() {
        List<FruitTransaction> actual = fruitTransactionParser.parse(CORRECT_EXAMPLE_LIST);
        Assert.assertTrue(actual.containsAll(FRUIT_TRANSACTION_CORRECT_LIST));
    }

    @Test
    public void parse_correctString_ok() {
        List<FruitTransaction> actual = fruitTransactionParser.parse(List.of(CORRECT_LINE));
        Assert.assertTrue(actual.containsAll(FRUIT_TRANSACTIONS_ONE_LINE_LIST));
    }

    private static FruitTransaction getFruitTransaction(Operation operation, String name, int value) {
        return new FruitTransaction()
                .setOperation(operation)
                .setFruitName(name)
                .setValueOfFruit(value);
    }

}
