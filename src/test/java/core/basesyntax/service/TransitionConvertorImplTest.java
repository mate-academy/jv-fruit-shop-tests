package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransitionConvertorImplTest {
    private final TransitionConvertor transitionConvertor = new TransitionConvertorImpl();

    @Test
    public void convert_Ok() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,80");
        stringList.add("b,apple,160");
        stringList.add("s,banana,40");
        stringList.add("s,apple,80");
        stringList.add("p,banana,20");
        stringList.add("p,apple,40");
        stringList.add("r,banana,10");
        stringList.add("r,apple,20");
        List<Transaction> expectedTransactions = getTransactionList();
        List<Transaction> actualTransactions = transitionConvertor.convert(stringList);
        assertEquals("Converted List of Transactions doesn't equal to expected",
                expectedTransactions, actualTransactions);
    }

    @Test
    public void convert_WithoutTransactions_Ok() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        List<Transaction> expectedTransactions = new ArrayList<>();
        List<Transaction> actualTransactions = transitionConvertor.convert(stringList);
        assertEquals("Converted List of Transactions doesn't equal to expected",
                expectedTransactions, actualTransactions);
    }

    @Test(expected = NullPointerException.class)
    public void convert_NullStringList_NotOk() {
        transitionConvertor.convert(null);
        fail("Convert method of NULL must throw NullPointerException exception");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void convert_EmptyStringList_NotOk() {
        List<String> stringList = new ArrayList<>();
        transitionConvertor.convert(stringList);
        fail("Convert method of empty String must throw IndexOutOfBoundsException exception");
    }

    private List<Transaction> getTransactionList() {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(getTransaction(Operation.BALANCE, banana, 80));
        transactionList.add(getTransaction(Operation.BALANCE, apple, 160));
        transactionList.add(getTransaction(Operation.SUPPLY, banana, 40));
        transactionList.add(getTransaction(Operation.SUPPLY, apple, 80));
        transactionList.add(getTransaction(Operation.PURCHASE, banana, 20));
        transactionList.add(getTransaction(Operation.PURCHASE, apple, 40));
        transactionList.add(getTransaction(Operation.RETURN, banana, 10));
        transactionList.add(getTransaction(Operation.RETURN, apple, 20));
        return transactionList;
    }

    private Transaction getTransaction(Operation operation, Fruit fruit, Integer quantity) {
        Transaction transaction = new Transaction();
        transaction.setOperation(operation);
        transaction.setProduct(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
