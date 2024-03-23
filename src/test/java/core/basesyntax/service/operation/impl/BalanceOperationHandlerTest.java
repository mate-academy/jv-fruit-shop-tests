package core.basesyntax.service.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private Storage storage;
    private FruitService fruitService;
    private BalanceOperationHandler handler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruitService = new FruitServiceImpl(storage);
        handler = new BalanceOperationHandler(fruitService);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void apply_dtoIsValid_ok() {
        FruitTransactionDto dto = new FruitTransactionDto("b", "banana",20);
        handler.apply(dto);
        int actual = storage.getFruitQuantity("banana");
        assertEquals(20, actual);
    }

    @Test
    void apply_dtoFruitQuantityIsZero_ok() {
        FruitTransactionDto dto = new FruitTransactionDto("b", "banana",0);
        handler.apply(dto);
        int actual = storage.getFruitQuantity("banana");
        assertEquals(0, actual);
    }
}
