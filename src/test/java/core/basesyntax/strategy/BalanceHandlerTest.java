package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandler balanceHandler;
    private FruitDto fruit;
    private FruitDao storageDao;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandler();
        fruit = new FruitDto();
        storageDao = new FruitDaoImpl();
    }

    @Test
    void process_validDto_ok() {
        FruitDto fruitDto = new FruitDto();
        fruitDto.setQuantity(30);
        fruitDto.setName("banana");
        fruitDto.setType("b");
        balanceHandler.process(fruitDto);
        Integer actual = Storage.fruitStorage.get("banana");
        Integer expected = 30;
        Assertions.assertEquals(expected,actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}
