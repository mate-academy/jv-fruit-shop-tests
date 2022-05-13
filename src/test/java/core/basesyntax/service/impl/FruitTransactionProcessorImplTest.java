package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.service.OperationStrategyService;
import core.basesyntax.service.ReportCreator;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionProcessorImplTest {
    private static FruitTransactionProcessor fruitTransactionProcessor;
    private static OperationStrategyService operationStrategyService;
    private static FruitTransactionParser fruitTransactionParser;
    private static ReportCreator reportCreator;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportCreator = new ReportCreatorImpl(storageDao);
        fruitTransactionParser = new FruitTransactionParserImpl();
        operationStrategyService = new OperationStrategyServiceImpl();
        fruitTransactionProcessor = new FruitTransactionProcessorImpl(operationStrategyService);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_emptyInput_NotOk() {
        List<String> list = new ArrayList<>();
        List<FruitTransaction> info = fruitTransactionParser.parse(list);
        fruitTransactionProcessor.process(info);
    }

    @Test
    public void calculate_correctInput_Ok() {
        List<String> list = new ArrayList<>();
        list.add("b,banana,20");
        list.add("s,banana,30");
        list.add("b,apple,67");
        list.add("r,apple,15");
        list.add("p,apple,15");
        List<FruitTransaction> fruitTransactions = fruitTransactionParser.parse(list);
        fruitTransactionProcessor.process(fruitTransactions);
        String actual = reportCreator.createReport();
        String expected = "fruit,quantity\nbanana,50"
                + System.lineSeparator()
                + "apple,67"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_wrongInput_NotOk() {
        List<String> list = new ArrayList<>();
        list.add("b,orange,33");
        list.add("p,orange,44");
        List<FruitTransaction> fruitTransactions = fruitTransactionParser.parse(list);
        fruitTransactionProcessor.process(fruitTransactions);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
