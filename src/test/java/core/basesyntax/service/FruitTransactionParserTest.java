package core.basesyntax.service;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserTest {
    private static FruitTransactionParser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new FruitTransactionParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void getFruitTransactionsList_nullInput_NotOk() {
        parser.getFruitTransactionsList(null);
    }

    @Test(expected = RuntimeException.class)
    public void getFruitTransactionsList_DataContainsNull_notOk() {
        parser.getFruitTransactionsList(List.of(null));
    }

    @Test
    public void getFruitTransactionsList_Ok() {
        FruitTransaction bananaFruitTransaction = new FruitTransaction();
        bananaFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        bananaFruitTransaction.setFruit("banana");
        bananaFruitTransaction.setQuantity(10);
        FruitTransaction appleFruitTransaction = new FruitTransaction();
        appleFruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        appleFruitTransaction.setFruit("apple");
        appleFruitTransaction.setQuantity(5);
        List<String> list = new ArrayList<>();
        list.add("fruit,quantity");
        list.add("b,banana,10");
        list.add("r,apple,5");
        List<FruitTransaction> expected = List.of(
                bananaFruitTransaction, appleFruitTransaction);
        List<FruitTransaction> actual = parser.getFruitTransactionsList(list);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFruitTransactionsList_InvalidData_Ok() {
        List<String> list = new ArrayList<>();
        list.add("r");
        list.add("b,banana");
        list.add("Hello,world");
        list.add("N,apple,20");
        list.add("p,banana,-10");
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = parser.getFruitTransactionsList(list);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getFruitTransactionsList_QuantityIsNotNumber_NotOk() {
        List<String> list = new ArrayList<>();
        list.add("r,apple,ten");
        parser.getFruitTransactionsList(list);
    }
}
