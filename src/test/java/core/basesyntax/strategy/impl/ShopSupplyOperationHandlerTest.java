package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.ShopStorage;
import core.basesyntax.strategy.ShopOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopSupplyOperationHandlerTest {
    private static Storage shopStorage;
    private final ShopOperationHandler handler = new ShopSupplyOperationHandler();

    @BeforeEach
    void setUp() {
        shopStorage = new ShopStorage();
    }

    @Test
    void handle_validCase_updatesProperly_ok() {
        shopStorage.update("orange", 60);
        handler.handle(shopStorage, "orange", 40);

        Map<String, Integer> expected = Map.of("orange", 100);
        Map<String, Integer> actual = shopStorage.getAll();

        assertEquals(expected, actual, "handler does not supply products properly");
    }
}
