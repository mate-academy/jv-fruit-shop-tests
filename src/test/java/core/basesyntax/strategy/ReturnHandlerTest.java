package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private ReturnHandler returnHandler;
    private FruitDto fruit;
    private FruitDao storageDao;

    @BeforeEach
    void setUp() {
        returnHandler = new ReturnHandler();
        fruit = new FruitDto();
        storageDao = new FruitDaoImpl();
    }

    @Test
    void process_validDto_ok() {
        fruit.setQuantity(50);
        fruit.setName("banana");
        fruit.setType("b");
        returnHandler.process(fruit);
        Integer actual = Storage.fruitStorage.get("banana");
        Integer expected = 100;
        Assertions.assertEquals(expected,actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}
