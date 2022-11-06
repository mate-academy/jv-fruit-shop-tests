package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.enums.Operation;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.impl.OperationHandlerBalanceImpl;
import core.basesyntax.strategy.impl.StrategyImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private TransactionProcessorImpl transactionProcessor;
    private Strategy testStrategy;
    private List<String> data;
    private Storage testStorageForProcessor;

    @Before
    public void setUp() {
        transactionProcessor = new TransactionProcessorImpl();
        testStrategy = new StrategyImpl();
        testStorageForProcessor = new StorageImpl();
        data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        testStrategy.addStrategyType(Operation.BALANCE.getOperation(),
                new OperationHandlerBalanceImpl());
    }

    @Test
    public void processingDataMethodTest_Ok() {
        transactionProcessor.processingData(data, testStrategy);
        List<String> expectedList = List.of("banana,20", "apple,100");
        expectedList = expectedList.stream().sorted().collect(Collectors.toList());
        List<String> actualList = testStorageForProcessor.getStorageAsList();
        assertEquals(expectedList, actualList);
    }
}

