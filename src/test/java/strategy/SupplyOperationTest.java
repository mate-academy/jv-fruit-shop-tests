package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {

    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() throws Exception {
        supplyOperation = new SupplyOperation();

        var field = Storage.class.getDeclaredField("fruitStorage");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        var map = (java.util.Map<String, Integer>) field.get(null);
        map.clear();
    }

    @Test
    void apply_increasesQuantityCorrectly() {
        Storage.setQuantity("banana", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 3
        );

        supplyOperation.apply(transaction);

        assertEquals(8, Storage.getQuantity("banana"));
    }

    @Test
    void apply_addsNewFruitIfNotPresent() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 4
        );

        supplyOperation.apply(transaction);

        assertEquals(4, Storage.getQuantity("apple"));
    }
}
