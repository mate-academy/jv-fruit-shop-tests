package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Map<String, Integer> initialFruits;
    private List<FruitTransaction> initialTransactions;

    @BeforeEach
    void setup() {
        initialFruits = new HashMap<>();
        initialFruits.put("banana", 20);

        initialTransactions = new ArrayList<>();
        initialTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 5));
    }

    @Test
    void testGetTransactions() {
        Storage.setTransactions(initialTransactions);
        assertEquals(initialTransactions, Storage.getTransactions());
    }

    @Test
    void testSetTransactions() {
        Storage.setTransactions(initialTransactions);
        List<FruitTransaction> newTransactions = new ArrayList<>();
        newTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10));
        Storage.setTransactions(newTransactions);
        assertEquals(newTransactions, Storage.getTransactions());
    }

    @Test
    void testGetFruits() {
        Storage.setFruits(initialFruits);
        assertEquals(initialFruits, Storage.getFruits());
    }

    @Test
    void testSetFruits() {
        Storage.setFruits(initialFruits);
        Map<String, Integer> newFruits = new HashMap<>();
        newFruits.put("apple", 15);
        Storage.setFruits(newFruits);
        assertEquals(newFruits, Storage.getFruits());
    }
}
