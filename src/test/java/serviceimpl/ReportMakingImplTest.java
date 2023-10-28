package serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.Main;
import db.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.OperationStrategy;
import strategy.Operating;

class ReportMakingImplTest {

    private static final String APPLE = "apple";
    private static final Integer AMOUNT = 10;
    private static final Integer PLUS_INDEX = 1;
    private static final int EXPECTED_RESULT = 20;

    private final List<FruitTransaction> transactionList = new ArrayList<>();
    private ReportMakingImpl fruitTransactionService;

    @BeforeEach
    public void setUp() {
        Storage.clearStorage();
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, APPLE,AMOUNT));
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.RETURN, APPLE,AMOUNT));
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, APPLE,AMOUNT));
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, APPLE,AMOUNT));
        Map<FruitTransaction.Operation, Operating> operationHandlersMap = Main.allOperationsMap();
        OperationStrategy op = new OperationStrategyImpl(operationHandlersMap);
        fruitTransactionService = new ReportMakingImpl(op);
    }

    @AfterEach
    public void tearDown() {
        transactionList.clear();
    }

    @Test
    public void testValidDailyReportProcessing() {
        fruitTransactionService.makeReport(transactionList);
        assertEquals(EXPECTED_RESULT, (long) Storage.getFruitsNumber(APPLE));
    }

    @Test
    public void testNegativeAmountInTransaction() {
        final List<FruitTransaction> originalList = new ArrayList<>(transactionList);
        transactionList.clear();
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, APPLE, AMOUNT));
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, APPLE, AMOUNT + PLUS_INDEX));
        assertNotEquals(originalList, transactionList);
    }

    @Test
    public void testZeroAmountInTransaction() {
        final List<FruitTransaction> originalList = new ArrayList<>(transactionList);
        transactionList.clear();
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, APPLE, AMOUNT));
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, APPLE, 0));
        assertNotEquals(originalList, transactionList);
    }

    @Test
    public void processDailyReport_wrongAmount_notOk() {
        final List<FruitTransaction> originalList = new ArrayList<>(transactionList);
        transactionList.clear();
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, APPLE, AMOUNT));
        transactionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, APPLE, AMOUNT + PLUS_INDEX));
        fruitTransactionService.makeReport(transactionList);
        assertNotEquals(originalList, transactionList);
    }
}
