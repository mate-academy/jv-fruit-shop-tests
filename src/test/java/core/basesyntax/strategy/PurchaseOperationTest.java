package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.Instruction;
import core.basesyntax.strategy.impl.PurchaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private Operation purchase = new PurchaseOperation(storageDao);
    private Instruction purchaseOk = new Instruction(FruitOperation.PURCHASE,
            "apple", 50);
    private Instruction purchaseNotExist = new Instruction(FruitOperation.PURCHASE,
            "banana", 50);
    private Instruction purchaseNegativeFruitQuantity = new Instruction(FruitOperation.PURCHASE,
            "apple", 101);
    private Instruction purchaseNegativeValue = new Instruction(FruitOperation.PURCHASE,
            "apple", -1);

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 100);
    }

    @Test
    void proceed_PurchaseOperation_ok() {
        purchase.proceed(purchaseOk);
        int expected = 50;
        int current = Storage.fruits.get(purchaseOk.getFruitName());
        assertEquals(expected, current,
                "Wrong work of Purchase operation");
    }

    @Test
    void proceed_PurchaseOperationNotExist_noOk() {
        assertThrows(OperationException.class, () -> purchase.proceed(purchaseNotExist),
                "Purchase operation should work only with existed products");
    }

    @Test
    void proceed_PurchaseOperationNegativeFruitQuantity_noOk() {
        assertThrows(OperationException.class,
                () -> purchase.proceed(purchaseNegativeFruitQuantity),
                "Purchase operation shouldn't make negative quantity of fruits");
    }

    @Test
    void proceed_PurchaseOperationNegativeQuantity_noOk() {
        assertThrows(OperationException.class, () -> purchase.proceed(purchaseNegativeValue),
                "Purchase operation shouldn't work with negative value");
    }
}
