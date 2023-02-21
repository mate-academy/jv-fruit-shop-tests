package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TransactionParserImplTest {
    private TransactionParserImpl transactionParser;
    private List<String> inputList;
    private List<FruitTransaction> expected;

    @Before
    public void setUp() {
        transactionParser = new TransactionParserImpl();
        inputList = new ArrayList<>();
        inputList.add("b,banana,20");
        inputList.add("b,apple,100");
        inputList.add("s,banana,100");
        inputList.add("p,banana,13");
        inputList.add("r,apple,10");
        inputList.add("p,apple,20");
        inputList.add("p,banana,5");
        inputList.add("s,banana,50");
        FruitTransaction bBanana20 = new FruitTransaction();
        bBanana20.setOperation(BALANCE);
        bBanana20.setFruit("banana");
        bBanana20.setQuantity(20);
        FruitTransaction bApple100 = new FruitTransaction();
        bApple100.setOperation(BALANCE);
        bApple100.setFruit("apple");
        bApple100.setQuantity(100);
        FruitTransaction sBanana100 = new FruitTransaction();
        sBanana100.setOperation(SUPPLY);
        sBanana100.setFruit("banana");
        sBanana100.setQuantity(100);
        FruitTransaction pBanana13 = new FruitTransaction();
        pBanana13.setOperation(PURCHASE);
        pBanana13.setFruit("banana");
        pBanana13.setQuantity(13);
        FruitTransaction rApple10 = new FruitTransaction();
        rApple10.setOperation(RETURN);
        rApple10.setFruit("apple");
        rApple10.setQuantity(10);
        FruitTransaction pApple20 = new FruitTransaction();
        pApple20.setOperation(PURCHASE);
        pApple20.setFruit("apple");
        pApple20.setQuantity(20);
        FruitTransaction pBanana5 = new FruitTransaction();
        pBanana5.setOperation(PURCHASE);
        pBanana5.setFruit("banana");
        pBanana5.setQuantity(5);
        FruitTransaction sBanana50 = new FruitTransaction();
        sBanana50.setOperation(SUPPLY);
        sBanana50.setFruit("banana");
        sBanana50.setQuantity(50);
        expected = new ArrayList<>();
        expected.add(bBanana20);
        expected.add(bApple100);
        expected.add(sBanana100);
        expected.add(pBanana13);
        expected.add(rApple10);
        expected.add(pApple20);
        expected.add(pBanana5);
        expected.add(sBanana50);
    }

    @Test
    public void getTransactions_ok() {
        List<FruitTransaction> actual = transactionParser.getTransactions(inputList);
        assertEquals(expected, actual);
    }

    @Test
    public void getTransactionsNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            transactionParser.getTransactions(null);
        });
    }
}
