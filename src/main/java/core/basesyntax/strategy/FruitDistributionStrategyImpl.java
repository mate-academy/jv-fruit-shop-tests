package core.basesyntax.strategy;

import core.basesyntax.models.FruitTransaction;
import java.util.Map;

public class FruitDistributionStrategyImpl implements FruitDistributionStrategy {
    private final Map<FruitTransaction.Operation, ShopActivities> shopActivitiesMap;

    public FruitDistributionStrategyImpl(Map<FruitTransaction.Operation,
            ShopActivities> shopActivitiesMap) {
        this.shopActivitiesMap = shopActivitiesMap;
    }

    @Override
    public ShopActivities getActivity(FruitTransaction.Operation operationType) {
        if (operationType == null) {
            throw new RuntimeException("OperationType can't be null");
        }
        return shopActivitiesMap.get(operationType);
    }
}
