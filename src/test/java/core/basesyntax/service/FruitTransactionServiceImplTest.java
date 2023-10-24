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
    private static final String FRUIT_APPLE = "apple";
    private static final Integer AMOUNT = 10;
    private List<Transaction> transactionList = new ArrayList<>();
    private Map<Operation, OperationHandler> operationHandlersMap;
    private OperationStrategy op;
    private FruitTransactionService fruitTransactionService;

    @Before
    public void setUp() {
        Storage.clearStorage();
        transactionList.add(new Transaction(Operation.BALANCE, FRUIT_APPLE,AMOUNT));
        transactionList.add(new Transaction(Operation.RETURN, FRUIT_APPLE,AMOUNT));
        transactionList.add(new Transaction(Operation.SUPPLY, FRUIT_APPLE,AMOUNT));
        transactionList.add(new Transaction(Operation.PURCHASE, FRUIT_APPLE,AMOUNT));
        operationHandlersMap = Main.initOperationsMap();
        op = new OperationStrategyImpl(operationHandlersMap);
        fruitTransactionService = new FruitTransactionServiceImpl(op);
    }

    @After
    public void tearDown() {
        transactionList.clear();
    }

    @Test
    public void processDailyReport_validData_ok() {
        fruitTransactionService.processDailyReport(transactionList);
        assertEquals(20, (long) Storage.getFruitAmount(FRUIT_APPLE));
    }

    @Test
    public void processDailyReport_wrongAmount_notOk() {
        transactionList.clear();
        transactionList.add(new Transaction(Operation.BALANCE, FRUIT_APPLE,AMOUNT));
        transactionList.add(new Transaction(Operation.PURCHASE, FRUIT_APPLE,AMOUNT + 1));
        assertThrows(RuntimeException.class, () ->
                        fruitTransactionService.processDailyReport(transactionList),
                "Illegal daily report value for "
                        + FRUIT_APPLE
                        + ". Total amount is negative.");
    }
}
