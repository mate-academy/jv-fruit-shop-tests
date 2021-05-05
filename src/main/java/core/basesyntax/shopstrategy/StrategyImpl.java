package core.basesyntax.shopstrategy;

import core.basesyntax.shopoperations.ListOfOperations;
import core.basesyntax.shopoperations.ShopBalanceOperation;
import java.util.Map;

public class StrategyImpl implements Strategy {
    private final Map<ListOfOperations, ShopBalanceOperation> balanceOperationMap;

    public StrategyImpl(Map<ListOfOperations, ShopBalanceOperation> balanceOperationMap) {
        this.balanceOperationMap = balanceOperationMap;
    }

    @Override
    public ShopBalanceOperation get(ListOfOperations type) {
        return balanceOperationMap.get(type);
    }
}
