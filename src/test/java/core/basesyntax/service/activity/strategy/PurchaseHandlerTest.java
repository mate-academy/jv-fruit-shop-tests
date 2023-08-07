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

class PurchaseHandlerTest {
    private static final Map<String, Integer> fruitDb = FruitsDb.fruitDb;
    private static final PurchaseHandler purchaseHandler = new PurchaseHandler();

    @BeforeEach
    void setUp() {
        fruitDb.clear();
        fruitDb.put("banana", 100);
        fruitDb.put("apple", 50);
        fruitDb.put("orange", 10);
    }

    @Test
    void processActivity() {
    }

    @ParameterizedTest
    @MethodSource("validPurchaseActivities")
    void handleActvity_validPurchaseActivitiy_Ok(
            List<FruitActivity> activities, Map<String, Integer> expected
    ) {
        assertDoesNotThrow(() -> {
            for (FruitActivity activity : activities) {
                purchaseHandler.processActivity(activity);
            }
        }, "Handle valid return-activities shouldnt throw any exceptions");
        Map<String, Integer> actual = fruitDb;
        assertEquals(expected, actual, "BalanceHandler doesnt wok perly");
    }

    static Stream<Arguments> validPurchaseActivities() {
        return Stream.of(
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20));
                        }},
                        new HashMap<String, Integer>() {{
                            put("banana", 80);
                            put("apple", 50);
                            put("orange", 10);
                        }}
                ),
                arguments(
                        new ArrayList<FruitActivity>() {{
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 20));
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "apple", 10));
                            add(new FruitActivity(FruitActivity.Type.BALANCE, "orange", 5));
                        }},
                        new HashMap<String, Integer>() {{
                            put("banana", 80);
                            put("apple", 40);
                            put("orange", 5);
                        }}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPurchaseActivities")
    void handleActvity_invalidPurchaseActivitiy_NotOk(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> invalidPurchaseActivities() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", -20)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "apple", -1)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "orange", null))
        );
    }

    @ParameterizedTest
    @MethodSource("tooBigQuantity")
    void handleActvity_tooBig_NotOk(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> tooBigQuantity() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "banana", 101)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "apple", 51)),
                arguments(new FruitActivity(FruitActivity.Type.BALANCE, "orange", 11))
        );
    }
}
