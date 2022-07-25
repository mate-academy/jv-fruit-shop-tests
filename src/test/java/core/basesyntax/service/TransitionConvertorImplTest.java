package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransitionConvertorImplTest {
    @Test
    public void convertNormalListOfStrings_Ok() {
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

        List<Transaction> expectedTransactions = getListTransaction();
        List<Transaction> actualTransactions = new TransitionConvertorImpl().convert(stringList);

        assertEquals(expectedTransactions, actualTransactions,
                "Converted List of Transactions doesn't equal to expected");
    }

    private List<Transaction> getListTransaction() {
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