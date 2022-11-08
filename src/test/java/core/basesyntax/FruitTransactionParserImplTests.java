package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTests {
    private static FruitTransactionParser transactionParser;
    private static List<String> testData;

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new FruitTransactionParserImpl();
        testData = new ArrayList<>();
    }

    @Test
    public void parseFruitTransaction_emptyData_ok() {
        testData.add("type,fruit,quantity");
        List<FruitTransaction> actual = transactionParser.parseFruitTransaction(testData);
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void parseFruitTransaction_normalData_ok() {
        testData.add("type,fruit,quantity");
        testData.add("b,banana,20");
        testData.add("b,apple,100");
        testData.add("s,banana,100");
        testData.add("r,apple,10");
        testData.add("p,banana,5");

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        new Fruit("banana"), 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        new Fruit("apple"), 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        new Fruit("banana"), 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        new Fruit("apple"), 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        new Fruit("banana"), 5));
        List<FruitTransaction> actual = transactionParser.parseFruitTransaction(testData);
        assertEquals(expected, actual);
    }

    @Test
    public void parseFruitTransaction_threeElement_ok() {
        testData.add("b,apple,100");
        testData.add("b,apple,150");
        testData.add("s,banana,200");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 150));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 200));
        List<FruitTransaction> actual = transactionParser.parseFruitTransaction(testData);
        assertEquals(expected, actual);
    }

    @Test
    public void parseFruitTransaction_oneElement_ok() {
        testData.add("b,apple,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 100));
        List<FruitTransaction> actual = transactionParser.parseFruitTransaction(testData);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        testData.clear();
    }
}
