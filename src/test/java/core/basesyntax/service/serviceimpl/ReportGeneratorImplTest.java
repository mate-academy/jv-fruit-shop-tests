package core.basesyntax.service.serviceimpl;

import core.basesyntax.dao.Dao;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final Dao dao = new TestDao();
    private static final ReportGenerator generator = new ReportGeneratorImpl(dao);

    @Before
    public void setUp() {
        Storage.supplies.clear();
    }

    @Test
    public void generate_defaultCase_ok() {
        Storage.supplies.put("apple", 25);
        Storage.supplies.put("orange", 13);
        Storage.supplies.put("banana", 35);
        List<String> expected = List.of(
                "fruit,quantity",
                "apple,25",
                "banana,35",
                "orange,13");
        List<String> actual = generator.generate();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generate_emptyStorage_ok() {
        List<String> expected = List.of(
                "fruit,quantity");
        List<String> actual = generator.generate();
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
