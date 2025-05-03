package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SummaryDataTest {
    private static Operation operationAdd;
    private static Operation operationSubtract;
    private static List<Transaction> listTransactions;
    private static SummaryData summaryData;

    @Before
    public void setUp() {
        operationAdd = new Operation("BALANCE", "b", Operation.ArithmeticOperation.ADD);
        operationSubtract = new Operation("PURCHASE", "p", Operation.ArithmeticOperation.SUBTRACT);
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
    public void totalResult_Ok() {
        List<Transaction> listOneLine = new ArrayList<>();
        listOneLine.add(new Transaction(operationAdd, "banana", 100));
        assertEquals(1, summaryData.getTotalResult(listOneLine).size());
    }

    @AfterClass
    public static void afterClass() {
        listTransactions.clear();
    }
}
