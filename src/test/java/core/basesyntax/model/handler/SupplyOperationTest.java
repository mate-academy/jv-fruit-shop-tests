package core.basesyntax.model.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static SupplyOperation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @AfterEach
    void afterEach() {
        Storage.getStorage().clear();
    }

    @Test
    void supplyIsVaLid_Ok() {
        Storage.getStorage().put("banana", 100);
        supplyOperation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 57));
        assertEquals(Integer.valueOf(157), Storage.getStorage().get("banana"));
    }

    @Test
    void supplyHandler_addMultipleFruits_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "orange",
                12);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                28);
        supplyOperation.handle(transaction1);
        supplyOperation.handle(transaction2);
        Integer actualAppleQuantity = Storage.getStorage()
                .getOrDefault("orange", 0);
        Integer actualBananaQuantity = Storage.getStorage()
                .getOrDefault("banana", 0);
        assertEquals(Integer.valueOf(12), actualAppleQuantity);
        assertEquals(Integer.valueOf(28), actualBananaQuantity);
    }

    @Test
    void supplyHandler_addNegativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction transaction = new FruitTransaction(
                    FruitTransaction.Operation.SUPPLY,
                    "banana",
                    -23);
            supplyOperation.handle(transaction);
        });
    }
}
