package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static OperationHandler supply;

    @BeforeAll
    static void beforeAll() {
        supply = new SupplyOperation();
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    void supply_ok() {
        supply.handle(Storage.storage,
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30));
        assertEquals(30,Storage.storage.get("apple"));
    }
}
