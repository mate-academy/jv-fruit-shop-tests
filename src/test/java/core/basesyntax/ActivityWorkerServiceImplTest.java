package core.basesyntax;

import static core.basesyntax.model.Fruit.Operation.BALANCE;
import static core.basesyntax.model.Fruit.Operation.PURCHASE;
import static core.basesyntax.model.Fruit.Operation.RETURN;
import static core.basesyntax.model.Fruit.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ActivityWorkerServiceImpl;
import core.basesyntax.service.strategy.ActivityHandler;
import core.basesyntax.service.strategy.ActivityStrategy;
import core.basesyntax.service.strategy.ActivityStrategyImpl;
import core.basesyntax.service.strategy.BalanceActivityHandler;
import core.basesyntax.service.strategy.PurchaseActivityHandler;
import core.basesyntax.service.strategy.ReturnActivityHandler;
import core.basesyntax.service.strategy.SupplyActivityHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityWorkerServiceImplTest {
    private static final String FIRST_FRUIT_NAME = "banana";
    private static final String SECOND_FRUIT_NAME = "apple";
    private static final int FIRST_QUANTITY = 5;
    private static final int SECOND_QUANTITY = 10;
    private static final int FIRST_PUT_QUANTITY = 60;
    private static final int SECOND_PUT_QUANTITY = 50;
    private static final int FIRST_EXP_QUANTITY = 75;
    private ActivityWorkerServiceImpl activityWorkerService;
    private ActivityStrategy activityStrategy;
    private FruitDao fruitDao;

    @BeforeEach
    public void setUp() {
        Storage.inputData.clear();
        activityStrategy = new ActivityStrategyImpl(getActivitiesServiceMap());
        fruitDao = new FruitDaoImpl();
        activityWorkerService = new ActivityWorkerServiceImpl(activityStrategy, fruitDao);
    }

    @Test
    public void modifyQuantity_successModify_ok() {
        List<Fruit> fruits = Arrays.asList(
                new Fruit(BALANCE, SECOND_FRUIT_NAME, SECOND_QUANTITY),
                new Fruit(PURCHASE, SECOND_FRUIT_NAME, FIRST_QUANTITY),
                new Fruit(SUPPLY, FIRST_FRUIT_NAME, FIRST_QUANTITY),
                new Fruit(RETURN, FIRST_FRUIT_NAME, SECOND_QUANTITY)
        );
        fruitDao.put(SECOND_FRUIT_NAME, SECOND_PUT_QUANTITY);
        fruitDao.put(FIRST_FRUIT_NAME, FIRST_PUT_QUANTITY);
        activityWorkerService.modifyQuantity(fruits);
        assertEquals(FIRST_EXP_QUANTITY, fruitDao.getByName(FIRST_FRUIT_NAME));
    }

    @Test
    public void modifyQuantity_EmptyList_exceptionThrown() {
        assertThrows(RuntimeException.class,
                () -> activityWorkerService.modifyQuantity(Collections.emptyList()));
    }

    @Test
    public void modifyQuantity_nullList_exceptionThrown() {
        assertThrows(RuntimeException.class,
                () -> activityWorkerService.modifyQuantity(null));
    }

    private Map<Fruit.Operation, ActivityHandler> getActivitiesServiceMap() {
        Map<Fruit.Operation, ActivityHandler> activitiesServiceMap = new HashMap<>();
        activitiesServiceMap.put(BALANCE, new BalanceActivityHandler());
        activitiesServiceMap.put(PURCHASE, new PurchaseActivityHandler());
        activitiesServiceMap.put(SUPPLY, new SupplyActivityHandler());
        activitiesServiceMap.put(RETURN, new ReturnActivityHandler());
        return activitiesServiceMap;
    }
}
