package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionParserImplTest {
    private final TransactionParser transactionParser = new TransactionParserImpl();
    private List<String> stringList = new ArrayList<>();

    @Test
    public void parseTransactionList_Ok() {
        stringList.add("b,banana,20");
        stringList.add("b,apple,100");
        stringList.add("s,banana,100");
        stringList.add("p,banana,13");
        stringList.add("r,apple,10");
        stringList.add("p,apple,20");
        stringList.add("p,banana,5");
        stringList.add("s,banana,50");
        final List<Transaction> actual = transactionParser.parseTransactionList(stringList);
        List<Transaction> expectedList = new ArrayList<>();
        expectedList.add(new Transaction("banana", 20));
        expectedList.get(0).setOperation(Transaction.Operation.BALANCE);
        expectedList.add(new Transaction("apple", 100));
        expectedList.get(1).setOperation(Transaction.Operation.BALANCE);
        expectedList.add(new Transaction("banana", 100));
        expectedList.get(2).setOperation(Transaction.Operation.SUPPLY);
        expectedList.add(new Transaction("banana", 13));
        expectedList.get(3).setOperation(Transaction.Operation.PURCHASE);
        expectedList.add(new Transaction("apple", 10));
        expectedList.get(4).setOperation(Transaction.Operation.RETURN);
        expectedList.add(new Transaction("apple", 20));
        expectedList.get(5).setOperation(Transaction.Operation.PURCHASE);
        expectedList.add(new Transaction("banana", 5));
        expectedList.get(6).setOperation(Transaction.Operation.PURCHASE);
        expectedList.add(new Transaction("banana", 50));
        expectedList.get(7).setOperation(Transaction.Operation.SUPPLY);
        assertEquals(expectedList.get(2).getOperation(), actual.get(2).getOperation());
    }

    @Test(expected = RuntimeException.class)
    public void parse_InvalidStoreOperationTransaction_NotOk() {
        stringList.add("type,fruit,quantity");
        stringList.add("q,banana,20");
        transactionParser.parseTransactionList(stringList);
    }
}
