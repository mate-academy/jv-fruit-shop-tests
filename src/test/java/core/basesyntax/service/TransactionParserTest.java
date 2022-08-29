package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TransactionParserTest {
    private static final String INPUT_TEST_PATH = "src/test/resources/inputTest3.csv";
    private static TransactionParser transactionParser;
    private static StorageDao storageDao = new StorageDaoImpl();
    private static FruitService fruitService = new FruitServiceImpl(storageDao);

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new CsvTransactionParserImpl();
    }

    @Test
    public void parse_validOutput_Ok() {
        List<String> data = List.of("type,fruit,amount","b,apple,90","b,banana,30");
        List<FruitOperation> actual = transactionParser.parseDataFile(data);
        List<FruitOperation> expected = new ArrayList<>();
        expected.add(new FruitOperation(FruitOperation.Operation.BALANCE,"apple",90));
        expected.add(new FruitOperation(FruitOperation.Operation.BALANCE, "banana",30));
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void parse_nullData_NotOk() {
        transactionParser.parseDataFile(null);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
