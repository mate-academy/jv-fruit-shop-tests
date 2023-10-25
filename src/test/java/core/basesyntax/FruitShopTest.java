package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.DataConvertService;
import core.basesyntax.service.impl.CreateReportServiceImpl;
import core.basesyntax.service.impl.DataConvertServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FruitShopTest {
    private static final String APPLE_NAME = "apple";
    private static final String BANANA_NAME = "banana";
    private static final String GRAPE_NAME = "grape";
    private static final String PEACH_NAME = "peach";
    private static final Integer APPLE_COUNT = 15;
    private static final Integer BANANA_COUNT = 20;
    private static final Integer GRAPE_COUNT = 30;
    private static final Integer PEACH_COUNT = 5;
    private static StorageDao storageDao;
    private static CreateReportService createReport;
    private static DataConvertService dataConverter;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        dataConverter = new DataConvertServiceImpl();
    }

    @BeforeEach
    void setUp() {
        createReport = new CreateReportServiceImpl();
    }

    @Test
    void StorageDaoAdd_twoFruits_Ok() {
        Map<String, Integer> expected = Map.of(APPLE_NAME, APPLE_COUNT,
                BANANA_NAME, BANANA_COUNT);

        storageDao.add(APPLE_NAME, APPLE_COUNT);
        storageDao.add(BANANA_NAME, BANANA_COUNT);

        Map<String, Integer> actual = Storage.fruitsCount;

        assertEquals(expected, actual);
    }

    @Test
    void StorageDaoGet_twoFruits_Ok() {
        Map<String, Integer> expected = Map.of(APPLE_NAME, APPLE_COUNT,
                BANANA_NAME, BANANA_COUNT);

        storageDao.add(APPLE_NAME, APPLE_COUNT);
        storageDao.add(BANANA_NAME, BANANA_COUNT);

        Map<String, Integer> actual = storageDao.getInfo();

        assertEquals(expected, actual);
    }

    @Test
    void createReport_twoFruits_Ok() {
        Map<String, Integer> putMap = Map.of(APPLE_NAME, APPLE_COUNT,
                BANANA_NAME, BANANA_COUNT);
        Storage.fruitsCount.putAll(putMap);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,15";
        String actual = createReport.createReport();

        assertEquals(expected, actual);
    }

    @Test
    void createReport_fourFruits_Ok() {
        Map<String, Integer> putMap = Map.of(GRAPE_NAME, GRAPE_COUNT,
                PEACH_NAME, PEACH_COUNT,
                BANANA_NAME, BANANA_COUNT,
                APPLE_NAME, APPLE_COUNT);
        Storage.fruitsCount.putAll(putMap);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,15" + System.lineSeparator()
                + "peach,5" + System.lineSeparator()
                + "grape,30";

        String actual = createReport.createReport();

        assertEquals(expected, actual);
    }

    @Test
    void dataConvert_correctValues_Ok() {
        FruitTransaction first =
                new FruitTransaction(Operation.BALANCE, APPLE_NAME, APPLE_COUNT);
        FruitTransaction second =
                new FruitTransaction(Operation.BALANCE, BANANA_NAME, BANANA_COUNT);
        FruitTransaction third =
                new FruitTransaction(Operation.SUPPLY, APPLE_NAME, 10);
        FruitTransaction fourth =
                new FruitTransaction(Operation.SUPPLY, BANANA_NAME, 5);
        FruitTransaction fifth =
                new FruitTransaction(Operation.PURCHASE, APPLE_NAME, 10);
        FruitTransaction sixth =
                new FruitTransaction(Operation.PURCHASE, BANANA_NAME, 15);
        FruitTransaction seventh =
                new FruitTransaction(Operation.RETURN, APPLE_NAME, 5);
        FruitTransaction eighth =
                new FruitTransaction(Operation.RETURN, BANANA_NAME, 10);

        List<String> args = List.of("b,apple,15", "b,banana,20",
                "s,apple,10", "s,banana,5",
                "p,apple,10", "p,banana,15",
                "r,apple,5", "r,banana,10");

        List<FruitTransaction> expected = List.of(first, second, third,
                fourth, fifth, sixth, seventh, eighth);
        List<FruitTransaction> actual = dataConverter.convert(args);

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsCount.clear();
    }
}