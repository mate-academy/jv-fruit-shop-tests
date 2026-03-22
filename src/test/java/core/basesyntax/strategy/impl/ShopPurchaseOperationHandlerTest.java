package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.ShopStorage;
import core.basesyntax.strategy.ShopOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopPurchaseOperationHandlerTest {
    private static Storage shopStorage;
    private final ShopOperationHandler handler = new ShopPurchaseOperationHandler();

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
        shopStorage.update("lemon", 13);
        handler.handle(shopStorage, "lemon", 3);

        Map<String, Integer> expected = Map.of("lemon", 10);
        Map<String, Integer> actual = shopStorage.getAll();

        assertEquals(expected, actual, "handler does not decrease quantity properly");
    }

    @Test
    void handle_notEnoughInStock_notOk() {
        shopStorage.update("lemon", 13);
        assertThrows(RuntimeException.class, () ->
                handler.handle(shopStorage, "lemon", 15));
    }

    @Test
    void handle_evenStockAndPurchaseQuantity_ok() {
        shopStorage.update("lemon", 1);
        handler.handle(shopStorage, "lemon", 1);

        Map<String, Integer> expected = Map.of("lemon", 0);
        Map<String, Integer> actual = shopStorage.getAll();

        assertEquals(expected, actual, "handler does not allow to buy all remaining stock");
    }
}
