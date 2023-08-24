package core.basesyntax.service.activity.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import core.basesyntax.db.FruitsDb;
import core.basesyntax.model.FruitActivity;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SupplyHandlerTest {

    private static final Map<String, Integer> fruitDb = FruitsDb.fruitDb;
    private static final SupplyHandler supplyHandler = new SupplyHandler();

    @BeforeEach
    void setUp() {
        fruitDb.clear();
        fruitDb.put("banana", 0);
        fruitDb.put("apple", 10);
        fruitDb.put("orange", 100);
    }

    @ParameterizedTest
    @MethodSource("validSupplyActivities")
    void handleActvity_validSupplyActivitiy_Ok(
            List<FruitActivity> activities, Map<String, Integer> expected
    ) {
        assertDoesNotThrow(() -> {
            for (FruitActivity activity : activities) {
                supplyHandler.processActivity(activity);
            }
        }, "Handle valid balance-activities shouldnt throw any exceptions");
        Map<String, Integer> actual = fruitDb;
        assertEquals(expected, actual, "BalanceHandler doesnt wok perly");
    }

    static Stream<Arguments> validSupplyActivities() {
        return Stream.of(
                arguments(
                        List.of(new FruitActivity(FruitActivity.Type.SUPPLY, "banana", 20)),
                        Map.of("banana", 20, "apple", 10, "orange", 100)
                ),
                arguments(
                        List.of(new FruitActivity(FruitActivity.Type.SUPPLY, "banana", 20),
                                new FruitActivity(FruitActivity.Type.SUPPLY, "apple", 100),
                                new FruitActivity(FruitActivity.Type.SUPPLY, "orange", 200)),
                        Map.of("banana", 20, "apple", 110, "orange", 300)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidSupplyActivities")
    void handleActvity_invalidSupplyActivitiy_notOk(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> supplyHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> invalidSupplyActivities() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.SUPPLY, "banana", -20)),
                arguments(new FruitActivity(FruitActivity.Type.SUPPLY, "banana", -1)),
                arguments(new FruitActivity(FruitActivity.Type.SUPPLY, "banana", null))
        );
    }
}
