package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import core.basesyntax.db.FruitsDb;
import core.basesyntax.model.FruitActivity;
import core.basesyntax.service.ActivitiesProcessor;
import core.basesyntax.service.activity.strategy.ActivityHandler;
import core.basesyntax.service.activity.strategy.BalanceHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ActivitiesProcessorImplTest {
    private static final Map<String, Integer> FRUIT_DB = FruitsDb.fruitDb;
    private static ActivitiesProcessor activitiesProcessor;

    @BeforeAll
    static void beforeAll() {
        Map<FruitActivity.Type, ActivityHandler> activityHandlerMap
                = Map.of(FruitActivity.Type.BALANCE, new BalanceHandler());
        activitiesProcessor =
                new ActivitiesProcessorImpl(new ActivityStrategyImpl(activityHandlerMap));
    }

    @BeforeEach
    void setUp() {
        FRUIT_DB.clear();
    }

    @ParameterizedTest
    @MethodSource("validActivities")
    void parseData_validActivities_Ok(
            List<FruitActivity> activities, Map<String, Integer> expected) {
        assertDoesNotThrow(() -> activitiesProcessor.processActivities(activities),
                "Parsing valid lines shouldnt throw any exceptions");
        Map<String, Integer> actual = FRUIT_DB;
        assertEquals(expected, actual, "FruitDB isn't equals expected");
    }

    static Stream<Arguments> validActivities() {
        return Stream.of(
                arguments(
                        List.of(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20),
                                new FruitActivity(FruitActivity.Type.BALANCE, "apple", 100)),
                        Map.of("banana", 20, "apple", 100)),
                arguments(
                        List.of(new FruitActivity(FruitActivity.Type.BALANCE, "orange", 40),
                                new FruitActivity(FruitActivity.Type.BALANCE, "apple", 60)),
                        Map.of("orange", 40, "apple", 60)),
                arguments(
                        new ArrayList<FruitActivity>(),
                        new HashMap<String, Integer>()
                )
        );
    }
}
