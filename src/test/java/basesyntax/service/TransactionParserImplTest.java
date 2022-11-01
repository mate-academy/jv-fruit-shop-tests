package basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationValidator;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.OperationValidatorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static List<String> testData;
    private static TransactionParser testParser;

    @BeforeClass
    public static void setUp() {
        OperationValidator validator = new OperationValidatorImpl();
        testParser = new TransactionParserImpl(validator);
        testData = new ArrayList<>();
        testData.add("type,fruit,quantity");
        testData.add("b,apple,100");
        testData.add("s,banana,200");
    }

    @Test
    public void parsTransactionGetFruit_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        List<FruitTransaction> actual = testParser.parseTransactions(testData);
        Assert.assertEquals(expected.get(0).getFruit(), actual.get(0).getFruit());
    }

    @Test
    public void parsTransactionGetCount_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        List<FruitTransaction> actual = testParser.parseTransactions(testData);
        Assert.assertEquals(expected.get(0).getCount(), actual.get(0).getCount());
    }

    @Test
    public void parsTransactionGetOperation_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        List<FruitTransaction> actual = testParser.parseTransactions(testData);
        Assert.assertEquals(expected.get(0).getOperation(), actual.get(0).getOperation());
    }

    @Test
    public void parsTransaction_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        expected.add(new FruitTransaction("s", new Fruit("banana"), 200));
        List<FruitTransaction> actual = testParser.parseTransactions(testData);
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void setTestParser_notOk() {
        testParser.parseTransactions(null);
    }
}
