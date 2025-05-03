package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TransactionParserImplTest {
    @Test
    public void getDataFromLine_isRightListOfFruitTransaction_Ok() {
        List<String> listData = new ArrayList<>();
        listData.add("b,banana,20");
        listData.add("b,apple,30");
        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> fruitTransactionList = transactionParser.getDataFromLine(listData);
        Assert.assertEquals("banana", fruitTransactionList.get(0).getFruit().getName());
        Assert.assertEquals("apple", fruitTransactionList.get(1).getFruit().getName());
        Assert.assertEquals(20, fruitTransactionList.get(0).getQuantity());
        Assert.assertEquals(30, fruitTransactionList.get(1).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactionList.get(0)
                .getOperation());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactionList.get(1)
                .getOperation());
    }
}
