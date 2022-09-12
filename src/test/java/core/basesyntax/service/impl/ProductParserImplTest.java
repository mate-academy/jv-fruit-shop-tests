package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProductParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductParserImplTest {
    private static final String BALANCE_TRANSACTION = "b,banana,20";
    private static final String PURCHASE_TRANSACTION = "p,banana,13";
    private static final String RETURN_TRANSACTION = "r,apple,10";
    private static final String SUPPLY_TRANSACTION = "s,banana,100";
    private ProductParser productParser;

    @Before
    public void setUp() {
        productParser = new ProductParserImpl();
    }

    @Test
    public void validDataBalanceIs_Ok() {
        String nameOfFruit = "banana";
        int amount = 20;
        FruitTransaction fruitTransaction = productParser.parse(BALANCE_TRANSACTION);
        Assert.assertEquals("Fruit operation: ",
                FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());
        Assert.assertEquals("Fruit name: ", nameOfFruit, fruitTransaction.getName());
        Assert.assertEquals("Fruit amount: ", amount, fruitTransaction.getAmount());
    }

    @Test
    public void validDataPurchaseIs_Ok() {
        String nameOfFruit = "banana";
        int amount = 13;
        FruitTransaction fruitTransaction = productParser.parse(PURCHASE_TRANSACTION);
        Assert.assertEquals("Fruit operation: ",
                FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation());
        Assert.assertEquals("Fruit name: ", nameOfFruit, fruitTransaction.getName());
        Assert.assertEquals("Fruit amount: ", amount, fruitTransaction.getAmount());
    }

    @Test
    public void validDataReturnIs_Ok() {
        String nameOfFruit = "apple";
        int amount = 10;
        FruitTransaction fruitTransaction = productParser.parse(RETURN_TRANSACTION);
        Assert.assertEquals("Fruit operation: ",
                FruitTransaction.Operation.RETURN, fruitTransaction.getOperation());
        Assert.assertEquals("Fruit name: ", nameOfFruit, fruitTransaction.getName());
        Assert.assertEquals("Fruit amount: ", amount, fruitTransaction.getAmount());
    }

    @Test
    public void validDataSupplyIs_Ok() {
        String nameOfFruit = "banana";
        int amount = 100;
        FruitTransaction fruitTransaction = productParser.parse(SUPPLY_TRANSACTION);
        Assert.assertEquals("Fruit operation: ",
                FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
        Assert.assertEquals("Fruit name: ", nameOfFruit, fruitTransaction.getName());
        Assert.assertEquals("Fruit amount: ", amount, fruitTransaction.getAmount());
    }

    @Test
    public void validDataIs_Ok() {
        List<String> list = new ArrayList<>();
        list.add(BALANCE_TRANSACTION);
        list.add(PURCHASE_TRANSACTION);
        list.add(RETURN_TRANSACTION);
        list.add(SUPPLY_TRANSACTION);
        List<FruitTransaction> fruitTransactionList = productParser.parseAll(list);
        Assert.assertEquals(list.size(), fruitTransactionList.size());
    }

    @Test(expected = RuntimeException.class)
    public void validDataFormatIs_NotOk() {
        String line = "b,0,banana";
        productParser.parse(line);
    }
}
