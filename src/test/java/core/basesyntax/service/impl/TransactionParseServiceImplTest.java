package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TransactionParseServiceImplTest {

    private TransactionParseServiceImpl transactionParseService = new TransactionParseServiceImpl();

    @Test
    public void parse_List_Ok() {
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        list.add("s,banana,100");
        list.add("s,apple,30");
        list.add("p,apple,1");
        list.add("r,apple,1");
        List<Transaction> transactions
                = transactionParseService.parser(list);
        Assert.assertEquals(5, transactions.size());
        Assert.assertEquals("banana", transactions.get(0).getProduct());
        Assert.assertEquals(20, transactions.get(0).getQuantity());
        Assert.assertEquals(Transaction.Operation.BALANCE,
                transactions.get(0).getTypeOperation());
        Assert.assertEquals("banana", transactions.get(1).getProduct());
        Assert.assertEquals(100, transactions.get(1).getQuantity());
        Assert.assertEquals(Transaction.Operation.SUPPLY,
                transactions.get(1).getTypeOperation());
        Assert.assertEquals("apple", transactions.get(2).getProduct());
        Assert.assertEquals(30, transactions.get(2).getQuantity());
        Assert.assertEquals(Transaction.Operation.SUPPLY,
                transactions.get(2).getTypeOperation());
        Assert.assertEquals("apple", transactions.get(3).getProduct());
        Assert.assertEquals(1, transactions.get(3).getQuantity());
        Assert.assertEquals(Transaction.Operation.PURCHASE,
                transactions.get(3).getTypeOperation());
        Assert.assertEquals("apple", transactions.get(4).getProduct());
        Assert.assertEquals(1, transactions.get(4).getQuantity());
        Assert.assertEquals(Transaction.Operation.RETURN,
                transactions.get(4).getTypeOperation());
    }

    @Test(expected = RuntimeException.class)
    public void parse_NullList_NotOk() {
        transactionParseService.parser(null);
    }
}
