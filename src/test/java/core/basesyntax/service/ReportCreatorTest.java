package core.basesyntax.service;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.database.Database;
import core.basesyntax.model.Record;
import core.basesyntax.operation.AdditionHandler;
import core.basesyntax.operation.DecreaseHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static ReportCreator reportCreator;
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static RecordDao recordDao;

    @BeforeClass
    public static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE, new AdditionHandler());
        operationHandlerMap.put(SUPPLY, new AdditionHandler());
        operationHandlerMap.put(PURCHASE, new DecreaseHandler());
        operationHandlerMap.put(RETURN, new AdditionHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        reportCreator = new ReportCreatorImpl(operationStrategy);
        recordDao = new RecordDaoImpl();
    }

    @Test
    public void createReport_CorrectData_Ok() {
        Database.RECORDS.add(new Record("b","banana",20));
        Database.RECORDS.add(new Record("b","apple",100));
        Database.RECORDS.add(new Record("s","banana",100));
        Database.RECORDS.add(new Record("p","banana",13));
        List<String> actual = reportCreator.createReport();
        List<String> expected = new ArrayList<>();
        expected.add("banana,107");
        expected.add("apple,100");
        Assert.assertEquals("Reports differ from each other!", expected, actual);
    }

    @Test
    public void createReport_EmptyData_Ok() {
        List<String> actual = reportCreator.createReport();
        List<String> expected = Collections.emptyList();
        Assert.assertEquals("Report should be empty!", expected, actual);
    }

    @After
    public void tearDown() {
        Database.RECORDS.clear();
        Database.FRUIT_BALANCE.clear();
    }
}
