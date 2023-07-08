package core.basesyntax.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.db.ShopStorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StrategyTest {
    public static final FruitTransaction BALANCE_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "APPLE", 25);
    private static final FruitTransaction PURCHASE_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "APPLE", 30);
    private static final FruitTransaction RETURN_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "BANANA", 20);
    private static final FruitTransaction SUPPLY_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "BANANA", 50);
    private static ShopStorage shopStorage;
    private final BalanceHandler balanceStrategy = new BalanceHandler();
    private final SupplyHandler supplyStrategy = new SupplyHandler();
    private final PurchaseHandler purchaseStrategy = new PurchaseHandler();
    private final ReturnHandler returnStrategy = new ReturnHandler();

    @BeforeAll
    public static void setUp() {
        shopStorage = new ShopStorageImpl();
    }

    @Test
    public void balance_validTransaction_Ok() {
        balanceStrategy.handle(BALANCE_TRANSACTION, shopStorage);
        assertEquals(25, shopStorage.getQuantity("APPLE"));
    }

    @Test
    public void purchase_validTransaction_Ok() {
        shopStorage.updateQuantity("APPLE", 50);
        purchaseStrategy.handle(PURCHASE_TRANSACTION, shopStorage);
        assertEquals(20, shopStorage.getQuantity("APPLE"));
    }

    @Test
    public void return_validTransaction_Ok() {
        shopStorage.updateQuantity("BANANA", 50);
        returnStrategy.handle(RETURN_TRANSACTION, shopStorage);
        assertEquals(70, shopStorage.getQuantity("BANANA"));
    }

    @Test
    public void supply_validTransaction_Ok() {
        supplyStrategy.handle(SUPPLY_TRANSACTION, shopStorage);
        assertEquals(50, shopStorage.getQuantity("BANANA"));
    }

    @Test
    public void supply_inValidTransaction_NotOk() {
        shopStorage.updateQuantity("APPLE", 10);
        assertThrows(RuntimeException.class, () -> {
            purchaseStrategy.handle(PURCHASE_TRANSACTION, shopStorage);
        });
    }
}
