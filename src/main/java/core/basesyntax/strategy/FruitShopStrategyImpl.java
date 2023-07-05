package core.basesyntax.strategy;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitShopStrategyImpl {
    private final Map<FruitTransaction.Operation, OperationHandler> fruitShopStrategy;

    public FruitShopStrategyImpl() {
        fruitShopStrategy = new HashMap<>();
        fruitShopStrategy.put(FruitTransaction.Operation.BALANCE, new BalanceStrategy());
        fruitShopStrategy.put(FruitTransaction.Operation.SUPPLY, new SupplyStrategy());
        fruitShopStrategy.put(FruitTransaction.Operation.PURCHASE, new PurchaseStrategy());
        fruitShopStrategy.put(FruitTransaction.Operation.RETURN, new ReturnStrategy());
    }

    public void processTransactions(List<FruitTransaction> transactions, ShopStorage fruitStorage) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = fruitShopStrategy.get(transaction.getOperation());
            handler.doActivity(transaction, fruitStorage);
        }
    }
}
