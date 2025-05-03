package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void updateFruitStorage_ValidOperationSupply_Ok() {
        FruitStorage.fruitStorage.put("apple", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                50
        );
        supplyOperationHandler.updateFruitStorage(fruitTransaction, fruitStorageDao);
        int actual = FruitStorage.fruitStorage.get("apple");
        assertEquals(150, actual, "Quantity is expected to be 150");
    }
}
