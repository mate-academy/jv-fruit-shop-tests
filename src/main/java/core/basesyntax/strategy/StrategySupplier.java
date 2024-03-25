package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public class StrategySupplier {
    private final Strategy balanceStrategy = new BalanceStrategy();
    private final Strategy supplyStrategy = new SupplyStrategy();
    private final Strategy purchaseStrategy = new PurchaseStrategy();
    private final Strategy returnStrategy = new ReturnStrategy();

    public Strategy getStrategy(FruitTransaction.Operation operationType) {
        return switch (operationType) {
            case BALANCE -> balanceStrategy;
            case SUPPLY -> supplyStrategy;
            case PURCHASE -> purchaseStrategy;
            case RETURN -> returnStrategy;
        };
    }
}
