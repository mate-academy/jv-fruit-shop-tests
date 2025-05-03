package core.basesyntax.strategy.impl;

import core.basesyntax.dao.Dao;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final Dao dao = new TestDao();
    private static final OperationHandler handler = new SupplyHandler(dao);

    @Before
    public void setUp() {
        Storage.supplies.clear();
    }

    @Test
    public void apply_normalData_ok() {
        Storage.supplies.put("apple", 100);
        handler.apply("apple", 69);
        Assert.assertTrue(Storage.supplies.containsKey("apple"));
        Integer expected = 169;
        Integer actual = Storage.supplies.get("apple");
        Assert.assertEquals(expected, actual);
    }

    private static class TestDao implements Dao {

        public Integer get(String product) {
            return Storage.supplies.get(product);
        }

        public Integer put(String product, Integer amount) {
            return Storage.supplies.put(product, amount);
        }

        public boolean contains(String product) {
            return Storage.supplies.containsKey(product);
        }

        public Integer remove(String product) {
            return Storage.supplies.remove(product);
        }

        public Set<String> getProductNames() {
            return Storage.supplies.keySet();
        }
    }
}
