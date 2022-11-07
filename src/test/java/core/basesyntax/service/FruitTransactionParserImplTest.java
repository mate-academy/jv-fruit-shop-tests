package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.service.impl.OperationValidatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static List<String> testData;
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void setUp() {
        testData = new ArrayList<>();
        OperationValidator operationValidator = new OperationValidatorImpl();
        fruitTransactionParser = new FruitTransactionParserImpl(operationValidator);
    }

    @Before
    public void beforeAll() {
        testData.clear();
        testData.add("type,fruit,quantity");
    }

    @Test
    public void parseTransaction_getFruit_ok() {
        testData.add("b,banana,20");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 20));
        List<FruitTransaction> actual = fruitTransactionParser.parseFruitTransactions(testData);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parsTransaction_wrongFruit_notOk() {
        testData.add("b,error,0");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 20));
        List<FruitTransaction> actual = fruitTransactionParser.parseFruitTransactions(testData);
        Assert.assertNotEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parseTransaction_element_ok() {
        testData.add("b,banana,20");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 20));
        List<FruitTransaction> actual = fruitTransactionParser.parseFruitTransactions(testData);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseTransaction_manyElement_ok() {
        testData.add("b,banana,20");
        testData.add("b,apple,30");
        testData.add("s,apple,50");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 20));
        expected.add(new FruitTransaction("b", new Fruit("apple"), 30));
        expected.add(new FruitTransaction("s", new Fruit("apple"), 50));
        List<FruitTransaction> actual = fruitTransactionParser.parseFruitTransactions(testData);
        Assert.assertEquals(expected, actual);
    }
}
