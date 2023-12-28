package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private FruitStorage fruitStorage;
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
        purchaseOperationHandler = new PurchaseOperationHandler(fruitStorage);
    }

    @Test
    void purchaseOperationHandler_performOperation_ok() {
        String fruit = "Apple";
        final int initialInventory = 10;
        int purchaseQuantity = 5;
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit(fruit);
        transaction.setQuantity(purchaseQuantity);

        fruitStorage.addFruit(fruit, initialInventory);

        purchaseOperationHandler.performOperation(transaction);

        int updatedInventory = fruitStorage.getFruitInventory().get(fruit);
        assertEquals(initialInventory - purchaseQuantity, updatedInventory);
    }
}
