package core.basesyntax.service.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private Storage storage;
    private FruitService fruitService;
    private PurchaseOperationHandler handler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruitService = new FruitServiceImpl(storage);
        handler = new PurchaseOperationHandler(storage, fruitService);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void apply_dtoIsValid_ok() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "apple", 10);
        storage.addFruit("apple", 15);
        handler.apply(dto);
        int actual = storage.getFruitQuantity("apple");
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void apply_dtoFruitQuantityIsZero_ok() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "apple", 0);
        storage.addFruit("apple", 15);
        handler.apply(dto);
        int actual = storage.getFruitQuantity("apple");
        int expected = 15;
        assertEquals(expected, actual);
    }

    @Test
    void apply_dtoFruitQuantityIsMoreThanInStorage_notOk() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "apple", 20);
        storage.addFruit("apple", 15);
        assertThrows(RuntimeException.class, () -> handler.apply(dto));
        String actual = assertThrows(RuntimeException.class, () ->
                handler.apply(dto)).getMessage();
        assertEquals("Quantity of " + dto.getNameFruit()
                + " is not enough for this purchase", actual);
    }
}
