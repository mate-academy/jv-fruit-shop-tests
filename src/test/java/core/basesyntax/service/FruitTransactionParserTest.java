package core.basesyntax.service;

import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.transactions.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserTest {
    private static final String TEST_NAME_1 = "banana";
    private static final String TEST_NAME_2 = "apple";
    private static final String CORRECT_LINE = "b,banana,20";
    private static final FruitTransaction correctFruitTransaction =
            getFruitTransaction(Operation.BALANCE, TEST_NAME_1, 20);
    private static final List<FruitTransaction> FRUIT_TRANSACTIONS_ONE_LINE_LIST =
            List.of(correctFruitTransaction);
    private static final List<String> CORRECT_EXAMPLE_LIST = List.of(
            "b," + TEST_NAME_2 + ",100",
            "s," + TEST_NAME_1 + ",100",
            "p," + TEST_NAME_1 + ",13",
            "r," + TEST_NAME_2 + ",10",
            "p," + TEST_NAME_2 + ",20",
            "p," + TEST_NAME_1 + ",5",
            "s," + TEST_NAME_1 + ",50");
    private static final List<FruitTransaction> FRUIT_TRANSACTION_CORRECT_LIST =
            List.of(
                    getFruitTransaction(Operation.BALANCE, TEST_NAME_2, 100),
                    getFruitTransaction(Operation.SUPPLY, TEST_NAME_1, 100),
                    getFruitTransaction(Operation.PURCHASE, TEST_NAME_1, 13),
                    getFruitTransaction(Operation.RETURN, TEST_NAME_2, 10),
                    getFruitTransaction(Operation.PURCHASE, TEST_NAME_2, 20),
                    getFruitTransaction(Operation.PURCHASE, TEST_NAME_1, 5),
                    getFruitTransaction(Operation.SUPPLY, TEST_NAME_1, 50));

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

    private static FruitTransaction getFruitTransaction(Operation operation,
                                                        String name, int value) {
        return new FruitTransaction()
                .setOperation(operation)
                .setFruitName(name)
                .setValueOfFruit(value);
    }
}
