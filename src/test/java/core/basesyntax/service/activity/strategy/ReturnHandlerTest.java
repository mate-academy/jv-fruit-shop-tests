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

class ReturnHandlerTest {
    private static final Map<String, Integer> fruitDb = FruitsDb.fruitDb;
    private static final ReturnHandler returnHandler = new ReturnHandler();

    @BeforeEach
    void setUp() {
        fruitDb.clear();
        fruitDb.put("banana", 0);
        fruitDb.put("apple", 10);
        fruitDb.put("orange", 100);
    }

    @ParameterizedTest
    @MethodSource("validReturnActivities")
    void handleActvity_validReturnActivitiy_Ok(
            List<FruitActivity> activities, Map<String, Integer> expected
    ) {
        assertDoesNotThrow(() -> {
            for (FruitActivity activity : activities) {
                returnHandler.processActivity(activity);
            }
        }, "Handle valid return-activities shouldnt throw any exceptions");
        Map<String, Integer> actual = fruitDb;
        assertEquals(expected, actual, "BalanceHandler doesnt wok perly");
    }

    static Stream<Arguments> validReturnActivities() {
        return Stream.of(
                arguments(
                        List.of(new FruitActivity(FruitActivity.Type.RETURN, "banana", 20)),
                        Map.of("banana", 20, "apple", 10, "orange", 100)
                ),
                arguments(
                        List.of(new FruitActivity(FruitActivity.Type.RETURN, "banana", 20),
                                new FruitActivity(FruitActivity.Type.RETURN, "apple", 100),
                                new FruitActivity(FruitActivity.Type.RETURN, "orange", 200)),
                        Map.of("banana", 20, "apple", 110, "orange", 300)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidReturnActivities")
    void handleActvity_invalidReturnActivitiy_notOk(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> returnHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> invalidReturnActivities() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.RETURN, "banana", -20)),
                arguments(new FruitActivity(FruitActivity.Type.RETURN, "banana", -1)),
                arguments(new FruitActivity(FruitActivity.Type.RETURN, "banana", null))
        );
    }
}
