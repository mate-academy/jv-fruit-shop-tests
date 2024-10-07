package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.StorageServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class IncrementHandlerTest {
    private final StorageServiceImpl storageService = new StorageServiceImpl();
    private final IncrementHandler incrementHandler = new IncrementHandler(storageService);

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_validIncrementData_ok() {
        Fruit orange = new Fruit("orange");
        storageService.addFruit(orange, 20);
        incrementHandler.handle(orange, 30);
        assertEquals(50, Storage.getFruitQuiantity(orange));
    }
}
