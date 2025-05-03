package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.Instruction;
import core.basesyntax.strategy.impl.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private Operation supply = new SupplyOperation(storageDao);
    private Instruction supplyOk = new Instruction(FruitOperation.SUPPLY,
            "apple", 50);
    private Instruction supplyNotExist = new Instruction(FruitOperation.SUPPLY,
            "banana", 50);
    private Instruction supplyNegativeValue = new Instruction(FruitOperation.SUPPLY,
            "apple", -1);

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 100);
    }

    @Test
    void proceed_PurchaseOperation_ok() {
        supply.proceed(supplyOk);
        int expected = 150;
        int current = Storage.fruits.get(supplyOk.getFruitName());
        assertEquals(expected, current,
                "Wrong work of Supply operation");
    }

    @Test
    void proceed_PurchaseOperationNotExist_noOk() {
        assertThrows(OperationException.class, () -> supply.proceed(supplyNotExist),
                "Supply operation should work only with existed products");
    }

    @Test
    void proceed_PurchaseOperationNegativeQuantity_noOk() {
        assertThrows(OperationException.class, () -> supply.proceed(supplyNegativeValue),
                "Supply operation shouldn't work with negative value");
    }
}
