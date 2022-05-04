package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;
    private static FruitTransaction testFruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Before
    public void setUp() {
        testFruitTransaction = new FruitTransaction();
    }

    @Test
    public void parseLine_getBalanceTransactionType_ok() {
        String lineFromFile = "b,banana,20";
        testFruitTransaction.setOperation(FruitTransaction.OperationType.BALANCE);
        testFruitTransaction.setFruitName("banana");
        testFruitTransaction.setQuantity(20);
        FruitTransaction actual = parserService.parse(lineFromFile);
        FruitTransaction expected = testFruitTransaction;
        Assert.assertEquals(expected.getOperation(), actual.getOperation());
        Assert.assertEquals(expected.getFruitName(), actual.getFruitName());
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test
    public void parseLine_getSupplyTransactionType_ok() {
        String lineFromFile = "s,banana,100";
        testFruitTransaction.setOperation(FruitTransaction.OperationType.SUPPLY);
        testFruitTransaction.setFruitName("banana");
        testFruitTransaction.setQuantity(100);
        FruitTransaction actual = parserService.parse(lineFromFile);
        FruitTransaction expected = testFruitTransaction;
        Assert.assertEquals(expected.getOperation(), actual.getOperation());
        Assert.assertEquals(expected.getFruitName(), actual.getFruitName());
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test
    public void parseLine_getReturnTransactionType_ok() {
        String lineFromFile = "r,apple,10";
        testFruitTransaction.setOperation(FruitTransaction.OperationType.RETURN);
        testFruitTransaction.setFruitName("apple");
        testFruitTransaction.setQuantity(10);
        FruitTransaction actual = parserService.parse(lineFromFile);
        FruitTransaction expected = testFruitTransaction;
        Assert.assertEquals(expected.getOperation(), actual.getOperation());
        Assert.assertEquals(expected.getFruitName(), actual.getFruitName());
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test
    public void parseLine_getPurchaseTransactionType_ok() {
        String lineFromFile = "p,banana,5";
        testFruitTransaction.setOperation(FruitTransaction.OperationType.PURCHASE);
        testFruitTransaction.setFruitName("banana");
        testFruitTransaction.setQuantity(5);
        FruitTransaction actual = parserService.parse(lineFromFile);
        FruitTransaction expected = testFruitTransaction;
        Assert.assertEquals(expected.getOperation(), actual.getOperation());
        Assert.assertEquals(expected.getFruitName(), actual.getFruitName());
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_wrongOperationType_notOk() {
        parserService.parse("write-off,banana,20");
    }

    @Test(expected = RuntimeException.class)
    public void parseEmptyLine_notOk() {
        parserService.parse("");
    }
}
