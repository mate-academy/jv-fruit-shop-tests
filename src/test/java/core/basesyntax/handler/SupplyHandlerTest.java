package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {

    private SupplyHandler supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyHandler();
        Storage.clearStorage();
    }

    @Test
    void apply_shouldIncreaseFruitQuantityOnSupply() {
        String fruit = "apple";
        Storage.modifyFruitStorage(fruit, 100);

        int supplyQuantity = 50;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, supplyQuantity);

        supplyOperation.handle(transaction);

        assertEquals(150, Storage.getFruitQuantity(fruit));
    }

    @Test
    void apply_shouldNotChangeStorageIfSupplyQuantityIsZero() {
        String fruit = "banana";
        Storage.modifyFruitStorage(fruit, 50);

        int supplyQuantity = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, supplyQuantity);

        supplyOperation.handle(transaction);

        assertEquals(50, Storage.getFruitQuantity(fruit));
    }

    @Test
    void apply_shouldIncreaseFruitQuantityForMultipleSupplies() {
        String fruit = "orange";
        Storage.modifyFruitStorage(fruit, 30);

        int firstSupplyQuantity = 20;
        FruitTransaction firstTransaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, firstSupplyQuantity);

        int secondSupplyQuantity = 10;
        FruitTransaction secondTransaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, secondSupplyQuantity);

        supplyOperation.handle(firstTransaction);
        supplyOperation.handle(secondTransaction);

        assertEquals(60, Storage.getFruitQuantity(fruit));
    }
}
