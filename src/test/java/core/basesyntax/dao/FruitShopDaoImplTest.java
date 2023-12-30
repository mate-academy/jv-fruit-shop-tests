package core.basesyntax.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;

class FruitShopDaoImplTest {
    private FruitShopDao fruitShopDao;

    @BeforeEach
    void setUp() {
        fruitShopDao = new FruitShopDaoImpl();
    }

    @Test
    void specifiedLemonIsNotExist_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitShopDao.getQuantity("lemon");
        });
    }

    @Test
    void specifiedCabbageIsNotExist_notOk() {
        assertThrows(RuntimeException.class, () -> {
           fruitShopDao.getQuantity("cabbage");
        });
    }
}
