package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddOperationHandlerTest {
    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void testAddOperationHandler_whenFruitNotExists() {
        AddOperationHandler addHandler = new AddOperationHandler();
        addHandler.apply(new HashMap<>(), "apple", 10);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void testAddOperationHandler_whenFruitExists() {
        Storage.inventory.put("apple", 5);
        AddOperationHandler addHandler = new AddOperationHandler();
        addHandler.apply(new HashMap<>(), "apple", 7);
        assertEquals(12, Storage.inventory.get("apple"));
    }

    @Test
    void testSubtractOperationHandler_whenFruitExists() {
        Storage.inventory.put("apple", 20);
        AddOperationHandler.SubtractOperationHandler subtractHandler = new
                AddOperationHandler.SubtractOperationHandler();
        subtractHandler.apply(new HashMap<>(), "apple", 5);
        assertEquals(15, Storage.inventory.get("apple"));
    }

    @Test
    void testSubtractOperationHandler_whenFruitNotExists() {
        AddOperationHandler.SubtractOperationHandler subtractHandler = new
                AddOperationHandler.SubtractOperationHandler();
        subtractHandler.apply(new HashMap<>(), "banana", 5);
        assertEquals(-5, Storage.inventory.get("banana"));
    }

    @Test
    void testSupplyOperationHandler_whenFruitNotExists() {
        Map<String, Integer> localInventory = new HashMap<>();
        AddOperationHandler.SupplyOperationHandler supplyHandler = new
                AddOperationHandler.SupplyOperationHandler();
        supplyHandler.apply(localInventory, "orange", 8);
        assertEquals(8, localInventory.get("orange"));
    }

    @Test
    void testSupplyOperationHandler_whenFruitExists() {
        Map<String, Integer> localInventory = new HashMap<>();
        localInventory.put("orange", 2);
        AddOperationHandler.SupplyOperationHandler supplyHandler = new
                AddOperationHandler.SupplyOperationHandler();
        supplyHandler.apply(localInventory, "orange", 5);
        assertEquals(7, localInventory.get("orange"));
    }

    @Test
    void testReturnOperationHandler_whenFruitNotExists() {
        AddOperationHandler.ReturnOperationHandler returnHandler = new
                AddOperationHandler.ReturnOperationHandler();
        returnHandler.apply(new HashMap<>(), "kiwi", 3);
        assertEquals(3, Storage.inventory.get("kiwi"));
    }

    @Test
    void testReturnOperationHandler_whenFruitExists() {
        Storage.inventory.put("kiwi", 4);
        AddOperationHandler.ReturnOperationHandler returnHandler = new
                AddOperationHandler.ReturnOperationHandler();
        returnHandler.apply(new HashMap<>(), "kiwi", 2);
        assertEquals(6, Storage.inventory.get("kiwi"));
    }
}
