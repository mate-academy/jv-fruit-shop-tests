package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    private static final FruitTransaction defaultTransaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
    private static final FruitTransaction additionalTransaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 5);
    private static final FruitTransaction supplyTransaction =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5);
    private static final FruitTransaction purchaseTransaction =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 3);
    private static final FruitTransaction returnTransaction =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 1);
    private static final int DEFAULT_FRUIT_QUANTITY = 100;

    private static StorageDao storageDao;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void beforeEach() {
        Storage.clearFruits();
    }

    @Test
    public void balanceOperation_applyOperation_NullStorageDao_NotOk() {
        assertThrows(RuntimeException.class, () -> new BalanceOperationHandler(null));
    }

    @Test
    public void balanceOperation_applyOperation_NullTransaction_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            OperationHandler handler = new BalanceOperationHandler(storageDao);
            handler.applyOperation(null);
        });
    }

    @Test
    public void balanceOperation_applyOperation_Complete_Ok() {
        assertNull(Storage.getFruitQuantity(defaultTransaction.getFruit()));
        Storage.setFruitQuantity(defaultTransaction.getFruit(), DEFAULT_FRUIT_QUANTITY);
        OperationHandler handler = new BalanceOperationHandler(storageDao);
        handler.applyOperation(defaultTransaction);
        assertEquals(defaultTransaction.getQuantity(),
                Storage.getFruitQuantity(defaultTransaction.getFruit()));
    }

    //

    @Test
    public void returnOperation_applyOperation_NullStorageDao_NotOk() {
        assertThrows(RuntimeException.class, () -> new ReturnOperationHandler(null));
    }

    @Test
    public void returnOperation_applyOperation_NullTransaction_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            OperationHandler handler = new ReturnOperationHandler(storageDao);
            handler.applyOperation(null);
        });
    }

    @Test
    public void returnOperation_applyOperation_Complete_Ok() {
        assertNull(Storage.getFruitQuantity(returnTransaction.getFruit()));
        Storage.setFruitQuantity(returnTransaction.getFruit(), DEFAULT_FRUIT_QUANTITY);
        OperationHandler handler = new ReturnOperationHandler(storageDao);
        handler.applyOperation(returnTransaction);
        Integer expectedQuantity = returnTransaction.getQuantity() + DEFAULT_FRUIT_QUANTITY;
        assertEquals(expectedQuantity, Storage.getFruitQuantity(returnTransaction.getFruit()));
    }

    //

    @Test
    public void supplyOperation_applyOperation_NullStorageDao_NotOk() {
        assertThrows(RuntimeException.class, () -> new SupplyOperationHandler(null));
    }

    @Test
    public void supplyOperation_applyOperation_NullTransaction_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            OperationHandler handler = new SupplyOperationHandler(storageDao);
            handler.applyOperation(null);
        });
    }

    @Test
    public void supplyOperation_applyOperation_Complete_Ok() {
        assertNull(Storage.getFruitQuantity(supplyTransaction.getFruit()));
        Storage.setFruitQuantity(supplyTransaction.getFruit(), DEFAULT_FRUIT_QUANTITY);
        OperationHandler handler = new SupplyOperationHandler(storageDao);
        handler.applyOperation(supplyTransaction);
        Integer expectedQuantity = supplyTransaction.getQuantity() + DEFAULT_FRUIT_QUANTITY;
        assertEquals(expectedQuantity, Storage.getFruitQuantity(supplyTransaction.getFruit()));
    }

    //

    @Test
    public void purchaseOperation_applyOperation_NullStorageDao_NotOk() {
        assertThrows(RuntimeException.class, () -> new PurchaseOperationHandler(null));
    }

    @Test
    public void purchaseOperation_applyOperation_NullTransaction_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            OperationHandler handler = new PurchaseOperationHandler(storageDao);
            handler.applyOperation(null);
        });
    }

    @Test
    public void purchaseOperation_applyOperation_NegativeQuantity_NotOk() {
        assertNull(Storage.getFruitQuantity(purchaseTransaction.getFruit()));
        OperationHandler handler = new PurchaseOperationHandler(storageDao);
        assertThrows(RuntimeException.class, () -> handler.applyOperation(purchaseTransaction));
        Storage.setFruitQuantity(purchaseTransaction.getFruit(), 2);
        assertThrows(RuntimeException.class, () -> handler.applyOperation(purchaseTransaction));
    }

    @Test
    public void purchaseOperation_applyOperation_Complete_Ok() {
        assertNull(Storage.getFruitQuantity(purchaseTransaction.getFruit()));
        Storage.setFruitQuantity(purchaseTransaction.getFruit(), DEFAULT_FRUIT_QUANTITY);
        OperationHandler handler = new PurchaseOperationHandler(storageDao);
        handler.applyOperation(purchaseTransaction);
        Integer expectedQuantity = DEFAULT_FRUIT_QUANTITY - purchaseTransaction.getQuantity();
        assertEquals(expectedQuantity, Storage.getFruitQuantity(purchaseTransaction.getFruit()));
    }
}
