package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.strategy.FruitDistributionStrategy;
import core.basesyntax.strategy.FruitDistributionStrategyImpl;
import core.basesyntax.strategy.ShopActivities;
import core.basesyntax.strategy.ShopBalance;
import core.basesyntax.strategy.ShopPurchase;
import core.basesyntax.strategy.ShopReturn;
import core.basesyntax.strategy.ShopSupply;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDistributionStrategyImplTest {
    private final Map<FruitTransaction.Operation, ShopActivities> shopActivitiesMap
            = new HashMap<>();
    private FruitDistributionStrategy distributionStrategy;
    private Set<FruitTransaction.Operation> operations;

    @BeforeEach
    void setUp() {
        shopActivitiesMap.put(FruitTransaction.Operation.BALANCE, new ShopBalance());
        shopActivitiesMap.put(FruitTransaction.Operation.SUPPLY, new ShopSupply());
        shopActivitiesMap.put(FruitTransaction.Operation.PURCHASE, new ShopPurchase());
        shopActivitiesMap.put(FruitTransaction.Operation.RETURN, new ShopReturn());
        distributionStrategy = new FruitDistributionStrategyImpl(shopActivitiesMap);
        operations = shopActivitiesMap.keySet();
    }

    @Test
    void getActivity_operationType_ok() {
        for (FruitTransaction.Operation operation : operations) {
            Assertions.assertNotNull(distributionStrategy.getActivity(operation),
                    "Such operation don't exist: " + operation);
        }
    }

    @Test
    void getActivity_operationTypeNull_notOk() {
        assertThrows(RuntimeException.class, () -> distributionStrategy.getActivity(null));
    }
}
