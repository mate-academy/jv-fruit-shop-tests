package core.basesyntax.service;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReportServiceCsvImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceCsvImplTest {
    private ReportServiceCsvImpl reportServiceCsv;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new TestFruitStorageDao();
        reportServiceCsv = new ReportServiceCsvImpl(fruitStorageDao);
    }

    @Test
    void createReport_validData_ok() {
        fruitStorageDao.addFruit(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100));
        fruitStorageDao.addFruit(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50));

        String expectedReport = "fruit,quantity\n"
                + "banana,50\n"
                + "apple,100\n";
        String actualReport = reportServiceCsv.createReport();

        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_emptyStorage_returnsHeaderOnly() {
        String expectedReport = "fruit,quantity\n";
        String actualReport = reportServiceCsv.createReport();

        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_singleEntry_ok() {
        fruitStorageDao.addFruit(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "orange", 200));

        String expectedReport = "fruit,quantity\norange,200\n";
        String actualReport = reportServiceCsv.createReport();

        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_multipleEntries_ok() {
        fruitStorageDao.addFruit(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 150));
        fruitStorageDao.addFruit(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 75));
        fruitStorageDao.addFruit(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "orange", 200));

        String expectedReport = "fruit,quantity\n"
                + "banana,75\n"
                + "orange,200\n"
                + "apple,150\n";
        String actualReport = reportServiceCsv.createReport();

        Assertions.assertEquals(expectedReport, actualReport);
    }

    private static class TestFruitStorageDao implements FruitStorageDao {
        private final Map<String, Integer> storage = new HashMap<>();

        @Override
        public void addFruit(FruitTransaction fruitTransaction) {
            storage.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        }

        @Override
        public void increaseQuantity(FruitTransaction fruitTransaction) {
            storage.merge(fruitTransaction.getFruit(),
                    fruitTransaction.getQuantity(), Integer::sum);
        }

        @Override
        public void decreaseQuantity(FruitTransaction fruitTransaction) {
            storage.merge(fruitTransaction.getFruit(),
                    -fruitTransaction.getQuantity(), Integer::sum);
            storage.putIfAbsent(fruitTransaction.getFruit(), 0);
            if (storage.get(fruitTransaction.getFruit()) < 0) {
                storage.put(fruitTransaction.getFruit(), 0);
            }
        }

        @Override
        public Map<String, Integer> getAllAsMap() {
            return storage;
        }
    }
}
