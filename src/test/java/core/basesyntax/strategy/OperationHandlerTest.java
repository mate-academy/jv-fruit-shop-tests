package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private OperationHandler operationHandler;

    @Test
    void processTransaction_positiveBalance_ok() {
        operationHandler = new BalanceHandlerImpl();
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 30);
        expected.put("banana", 10);
        operationHandler.processTransaction(new FruitTransaction(Operation.BALANCE, "apple", 30));
        operationHandler.processTransaction(new FruitTransaction(Operation.BALANCE, "banana", 10));
        Map<String, Integer> actual = Storage.getStorage();
        Assertions.assertEquals(expected.size(), actual.size());
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assertions.assertTrue(actual.containsKey(entry.getKey()));
            Assertions.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @Test
    void processTransaction_negativeBalance_notOk() {
        operationHandler = new BalanceHandlerImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> operationHandler.processTransaction(
                        new FruitTransaction(Operation.BALANCE, "apple", -30)));
    }

    @Test
    void processTransaction_transactionBalanceNull_ok() {
        operationHandler = new BalanceHandlerImpl();
        operationHandler.processTransaction(null);
        Assertions.assertTrue(Storage.getStorage().isEmpty());
    }

    @Test
    void processTransaction_transactionPurchaseNull_ok() {
        operationHandler = new PurchaseHandlerImpl();
        operationHandler.processTransaction(null);
        Assertions.assertTrue(Storage.getStorage().isEmpty());
    }

    @Test
    void processTransaction_positivePurchase_ok() {
        operationHandler = new PurchaseHandlerImpl();
        Storage.getStorage().put("apple", 30);
        Storage.getStorage().put("banana", 10);
        operationHandler.processTransaction(new FruitTransaction(Operation.PURCHASE, "apple", 10));
        operationHandler.processTransaction(new FruitTransaction(Operation.PURCHASE, "banana", 2));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 20);
        expected.put("banana", 8);
        Map<String, Integer> actual = Storage.getStorage();
        Assertions.assertEquals(expected.size(), actual.size());
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assertions.assertTrue(actual.containsKey(entry.getKey()));
            Assertions.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @Test
    void processTransaction_negativePurchase_ok() {
        operationHandler = new PurchaseHandlerImpl();
        Storage.getStorage().put("apple", 30);
        Storage.getStorage().put("banana", 10);
        operationHandler.processTransaction(new FruitTransaction(Operation.PURCHASE, "apple", -10));
        operationHandler.processTransaction(new FruitTransaction(Operation.PURCHASE, "banana", -2));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 20);
        expected.put("banana", 8);
        Map<String, Integer> actual = Storage.getStorage();
        Assertions.assertEquals(expected.size(), actual.size());
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assertions.assertTrue(actual.containsKey(entry.getKey()));
            Assertions.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @Test
    void processTransaction_transactionSupplyNull_ok() {
        operationHandler = new SupplyHandlerImpl();
        operationHandler.processTransaction(null);
        Assertions.assertTrue(Storage.getStorage().isEmpty());
    }

    @Test
    void processTransaction_positiveSupply_ok() {
        operationHandler = new SupplyHandlerImpl();
        Storage.getStorage().put("apple", 3);
        Storage.getStorage().put("banana", 1);
        operationHandler.processTransaction(new FruitTransaction(Operation.SUPPLY, "apple", 5));
        operationHandler.processTransaction(new FruitTransaction(Operation.SUPPLY, "banana", 5));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 8);
        expected.put("banana", 6);
        Map<String, Integer> actual = Storage.getStorage();
        Assertions.assertEquals(expected.size(), actual.size());
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assertions.assertTrue(actual.containsKey(entry.getKey()));
            Assertions.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @Test
    void processTransaction_negativeSupply_ok() {
        operationHandler = new SupplyHandlerImpl();
        Storage.getStorage().put("apple", 13);
        Storage.getStorage().put("banana", 8);
        operationHandler.processTransaction(new FruitTransaction(Operation.SUPPLY, "apple", -10));
        operationHandler.processTransaction(new FruitTransaction(Operation.SUPPLY, "banana", -2));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 3);
        expected.put("banana", 6);
        Map<String, Integer> actual = Storage.getStorage();
        Assertions.assertEquals(expected.size(), actual.size());
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assertions.assertTrue(actual.containsKey(entry.getKey()));
            Assertions.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @Test
    void processTransaction_transactionReturnNull_ok() {
        operationHandler = new ReturnHandlerImpl();
        operationHandler.processTransaction(null);
        Assertions.assertTrue(Storage.getStorage().isEmpty());
    }

    @Test
    void processTransaction_positiveReturn_ok() {
        operationHandler = new ReturnHandlerImpl();
        Storage.getStorage().put("apple", 3);
        Storage.getStorage().put("banana", 1);
        operationHandler.processTransaction(new FruitTransaction(Operation.RETURN, "apple", 15));
        operationHandler.processTransaction(new FruitTransaction(Operation.RETURN, "banana", 15));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 18);
        expected.put("banana", 16);
        Map<String, Integer> actual = Storage.getStorage();
        Assertions.assertEquals(expected.size(), actual.size());
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assertions.assertTrue(actual.containsKey(entry.getKey()));
            Assertions.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @Test
    void processTransaction_negativeReturn_ok() {
        operationHandler = new ReturnHandlerImpl();
        Storage.getStorage().put("apple", 13);
        Storage.getStorage().put("banana", 8);
        operationHandler.processTransaction(new FruitTransaction(Operation.RETURN, "apple", -10));
        operationHandler.processTransaction(new FruitTransaction(Operation.RETURN, "banana", -2));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 23);
        expected.put("banana", 10);
        Map<String, Integer> actual = Storage.getStorage();
        Assertions.assertEquals(expected.size(), actual.size());
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assertions.assertTrue(actual.containsKey(entry.getKey()));
            Assertions.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @AfterEach
    void afterEachTest() {
        Storage.getStorage().clear();
    }
}
