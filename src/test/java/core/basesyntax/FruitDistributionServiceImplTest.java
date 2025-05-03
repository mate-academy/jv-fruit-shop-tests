package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.FruitDistributionService;
import core.basesyntax.service.impl.FruitDistributionServiceImpl;
import core.basesyntax.strategy.FruitDistributionStrategy;
import core.basesyntax.strategy.FruitDistributionStrategyImpl;
import core.basesyntax.strategy.ShopActivities;
import core.basesyntax.strategy.ShopBalance;
import core.basesyntax.strategy.ShopPurchase;
import core.basesyntax.strategy.ShopReturn;
import core.basesyntax.strategy.ShopSupply;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDistributionServiceImplTest {
    private static final String HEADER = "MyHeader";
    private static final String FIRST_FRUIT_TYPE = "banana";
    private static final int BANANA_INCOME = 330;
    private static final String SECOND_FRUIT_TYPE = "raspberry";
    private static final int RASPBERRY_INCOME = 780;
    private static List<String> inputList;
    private static Map<FruitTransaction.Operation, ShopActivities> shopActivitiesMap;
    private FruitDistributionStrategy distributionStrategy;
    private FruitDistributionService distributionService;

    @BeforeAll
    static void beforeAll() {
        inputList = new ArrayList<>();
        shopActivitiesMap = new HashMap<>();
        shopActivitiesMap.put(FruitTransaction.Operation.BALANCE, new ShopBalance());
        shopActivitiesMap.put(FruitTransaction.Operation.SUPPLY, new ShopSupply());
        shopActivitiesMap.put(FruitTransaction.Operation.PURCHASE, new ShopPurchase());
        shopActivitiesMap.put(FruitTransaction.Operation.RETURN, new ShopReturn());
        inputList.add("MyHeader");
        inputList.add("s,banana,150");
        inputList.add("s,apple,250");
        inputList.add("s,raspberry,780");
        inputList.add("r,banana,180");
    }

    @BeforeEach
    void setUp() {
        distributionStrategy = new FruitDistributionStrategyImpl(shopActivitiesMap);
        distributionService = new FruitDistributionServiceImpl(distributionStrategy);
    }

    @AfterEach
    void tearDown() {
        inputList.clear();
    }

    @Test
    void countFruitDistribution_inputDataList_ok() {
        distributionService.countFruitDistribution(inputList);
        int expected = BANANA_INCOME;
        int actual = FruitStorage.fruits.get(FIRST_FRUIT_TYPE);
        assertEquals(expected, actual);

        expected = RASPBERRY_INCOME;
        actual = FruitStorage.fruits.get(SECOND_FRUIT_TYPE);
        assertEquals(expected, actual);
    }

    @Test
    void countFruitDistribution_incorrectInputDataList_notOk() {
        inputList.add(HEADER);
        inputList.add(HEADER);
        inputList.add("r,apple,100");
        assertThrows(RuntimeException.class,
                () -> distributionService.countFruitDistribution(inputList));
    }
}
