package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.ShopOperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
    void handle_validQuantityData_Ok() {
        assertAll(
                () -> {
                    fruitTransaction.setQuantity(0);
                    balanceOperationHandler.handle(fruitTransaction);
                },
                () -> {
                    fruitTransaction.setQuantity(12);
                    balanceOperationHandler.handle(fruitTransaction);
                }

        );
        int expectedQuantity = 12;
        Integer actualQuantity = Storage.fruitStorage.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }
}
    
   