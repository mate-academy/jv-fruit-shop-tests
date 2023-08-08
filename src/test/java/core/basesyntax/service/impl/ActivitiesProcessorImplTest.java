package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import core.basesyntax.db.FruitsDb;
import core.basesyntax.model.FruitActivity;
import core.basesyntax.service.ActivitiesProcessor;
import core.basesyntax.service.activity.strategy.ActivityHandler;
import core.basesyntax.service.activity.strategy.BalanceHandler;
import core.basesyntax.service.activity.strategy.PurchaseHandler;
import core.basesyntax.service.activity.strategy.ReturnHandler;
import core.basesyntax.service.activity.strategy.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ActivitiesProcessorImplTest {

    private static final Map<FruitActivity.Type, ActivityHandler> ACTIVITY_HANDLER_MAP
            = new HashMap<>();

    static {
        ACTIVITY_HANDLER_MAP.put(FruitActivity.Type.BALANCE, new BalanceHandler());
        ACTIVITY_HANDLER_MAP.put(FruitActivity.Type.SUPPLY, new SupplyHandler());
        ACTIVITY_HANDLER_MAP.put(FruitActivity.Type.PURCHASE, new PurchaseHandler());
        ACTIVITY_HANDLER_MAP.put(FruitActivity.Type.RETURN, new ReturnHandler());
    }

    private static final ActivitiesProcessor activitiesProcessor =
            new ActivitiesProcessorImpl(new ActivityStrategyImpl(ACTIVITY_HANDLER_MAP));
    private static final Map<String, Integer> fruitDb = FruitsDb.fruitDb;

    @BeforeEach
    void setUp() {
        fruitDb.clear();
    }

    @ParameterizedTest
    @MethodSource("validActivities")
    void parseData_validActivities_Ok(
            List<FruitActivity> activities, Map<String, Integer> expected) {
        assertDoesNotThrow(() -> activitiesProcessor.processActivities(activities),
                "Parsing valid lines shouldnt throw any exceptions");
        Map<String, Integer> actual = fruitDb;
        assertEquals(expected, actual, "FruitDB isn't equals expected");
    }

    static Stream<Arguments> validActivities() {
        return Stream.of(
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20));
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "apple", 100));
                            add(new FruitActivity(FruitActivity.Type.SUPPLY, "banana", 100));
                            add(new FruitActivity(FruitActivity.Type.PURCHASE, "banana", 13));
                        }},
                        new HashMap<String, Integer>() {{
                            put("banana", 107);
                            put("apple", 100);
                        }}
                ),
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "orange", 40));
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "apple", 60));
                            add(new FruitActivity(FruitActivity.Type.SUPPLY, "orange", 30));
                            add(new FruitActivity(FruitActivity.Type.PURCHASE, "orange", 20));
                            add(new FruitActivity(FruitActivity.Type.PURCHASE, "apple", 20));

                        }},
                        new HashMap<String, Integer>() {{
                            put("orange", 50);
                            put("apple", 40);
                        }}
                ),
                arguments(
                        new ArrayList<FruitActivity>(),
                        new HashMap<String, Integer>()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidActivities")
    void parseData_invalidActivities_NotOk(List<FruitActivity> activities) {
        assertThrows(RuntimeException.class,
                () -> activitiesProcessor.processActivities(activities),
                "Parsing invalid lines should throw exceptions");
    }

    static Stream<Arguments> invalidActivities() {
        return Stream.of(
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 10));
                            add(new FruitActivity(FruitActivity.Type.PURCHASE, "banana", 20));
                        }}
                ),
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "orange", 40));
                            add(new FruitActivity(FruitActivity.Type.SUPPLY, "orange", 30));
                            add(new FruitActivity(FruitActivity.Type.PURCHASE, "orange", 100));
                        }}
                ),
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", -20));
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "apple", 100));
                        }}
                ),
                arguments(
                        null,
                        null
                )
        );
    }
}
