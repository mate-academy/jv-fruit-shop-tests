package core.basesyntax.dao;

import static core.basesyntax.db.Storage.STORAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitDaoTest {
    private static FruitDao fruitDao;

    @BeforeAll
    static void setUp() {
        fruitDao = new FruitDaoImp();
    }

    @AfterEach
    void afterEach() {
        STORAGE.clear();
    }

    @Test
    void getStorage_ValidData_Ok() {
        STORAGE.put("data1",1);
        STORAGE.put("data2",2);
        assertEquals(STORAGE,fruitDao.getStorage());
    }

    @Test
    void get_ValidData_Ok() {
        STORAGE.put("data1",1);
        STORAGE.put("data2",2);
        assertEquals(1,fruitDao.get("data1"));
    }

    @Test
    void add() {
        fruitDao.add("data1",1);
        fruitDao.add(List.of(
                new FruitTransaction(Operation.RETURN, "data2", 133),
                new FruitTransaction(Operation.SUPPLY, "data3", 500))
        );
        assertEquals(0, STORAGE.get("data2"));
    }

    @Test
    void get_InValidData_NotOk() {
        STORAGE.put("data1",1);
        STORAGE.put("data2",2);
        assertThrows(IllegalArgumentException.class,
                () -> fruitDao.get("data3"));
    }
}
