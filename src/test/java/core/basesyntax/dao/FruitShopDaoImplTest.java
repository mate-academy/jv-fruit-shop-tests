package core.basesyntax.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopDaoImplTest {
    private FruitShopDao fruitShopDao;

    @BeforeEach
    void setUp() {
        fruitShopDao = new FruitShopDaoImpl();
    }

    @Test
    void specifiedLemonIsNotExist_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fruitShopDao.getQuantity("lemon");
        });
    }

    @Test
    void specifiedCabbageIsNotExist_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fruitShopDao.getQuantity("cabbage");
        });
    }
}
