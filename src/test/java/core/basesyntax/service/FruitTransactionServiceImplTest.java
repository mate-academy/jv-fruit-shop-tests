package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.Main;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private OperationStrategy operationStrategy;
    private List<Transaction> transactionList = new ArrayList<>();
    private Map<Operation, OperationHandler> operationHandlersMap;
    private String fruit = "apple";

    @Before
    public void setUp() {
        Storage.clearStorage();
        transactionList.add(new Transaction(Operation.BALANCE, fruit,10));
        transactionList.add(new Transaction(Operation.RETURN, fruit,10));
        transactionList.add(new Transaction(Operation.SUPPLY, fruit,10));
        transactionList.add(new Transaction(Operation.PURCHASE, fruit,10));
        operationHandlersMap = Main.initOperationsMap();
    }

    @After
    public void tearDown() {
        transactionList.clear();
    }

    @Test
    public void processDailyReport_validData_ok() {
        OperationStrategy op = new OperationStrategyImpl(operationHandlersMap);
        new FruitTransactionServiceImpl(op).processDailyReport(transactionList);
        assertEquals(20, (long) Storage.getFruitAmount(fruit));
    }

    @Test
    public void processDailyReport_wrongAmount_notOk() {
        transactionList.clear();
        transactionList.add(new Transaction(Operation.BALANCE, fruit,10));
        transactionList.add(new Transaction(Operation.PURCHASE, fruit,11));
        OperationStrategy op = new OperationStrategyImpl(operationHandlersMap);
        ;
        assertThrows(RuntimeException.class, () ->
                        new FruitTransactionServiceImpl(op).processDailyReport(transactionList),
                "Illegal daily report value for "
                        + fruit
                        + ". Total amount is negative.");
    }
}
