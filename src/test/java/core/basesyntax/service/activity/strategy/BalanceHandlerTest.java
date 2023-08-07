package core.basesyntax.service.activity.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

class BalanceHandlerTest {

    private static final Map<String, Integer> fruitDb = FruitsDb.fruitDb;
    private static final BalanceHandler balanceHandler = new BalanceHandler();

    @BeforeEach
    void setUp() {
        fruitDb.clear();
    }

    @Test
    void processActivity() {
    }

    @ParameterizedTest
    @MethodSource("validActivities")
    void handleActvity_validActivitiy_Ok(List<FruitActivity> activities) {
        assertDoesNotThrow(() -> {
            for (FruitActivity activity : activities) {
                balanceHandler.processActivity(activity);
            }
        }, "Handle valid balance-activities shouldnt throw any exceptions");
    }

    static Stream<Arguments> validActivities() {
        return Stream.of(
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20));
                        }},
                        new HashMap<String, Integer>() {{
                            put("banana", 20);
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
    @MethodSource("invalidActivities")
    void handleActvity_invalidActivitiy_Ok(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> balanceHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> invalidActivities() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", -20)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", -1)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", null))
        );
    }
}
