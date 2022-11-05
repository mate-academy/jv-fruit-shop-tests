package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import com.basesyntax.dao.FruitDao;
import com.basesyntax.dao.FruitDaoImpl;
import com.basesyntax.db.Storage;
import com.basesyntax.db.impl.StorageImpl;
import com.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopFruitDaoTests {
    private static FruitDao fruitDao;
    private Storage storage;
    private Fruit apple;
    private Fruit kiwi;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    void setUP() {
        storage = new StorageImpl();
        apple = new Fruit("apple");
        kiwi = new Fruit("Kiwi");
        fruitDao.put(apple, 10);
        fruitDao.put(kiwi, 10);
    }

    @Test
    public void put_fruitsInDb_Ok() {
        Map<Fruit, Integer> newStorage = Map.of(apple, 10, kiwi, 10);
        assertEquals(newStorage, storage.getStorage());
    }

    @Test
    public void get_fruitFromDb_Ok() {
        Integer expected = 10;
        Integer getFromDb = fruitDao.getAmountCurrentFruitInShop(apple);
        assertEquals(expected, getFromDb);
    }

    @Test
    public void update_fruitsInDb_Ok() {
        Integer expected = 20;
        fruitDao.update(apple, 20);
        Integer getFromDb = fruitDao.getAmountCurrentFruitInShop(apple);
        assertEquals(expected, getFromDb);
    }

    @Test
    public void remove_fruitsFromDb_notOk() {
        Assertions.assertEquals(null, fruitDao.remove());
    }
}
