package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationStrategyImpl;
import core.basesyntax.strategy.impl.ReturnOperationStrategyImpl;
import core.basesyntax.strategy.impl.SupplyOperationStrategyImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy strategy;
    private static FruitTransaction transaction;

    @BeforeAll
    static void setUpAll() {
        transaction = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void balanceStrategy_setBalance_Ok() {
        strategy = new BalanceOperationStrategyImpl();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("Strawberry");
        transaction.setQuantity(1);

        strategy.performOperation(transaction);

        assertEquals(1, Storage.getFruitStorage().size());
        assertTrue(Storage.getFruitStorage().containsKey("Strawberry"));
        assertEquals(1, Storage.getFruitStorage().get("Strawberry"));
    }

    @Test
    void returnStrategy_returnToBalance_Ok() {
        strategy = new ReturnOperationStrategyImpl();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("Apple");
        transaction.setQuantity(50);

        Storage.getFruitStorage().put("Apple", 20);
        strategy.performOperation(transaction);

        assertEquals(1, Storage.getFruitStorage().size());
        assertTrue(Storage.getFruitStorage().containsKey("Apple"));
        assertEquals(70, Storage.getFruitStorage().get("Apple"));
    }

    @Test
    void supplyStrategy_supplyToBalance_Ok() {
        strategy = new SupplyOperationStrategyImpl();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("Pear");
        transaction.setQuantity(42);

        Storage.getFruitStorage().put("Apple", 20);
        Storage.getFruitStorage().put("Pear", 100);
        strategy.performOperation(transaction);

        assertEquals(2, Storage.getFruitStorage().size());
        assertTrue(Storage.getFruitStorage().containsKey("Pear"));
        assertEquals(142, Storage.getFruitStorage().get("Pear"));
    }

    @Test
    void purchaseStrategy_purchaseItemsPositiveBalance_Ok() {
        strategy = new PurchaseOperationStrategyImpl();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Strawberry");
        transaction.setQuantity(500);

        Storage.getFruitStorage().put("Strawberry", 800);
        strategy.performOperation(transaction);

        assertEquals(1, Storage.getFruitStorage().size());
        assertTrue(Storage.getFruitStorage().containsKey("Strawberry"));
        assertEquals(300, Storage.getFruitStorage().get("Strawberry"));
    }

    @Test
    void purchaseStrategy_purchaseItemsZeroBalance_Ok() {
        strategy = new PurchaseOperationStrategyImpl();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Strawberry");
        transaction.setQuantity(40);

        Storage.getFruitStorage().put("Strawberry", 40);
        strategy.performOperation(transaction);

        assertEquals(1, Storage.getFruitStorage().size());
        assertTrue(Storage.getFruitStorage().containsKey("Strawberry"));
        assertEquals(0, Storage.getFruitStorage().get("Strawberry"));
    }

    @Test
    void purchaseStrategy_purchaseItemsNegativeBalance_NotOk() {
        strategy = new PurchaseOperationStrategyImpl();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("Strawberry");
        transaction.setQuantity(2);

        Storage.getFruitStorage().put("Strawberry", 1);
        assertThrows(RuntimeException.class, () -> strategy.performOperation(transaction));
    }
}
