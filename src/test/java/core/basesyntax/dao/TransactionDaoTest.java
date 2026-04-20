package core.basesyntax.dao;

import core.basesyntax.checker.Checker;
import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.service.TransactionDao;
import core.basesyntax.service.impl.TransactionDaoImpl;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionDaoTest {

    private Storage storage;
    private Checker checker;
    private TransactionDao dao;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
        checker = new Checker(storage);
        dao = new TransactionDaoImpl(storage, checker);
    }

    @Test
    void set_validFruitAndQuantity_ok() {
        dao.set("Banana", 100);
        Assertions.assertEquals(100, storage.getQuantity("Banana").get());
    }

    @Test
    void set_invalidFruit_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dao.set("", 10));
    }

    @Test
    void add_existingFruit_ok() {
        dao.set("Apple", 50);

        dao.add("Apple", 30);

        Assertions.assertEquals(80, storage.getQuantity("Apple").get());
    }

    @Test
    void add_negativeQuantity_notOk() {
        dao.set("Apple", 10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dao.add("Apple", -5));
    }

    @Test
    void subtract_valid_ok() {
        dao.set("Banana", 100);

        dao.subtract("Banana", 50);

        Assertions.assertEquals(50, storage.getQuantity("Banana").get());
    }

    @Test
    void subtract_resultLessThanZero_notOk() {
        dao.set("Banana", 100);

        Assertions.assertThrows(RuntimeException.class,
                () -> dao.subtract("Banana", 150));
    }

    @Test
    void getAll_returnsCopy_ok() {
        dao.set("Banana", 100);
        dao.set("Apple", 50);

        Map<String, Integer> result = dao.getAll();

        result.put("Hack", 999);

        Assertions.assertFalse(dao.getAll().containsKey("Hack"));
    }
}
