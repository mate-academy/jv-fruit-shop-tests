package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.ShopStorage;
import core.basesyntax.strategy.ShopOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopBalanceOperationHandlerTest {
    private static Storage shopStorage;
    private final ShopOperationHandler handler = new ShopBalanceOperationHandler();

    @BeforeEach
    void setUp() {
        shopStorage = new ShopStorage();
    }

    @Test
    void handle_validCase_addsProperly_ok() {
        shopStorage.update("apple", 8);
        handler.handle(shopStorage, "banana", 10);

        Map<String, Integer> expected = Map.of("apple", 8, "banana", 10);
        Map<String, Integer> actual = shopStorage.getAll();

        assertEquals(expected, actual, "handler does not adds data properly");
    }

    @Test
    void handle_validCase_updatesProperly_ok() {
        shopStorage.update("peach", 45);
        handler.handle(shopStorage, "peach", 15);

        Map<String, Integer> expected = Map.of("peach", 15);
        Map<String, Integer> actual = shopStorage.getAll();

        assertEquals(expected, actual, "handler does not updates data properly");
    }
}
