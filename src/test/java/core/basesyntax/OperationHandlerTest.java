package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {

    private OperationHandlerImpl handler;

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
        handler = new OperationHandlerImpl();
    }

    private FruitTransaction makeTx(String fruit, int quantity, FruitTransaction.Operation op) {
        FruitTransaction tx = new FruitTransaction();
        tx.setFruit(fruit);
        tx.setQuantity(quantity);
        tx.setOperation(op);
        return tx;
    }

    @Test
    void handle_balance_shouldSetExactQuantity() {
        handler.handle(makeTx("banana", 100, FruitTransaction.Operation.BALANCE));
        assertEquals(100, Storage.fruitStorage.get("banana"));
    }

    @Test
    void handle_supply_shouldIncreaseQuantity() {
        Storage.fruitStorage.put("apple", 30);
        handler.handle(makeTx("apple", 20, FruitTransaction.Operation.SUPPLY));
        assertEquals(50, Storage.fruitStorage.get("apple"));
    }

    @Test
    void handle_return_shouldIncreaseQuantity() {
        Storage.fruitStorage.put("orange", 10);
        handler.handle(makeTx("orange", 15, FruitTransaction.Operation.RETURN));
        assertEquals(25, Storage.fruitStorage.get("orange"));
    }

    @Test
    void handle_purchase_shouldDecreaseQuantity() {
        Storage.fruitStorage.put("mango", 40);
        handler.handle(makeTx("mango", 15, FruitTransaction.Operation.PURCHASE));
        assertEquals(25, Storage.fruitStorage.get("mango"));
    }

    @Test
    void handle_purchase_shouldThrowException_whenNotEnoughStock() {
        Storage.fruitStorage.put("kiwi", 5);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                handler.handle(makeTx("kiwi", 10, FruitTransaction.Operation.PURCHASE))
        );
        assertEquals("Not enough stock for purchase of kiwi", exception.getMessage());
    }

    @Test
    void handle_balance_shouldOverrideExistingQuantity() {
        Storage.fruitStorage.put("banana", 200);
        handler.handle(makeTx("banana", 50, FruitTransaction.Operation.BALANCE));
        assertEquals(50, Storage.fruitStorage.get("banana"));
    }

    @Test
    void handle_supply_shouldTreatMissingFruitAsZero() {
        assertEquals(null, Storage.fruitStorage.get("blueberry"));
        handler.handle(makeTx("blueberry", 30, FruitTransaction.Operation.SUPPLY));
        assertEquals(30, Storage.fruitStorage.get("blueberry"));
    }

    @Test
    void handle_return_shouldTreatMissingFruitAsZero() {
        handler.handle(makeTx("grape", 12, FruitTransaction.Operation.RETURN));
        assertEquals(12, Storage.fruitStorage.get("grape"));
    }

    @Test
    void handle_purchase_shouldWorkIfExactAmountAvailable() {
        Storage.fruitStorage.put("pineapple", 20);
        handler.handle(makeTx("pineapple", 20, FruitTransaction.Operation.PURCHASE));
        assertEquals(0, Storage.fruitStorage.get("pineapple"));
    }

}
