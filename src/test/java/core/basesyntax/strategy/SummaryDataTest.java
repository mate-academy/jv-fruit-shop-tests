package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SummaryDataTest {
    private static Operation operationAdd;
    private static Operation operationSubtract;
    private static List<Transaction> listTransactions;
    private static final int SIZE_ZERO = 0;
    private static SummaryData summaryData;
    private static ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        operationAdd = new Operation("BALANCE", "b", Operation.ArithmeticOperation.ADD);
        operationSubtract = new Operation("PURCHASE", "p", Operation.ArithmeticOperation.SUBTRACT);
        readerService = new ReaderServiceImpl();
        summaryData = new SummaryData();
        listTransactions = new ArrayList<>();
        listTransactions.add(new Transaction(operationAdd, "banana", 100));
        listTransactions.add(new Transaction(operationAdd, "banana", 100));
        listTransactions.add(new Transaction(operationSubtract, "banana", 1));
        listTransactions.add(new Transaction(operationAdd, "apple", 10));
        listTransactions.add(new Transaction(operationAdd, "orange", 1));
    }

    @Test
    public void totalFruit_Ok() {
        List<Transaction> totalResult = summaryData.getTotalResult(listTransactions);
        assertEquals(3, totalResult.size());
    }

    @Test
    public void totalQuantity_Ok() {
        List<Transaction> totalResult = summaryData.getTotalResult(listTransactions);
        for (Transaction result : totalResult) {
            if (result.getFruit().equals("banana")) {
                assertEquals(199, result.getQuantity());
            }
        }
    }

    @Test
    public void totalResultEmpty_NotOk() {
        listTransactions.clear();
        listTransactions = readerService.getListTransaction();
        assertTrue(summaryData.getTotalResult(listTransactions).size() > SIZE_ZERO);
    }

    @AfterClass
    public static void afterClass() {
        listTransactions.clear();
    }
}
