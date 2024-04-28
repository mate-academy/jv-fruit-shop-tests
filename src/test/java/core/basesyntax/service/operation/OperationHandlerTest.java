package core.basesyntax.service.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.FruitStorageImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private final FruitStorage storage = new FruitStorageImpl();
    private final OperationHandler operationHandlerBalancer = new BalanceOperationHandler();
    private final OperationHandler operationHandlerSupplier = new SupplyOperationHandler();
    private final OperationHandler operationHandlerPurchaser = new PurchaseOperationHandler();
    private final OperationHandler operationHandlerReturner = new ReturnOperationHandler();
    private final FruitTransaction balanceInstance = new FruitTransaction(
            FruitTransaction.Operation.BALANCE,
            "banana",
            100);

    private final FruitTransaction supplyInstance = new FruitTransaction(
            FruitTransaction.Operation.SUPPLY,
            "banana",
            90);

    private final FruitTransaction purchaseInstance = new FruitTransaction(
            FruitTransaction.Operation.PURCHASE,
            "banana",
            50);

    private final FruitTransaction returnInstance = new FruitTransaction(
            FruitTransaction.Operation.RETURN,
            "banana",
            20);
    
    @Test
    void process_Balance_Ok() {
        operationHandlerBalancer.process(balanceInstance);
        Assertions.assertEquals(storage.getQuantity("banana"), 100);
    }

    @Test
    void process_Supply_Ok() {
        storage.add("banana", 100);
        operationHandlerSupplier.process(supplyInstance);
        Assertions.assertEquals(storage.getQuantity("banana"), 190);
    }

    @Test
    void process_Purchase_Ok() {
        storage.add("banana", 100);
        operationHandlerPurchaser.process(purchaseInstance);
        Assertions.assertEquals(storage.getQuantity("banana"), 50);
    }

    @Test
    void process_Return_Ok() {
        storage.add("banana", 100);
        operationHandlerReturner.process(returnInstance);
        Assertions.assertEquals(storage.getQuantity("banana"), 120);
    }
}
