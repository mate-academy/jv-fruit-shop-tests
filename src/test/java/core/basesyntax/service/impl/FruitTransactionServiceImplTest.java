package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private FruitTransactionServiceImpl transactionService;

    @Before
    public void setUp() {
        transactionService = new FruitTransactionServiceImpl();
    }

    @Test
    public void processTransactions_Ok() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("banana", 152);
        expectedMap.put("apple", 90);

        transactionService.processTransactions(prepareTestTransactionList());
        Assert.assertEquals(expectedMap, Storage.fruitMap);
    }

    @After
    public void clearStorage() {
        Storage.fruitMap.clear();
    }

    private List<FruitTransaction> prepareTestTransactionList() {
        final List<FruitTransaction> transactions = new ArrayList<>();

        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit(new Fruit("banana"));
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setQuantity(20);
        transactions.add(firstTransaction);

        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setFruit(new Fruit("apple"));
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setQuantity(100);
        transactions.add(secondTransaction);

        FruitTransaction thirdTransaction = new FruitTransaction();
        thirdTransaction.setFruit(new Fruit("banana"));
        thirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdTransaction.setQuantity(100);
        transactions.add(thirdTransaction);

        FruitTransaction fourthTransaction = new FruitTransaction();
        fourthTransaction.setFruit(new Fruit("banana"));
        fourthTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fourthTransaction.setQuantity(13);
        transactions.add(fourthTransaction);

        FruitTransaction fifthTransaction = new FruitTransaction();
        fifthTransaction.setFruit(new Fruit("apple"));
        fifthTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fifthTransaction.setQuantity(10);
        transactions.add(fifthTransaction);

        FruitTransaction sixthTransaction = new FruitTransaction();
        sixthTransaction.setFruit(new Fruit("apple"));
        sixthTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        sixthTransaction.setQuantity(20);
        transactions.add(sixthTransaction);

        FruitTransaction seventhTransaction = new FruitTransaction();
        seventhTransaction.setFruit(new Fruit("banana"));
        seventhTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        seventhTransaction.setQuantity(5);
        transactions.add(seventhTransaction);

        FruitTransaction eighthTransaction = new FruitTransaction();
        eighthTransaction.setFruit(new Fruit("banana"));
        eighthTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        eighthTransaction.setQuantity(50);
        transactions.add(eighthTransaction);

        return transactions;
    }
}
