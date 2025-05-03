package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class StorageTest {
    private final Storage storage = new Storage();
    private final List<FruitTransaction> fruitTransactionList = new ArrayList<>();

    @Test
    public void getMapFromList_Ok() {
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getFruitOperation("b"),
                "banana",20));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getFruitOperation("b"),
                "apple",100));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getFruitOperation("s"),
                "banana",100));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getFruitOperation("p"),
                "banana",13));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getFruitOperation("r"),
                "apple",10));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getFruitOperation("p"),
                "apple",20));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getFruitOperation("p"),
                "banana",5));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getFruitOperation("s"),
                "banana",50));
        Map<String, Integer> actual = storage.putFruitInStorage(fruitTransactionList);
        Map<String, Integer> expected = new HashMap<>();
        {
            expected.put("banana", 152);
            expected.put("apple", 90);
        }
        assertEquals(expected,actual);
    }

    @Test
    public void fruitTransactionListIsNull_NotOk() {
        Map<String, Integer> actual = storage.putFruitInStorage(fruitTransactionList);
        assertTrue(actual.isEmpty());
    }
}
