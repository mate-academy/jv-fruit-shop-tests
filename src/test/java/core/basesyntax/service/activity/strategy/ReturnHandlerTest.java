package core.basesyntax.service.activity.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import core.basesyntax.db.FruitsDb;
import core.basesyntax.model.FruitActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        fruitDb.put("apple", 0);
        fruitDb.put("orange", 0);
    }

    @Test
    void processActivity() {
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
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20));
                        }},
                        new HashMap<String, Integer>() {{
                            put("banana", 20);
                            put("apple", 0);
                            put("orange", 0);
                        }}
                ),
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20));
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "apple", 100));
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "orange", 200));
                        }},
                        new HashMap<String, Integer>() {{
                            put("banana", 20);
                            put("apple", 100);
                            put("orange", 200);
                        }}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidReturnActivities")
    void handleActvity_invalidReturnActivitiy_NotOk(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> returnHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> invalidReturnActivities() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", -20)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", -1)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", null))
        );
    }
}
