package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.ShopOperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final int PRIMARY_FRUIT_QUANTITY = 10;
    private static ShopOperationHandler returnOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        returnOperationHandler = new ReturnOperationHandler();
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
    void handle_validQuantityData_ok() {
        assertAll(
                () -> {
                    fruitTransaction.setQuantity(PRIMARY_FRUIT_QUANTITY / 2);
                    returnOperationHandler.handle(fruitTransaction);
                },
                () -> {
                    Storage.fruitStorage.put(fruitTransaction.getFruit(),null);
                    fruitTransaction.setQuantity(PRIMARY_FRUIT_QUANTITY);
                    returnOperationHandler.handle(fruitTransaction);
                }
        );
        int actualQuantity = Storage.fruitStorage.get(fruitTransaction.getFruit());
        assertEquals(PRIMARY_FRUIT_QUANTITY, actualQuantity);
    }
}
