package core.basesyntax.countingoperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.operationswithfile.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CountOperationImplTest {
    private final Map<String, Integer> balance = new HashMap<>();
    private final List<Transaction> transactionList = new ArrayList<>();
    private final CountOperationImpl countOperation = new CountOperationImpl();
    private final Map<String, Integer> expected = new HashMap<>();

    @Before
    public void addToMapAndToTransactionList() {
        balance.put("banana",50);
        balance.put("apple",20);
        Transaction transaction = new Transaction();
        transaction.setOperationType("s");
        transaction.setName("banana");
        transaction.setCount(50);
        Transaction transaction2 = new Transaction();
        transaction2.setOperationType("p");
        transaction2.setName("banana");
        transaction2.setCount(20);
        transactionList.add(transaction);
        transactionList.add(transaction2);
    }

    @Test
    public void getCount_ok() {
        Map<String, Integer> actual = countOperation.getCount(balance, transactionList);
        expected.put("banana", 80);
        expected.put("apple", 20);
        assertEquals(actual,expected);
    }

    @Test
    public void getCount_notOk() {
        Map<String, Integer> actual = countOperation.getCount(balance, transactionList);
        expected.put("banana", 50);
        expected.put("apple", 80);
        assertNotEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void getCount_withIllegalData() {
        transactionList.get(1).setCount(180);
        Map<String, Integer> actual = countOperation.getCount(balance, transactionList);

    }

}
