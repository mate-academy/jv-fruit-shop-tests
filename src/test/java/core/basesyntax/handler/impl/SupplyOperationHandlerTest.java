package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.ShopOperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplyOperationHandlerTest {
    private static final int PRIMARY_FRUIT_QUANTITY = 10;
    private static ShopOperationHandler supplyOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        supplyOperationHandler = new SupplyOperationHandler();
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
                    fruitTransaction.setQuantity(PRIMARY_FRUIT_QUANTITY/2);
                    supplyOperationHandler.handle(fruitTransaction);
                },
                () -> {
                    Storage.fruitStorage.put(fruitTransaction.getFruit(),null);
                    fruitTransaction.setQuantity(PRIMARY_FRUIT_QUANTITY);
                    supplyOperationHandler.handle(fruitTransaction);
                }
        );
        int actualQuantity = Storage.fruitStorage.get(fruitTransaction.getFruit());
        assertEquals(PRIMARY_FRUIT_QUANTITY, actualQuantity);
    }

}