package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParserImpl transactionParser;
    private List<String> inputList;
    private List<FruitTransaction> expected;

    @Before
    public void setUp() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void getTransactions_ok() {
        inputList = new ArrayList<>();
        inputList.add("b,banana,20");
        inputList.add("b,apple,100");
        inputList.add("s,banana,100");
        inputList.add("p,banana,13");
        inputList.add("r,apple,10");
        inputList.add("p,apple,20");
        inputList.add("p,banana,5");
        inputList.add("s,banana,50");
        FruitTransaction balanceBanana20 = new FruitTransaction();
        balanceBanana20.setOperation(BALANCE);
        balanceBanana20.setFruit("banana");
        balanceBanana20.setQuantity(20);
        FruitTransaction balanceApple100 = new FruitTransaction();
        balanceApple100.setOperation(BALANCE);
        balanceApple100.setFruit("apple");
        balanceApple100.setQuantity(100);
        FruitTransaction supplyBanana100 = new FruitTransaction();
        supplyBanana100.setOperation(SUPPLY);
        supplyBanana100.setFruit("banana");
        supplyBanana100.setQuantity(100);
        FruitTransaction purchaseBanana13 = new FruitTransaction();
        purchaseBanana13.setOperation(PURCHASE);
        purchaseBanana13.setFruit("banana");
        purchaseBanana13.setQuantity(13);
        FruitTransaction returnApple10 = new FruitTransaction();
        returnApple10.setOperation(RETURN);
        returnApple10.setFruit("apple");
        returnApple10.setQuantity(10);
        FruitTransaction purcheseApple20 = new FruitTransaction();
        purcheseApple20.setOperation(PURCHASE);
        purcheseApple20.setFruit("apple");
        purcheseApple20.setQuantity(20);
        FruitTransaction purchaseBanana5 = new FruitTransaction();
        purchaseBanana5.setOperation(PURCHASE);
        purchaseBanana5.setFruit("banana");
        purchaseBanana5.setQuantity(5);
        FruitTransaction supplyBanana50 = new FruitTransaction();
        supplyBanana50.setOperation(SUPPLY);
        supplyBanana50.setFruit("banana");
        supplyBanana50.setQuantity(50);
        expected = new ArrayList<>();
        expected.add(balanceBanana20);
        expected.add(balanceApple100);
        expected.add(supplyBanana100);
        expected.add(purchaseBanana13);
        expected.add(returnApple10);
        expected.add(purcheseApple20);
        expected.add(purchaseBanana5);
        expected.add(supplyBanana50);
        List<FruitTransaction> actual = transactionParser.getTransactions(inputList);
        assertEquals(expected, actual);
    }

    @Test
    public void getTransactions_null_notOk() {
        assertThrows(RuntimeException.class, () -> {
            transactionParser.getTransactions(null);
        });
    }
}
