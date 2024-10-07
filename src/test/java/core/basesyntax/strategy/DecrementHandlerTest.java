package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.StorageServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DecrementHandlerTest {
    private StorageServiceImpl storageService;
    private DecrementHandler decrementHandler;
    private Fruit banana;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        decrementHandler = new DecrementHandler(storageService);
        banana = new Fruit("banana");
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_validDecrementing_ok() {
        storageService.addFruit(banana, 50);
        decrementHandler.handle(banana, 30);
        assertEquals(20, Storage.getFruitQuiantity(banana));
    }

    @Test
    void handle_insufficientStock_notOk() {
        Fruit banana = new Fruit("banana");
        storageService.addFruit(banana, 10);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> decrementHandler.handle(banana, 20));
        assertEquals("Not enough banana in storage to remove", exception.getMessage());
    }
}
