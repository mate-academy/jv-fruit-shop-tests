package basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import basesyntax.model.FruitTransaction;
import basesyntax.model.Operation;
import basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private OperationHandler supply;

    @BeforeEach
    void setUp() {
        supply = new SupplyOperation();
        Storage.clear();
    }

    @Test
    void handle_oneValidSupply_Ok() {
        Storage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 25);
        supply.handle(transaction);
        assertEquals(30, Storage.get("apple"));
    }

    @Test
    void handle_moreThenOneValidSupply_Ok() {
        Storage.put("kiwi", 15);
        FruitTransaction transaction1 = new FruitTransaction(Operation.SUPPLY, "kiwi", 5);
        supply.handle(transaction1);
        assertEquals(20, Storage.get("kiwi"));

        FruitTransaction transaction2 = new FruitTransaction(Operation.SUPPLY, "kiwi", 3);
        supply.handle(transaction2);
        assertEquals(23, Storage.get("kiwi"));
    }

    @Test
    void handle_validSupplyToZeroStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 50);
        supply.handle(transaction);
        assertEquals(50, Storage.get("banana"));
    }
}
