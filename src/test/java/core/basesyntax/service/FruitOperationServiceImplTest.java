package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.dao.FruitOperationDao;
import core.basesyntax.dao.FruitOperationDaoImpl;
import core.basesyntax.model.FruitOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitOperationServiceImplTest {
    private FruitOperationDao fruitOperationDao;
    private FruitOperationServiceImpl fruitOperationService;

    @BeforeEach
    void setUp() {
        fruitOperationDao = new FruitOperationDaoImpl();
        fruitOperationService = new FruitOperationServiceImpl(fruitOperationDao);
    }

    @Test
    void createNewFruitOperation_validFruit() {
        String fruit = "apple";
        fruitOperationService.createNewFruitOperation(fruit);
        FruitOperation result = fruitOperationDao.get(fruit).orElse(null);
        assertNotNull(result, "Fruit operation should be added to the database");
        assertEquals(fruit, result.getFruit(), "Fruit name should match");
    }
}
