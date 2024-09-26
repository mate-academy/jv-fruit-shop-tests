package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl();
    }

    @Test
    void processBalanceTransaction_OK() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        shopService.process(transactions);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("apple", 100);
        assertEquals(expectedStorage, shopService.getStorage());
    }

    @Test
    void processSupplyTransaction_Ok() {
        Map<String, Integer> initialStorage = new HashMap<>();
        initialStorage.put("apple", 50);
        shopService.setStorage(initialStorage);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.SUPPLY, "apple", 30));
        shopService.process(transactions);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("apple", 80);
        assertEquals(expectedStorage, shopService.getStorage());
    }

    @Test
    void processPurchaseTransaction_Ok() {
        Map<String, Integer> initialStorage = new HashMap<>();
        initialStorage.put("apple", 100);
        shopService.setStorage(initialStorage);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.PURCHASE, "apple", 20));
        shopService.process(transactions);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("apple", 80);
        assertEquals(expectedStorage, shopService.getStorage());
    }

    @Test
    void processReturnTransaction_Ok() {
        Map<String, Integer> initialStorage = new HashMap<>();
        initialStorage.put("apple", 50);
        shopService.setStorage(initialStorage);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.RETURN, "apple", 10));
        shopService.process(transactions);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("apple", 60);
        assertEquals(expectedStorage, shopService.getStorage());
    }

    @Test
    void processEmptyTransactionList_Ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        shopService.process(transactions);
        Map<String, Integer> expectedStorage = new HashMap<>();
        assertEquals(expectedStorage, shopService.getStorage());
    }

    @Test
    void negativeQuantity_NotOK() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.SUPPLY, "apple", -10));
        assertThrows(RuntimeException.class, () ->
                shopService.process(transactions));
    }

    @Test
    void getStorage_Ok() {
        Map<String, Integer> initialStorage = new HashMap<>();
        initialStorage.put("apple", 50);
        shopService.setStorage(initialStorage);
        assertEquals(initialStorage, shopService.getStorage());
    }

    @Test
    void setStorage_Ok() {
        Map<String, Integer> newStorage = new HashMap<>();
        newStorage.put("banana", 20);
        shopService.setStorage(newStorage);
        assertEquals(newStorage, shopService.getStorage());
    }
}
