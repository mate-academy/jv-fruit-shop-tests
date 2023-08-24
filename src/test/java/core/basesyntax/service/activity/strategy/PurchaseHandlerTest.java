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
                        List.of(new FruitActivity(FruitActivity.Type.PURCHASE, "banana", 20)),
                        Map.of("banana", 80, "apple", 50, "orange", 10)
                ), arguments(
                        List.of(new FruitActivity(FruitActivity.Type.PURCHASE, "banana", 20),
                                new FruitActivity(FruitActivity.Type.PURCHASE, "apple", 10),
                                new FruitActivity(FruitActivity.Type.PURCHASE, "orange", 5)),
                        Map.of("banana", 80, "apple", 40, "orange", 5)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPurchaseActivities")
    void handleActvity_invalidPurchaseActivitiy_notOk(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> invalidPurchaseActivities() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.PURCHASE, "banana", -20)),
                arguments(new FruitActivity(FruitActivity.Type.PURCHASE, "apple", -1)),
                arguments(new FruitActivity(FruitActivity.Type.PURCHASE, "orange", null))
        );
    }

    @ParameterizedTest
    @MethodSource("tooBigQuantity")
    void handleActvity_tooBig_notOk(FruitActivity activity) {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.processActivity(activity)
        );
    }

    static Stream<Arguments> tooBigQuantity() {
        return Stream.of(
                arguments(new FruitActivity(FruitActivity.Type.PURCHASE, "banana", 101)),
                arguments(new FruitActivity(FruitActivity.Type.PURCHASE, "apple", 51)),
                arguments(new FruitActivity(FruitActivity.Type.PURCHASE, "orange", 11))
        );
    }
}
