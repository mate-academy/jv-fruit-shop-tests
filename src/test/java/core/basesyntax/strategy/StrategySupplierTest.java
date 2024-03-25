package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StrategySupplierTest {
    private final StrategySupplier supplier = new StrategySupplier();
    private final Strategy balanceStrategy = new BalanceStrategy();
    private final Strategy supplyStrategy = new SupplyStrategy();
    private final Strategy purchaseStrategy = new PurchaseStrategy();
    private final Strategy returnStrategy = new ReturnStrategy();

    @Test
    void getStrategy_withBalance_isOK() {
        Assertions.assertEquals(balanceStrategy.getClass(),
                supplier.getStrategy(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    void getStrategy_withSupply_isOK() {
        Assertions.assertEquals(supplyStrategy.getClass(),
                supplier.getStrategy(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    void getStrategy_withPurchase_isOK() {
        Assertions.assertEquals(purchaseStrategy.getClass(),
                supplier.getStrategy(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    void getStrategy_withReturn_isOK() {
        Assertions.assertEquals(returnStrategy.getClass(),
                supplier.getStrategy(FruitTransaction.Operation.RETURN).getClass());
    }
}
