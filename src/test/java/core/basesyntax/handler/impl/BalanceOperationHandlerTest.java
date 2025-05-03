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

class BalanceOperationHandlerTest {
    private static ShopOperationHandler balanceOperationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("dummy");
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.remove(fruitTransaction.getFruit());
    }

    @Test
    void handle_validQuantityData_ok() {
        assertAll(() -> {
                    fruitTransaction.setQuantity(0);
                    balanceOperationHandler.handle(fruitTransaction);
                    int expectedQuantity = 0;
                    Integer actualQuantity = Storage.fruitStorage.get(fruitTransaction.getFruit());
                    assertEquals(expectedQuantity, actualQuantity);

                }, () -> {
                    fruitTransaction.setQuantity(12);
                    balanceOperationHandler.handle(fruitTransaction);
                    int expectedQuantity = 12;
                    Integer actualQuantity = Storage.fruitStorage.get(fruitTransaction.getFruit());
                    assertEquals(expectedQuantity, actualQuantity);
                }
        );
    }
}
