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

class BalanceHandlerTest {
    private static final Map<String, Integer> fruitDb = FruitsDb.fruitDb;
    private static final BalanceHandler balanceHandler = new BalanceHandler();

    @BeforeEach
    void setUp() {
        fruitDb.clear();
    }

    @ParameterizedTest
    @MethodSource("validBalanceActivities")
    void handleActvity_validBalanceActivitiy_Ok(
            List<FruitActivity> activities, Map<String, Integer> expected
    ) {
        assertDoesNotThrow(() -> {
            for (FruitActivity activity : activities) {
                balanceHandler.processActivity(activity);
            }
        }, "Handle valid balance-activities shouldnt throw any exceptions");
        Map<String, Integer> actual = fruitDb;
        assertEquals(expected, actual, "BalanceHandler doesnt wok perly");
    }

    static Stream<Arguments> validBalanceActivities() {
        return Stream.of(
                arguments(
                        List.of(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20)),
                        Map.of("banana", 20)
                ),
                arguments(
                        List.of(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20),
                                new FruitActivity(FruitActivity.Type.BALANCE, "apple", 100),
                                new FruitActivity(FruitActivity.Type.BALANCE, "orange", 200)),
                        Map.of("banana", 20, "apple", 100, "orange", 200)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidBalanceActivities")
    void handleActvity_invalidBalanceActivitiy_notOk(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> balanceHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> invalidBalanceActivities() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", -20)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", -1)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", null))
        );
    }
}
