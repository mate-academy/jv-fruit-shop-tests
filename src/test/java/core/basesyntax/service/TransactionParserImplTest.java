package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.OperationValidatorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static List<String> testData;
    private static TransactionParser testParser;

    @BeforeClass
    public static void setUp() {
        OperationValidation validator = new OperationValidatorImpl();
        testParser = new TransactionParserImpl(validator);
        testData = new ArrayList<>();
    }

    @Test
    public void parsTransactionGetFruit_ok() {
        testData.add("b,apple,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, new Fruit("apple"), 100));
        List<FruitTransaction> actual = testParser.parseTransaction(testData);
        assertEquals(expected.get(0).getFruit(), actual.get(0).getFruit());
        assertEquals(expected.get(0).getQuantity(), actual.get(0).getQuantity());
        assertEquals(expected.get(0).getOperation(), actual.get(0).getOperation());
    }

    @Test
    public void parsTransaction_singleElement_ok() {
        testData.add("b,apple,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, new Fruit("apple"), 100));
        List<FruitTransaction> actual = testParser.parseTransaction(testData);
        assertEquals(expected.get(0).getFruit(), actual.get(0).getFruit());
    }

    @Test
    public void parsTransaction_manyElement_ok() {
        testData.add("b,apple,100");
        testData.add("b,apple,150");
        testData.add("s,banana,200");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, new Fruit("apple"), 100));
        expected.add(new FruitTransaction(Operation.BALANCE, new Fruit("apple"), 150));
        expected.add(new FruitTransaction(Operation.BALANCE, new Fruit("banana"), 200));
        List<FruitTransaction> actual = testParser.parseTransaction(testData);
        assertEquals(expected.get(0).getFruit(), actual.get(0).getFruit());

    }
}
