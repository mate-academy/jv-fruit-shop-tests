package core.basesyntax.service.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private Storage storage;
    private FruitService fruitService;
    private ReturnOperationHandler handler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruitService = new FruitServiceImpl(storage);
        handler = new ReturnOperationHandler(storage, fruitService);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void apply_dtoIsValid_ok() {
        FruitTransactionDto dto = new FruitTransactionDto("r", "apple", 10);
        storage.addFruit("apple", 5);
        handler.apply(dto);
        int actual = storage.getFruitQuantity("apple");
        assertEquals(15, actual);

    }

    @Test
    void apply_dtoFruitQuantityIsZero_ok() {
        FruitTransactionDto dto = new FruitTransactionDto("r", "apple", 0);
        storage.addFruit("apple", 5);
        handler.apply(dto);
        assertEquals(5, storage.getFruitQuantity("apple"));
    }
}
