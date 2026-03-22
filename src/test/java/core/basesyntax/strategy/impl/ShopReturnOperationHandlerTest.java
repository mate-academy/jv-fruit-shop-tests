package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.ShopStorage;
import core.basesyntax.strategy.ShopOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopReturnOperationHandlerTest {
    private static Storage shopStorage;
    private final ShopOperationHandler handler = new ShopReturnOperationHandler();

    @BeforeAll
    static void init() {
        shopStorage = new ShopStorage();
    }

    @AfterEach
    void tearDown() {
        shopStorage.clear();
    }

    @Test
    void handle_validCase_updatesProperly_ok() {
        shopStorage.update("orange", 60);
        handler.handle(shopStorage, "orange", 40);

        Map<String, Integer> expected = Map.of("orange", 100);
        Map<String, Integer> actual = shopStorage.getAll();

        assertEquals(expected, actual, "handler does not return products properly");
    }
}
