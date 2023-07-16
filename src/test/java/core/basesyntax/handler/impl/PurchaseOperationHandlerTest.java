package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.ShopOperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final int PRIMARY_FRUIT_QUANTITY = 10;
    private static ShopOperationHandler purchaseOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        fruitTransaction = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction.setFruit("dummy");
        Storage.fruitStorage.put(fruitTransaction.getFruit(), PRIMARY_FRUIT_QUANTITY);

    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.remove(fruitTransaction.getFruit());
    }

    @Test
    void handle_validQuantityData_Ok() {
        assertAll(
                () -> {
                    fruitTransaction.setQuantity(PRIMARY_FRUIT_QUANTITY / 2);
                    purchaseOperationHandler.handle(fruitTransaction);
                },
                () -> {
                    Storage.fruitStorage.put(fruitTransaction.getFruit(), PRIMARY_FRUIT_QUANTITY);
                    fruitTransaction.setQuantity(PRIMARY_FRUIT_QUANTITY);
                    purchaseOperationHandler.handle(fruitTransaction);
                }
        );
        int expectedQuantity = 0;
        int actualQuantity = Storage.fruitStorage.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handle_InvalidQuantityData_NotOk() {
        fruitTransaction.setQuantity(PRIMARY_FRUIT_QUANTITY + 1);
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperationHandler.handle(fruitTransaction));
    }
}
