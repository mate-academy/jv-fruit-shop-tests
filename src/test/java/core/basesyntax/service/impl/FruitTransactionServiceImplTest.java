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

        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setFruit(new Fruit("banana"));
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setQuantity(20);
        transactions.add(transaction1);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setFruit(new Fruit("apple"));
        transaction2.setOperation(FruitTransaction.Operation.BALANCE);
        transaction2.setQuantity(100);
        transactions.add(transaction2);

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setFruit(new Fruit("banana"));
        transaction3.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction3.setQuantity(100);
        transactions.add(transaction3);

        FruitTransaction transaction4 = new FruitTransaction();
        transaction4.setFruit(new Fruit("banana"));
        transaction4.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction4.setQuantity(13);
        transactions.add(transaction4);

        FruitTransaction transaction5 = new FruitTransaction();
        transaction5.setFruit(new Fruit("apple"));
        transaction5.setOperation(FruitTransaction.Operation.RETURN);
        transaction5.setQuantity(10);
        transactions.add(transaction5);

        FruitTransaction transaction6 = new FruitTransaction();
        transaction6.setFruit(new Fruit("apple"));
        transaction6.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction6.setQuantity(20);
        transactions.add(transaction6);

        FruitTransaction transaction7 = new FruitTransaction();
        transaction7.setFruit(new Fruit("banana"));
        transaction7.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction7.setQuantity(5);
        transactions.add(transaction7);

        FruitTransaction transaction8 = new FruitTransaction();
        transaction8.setFruit(new Fruit("banana"));
        transaction8.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction8.setQuantity(50);
        transactions.add(transaction8);

        return transactions;
    }
}
