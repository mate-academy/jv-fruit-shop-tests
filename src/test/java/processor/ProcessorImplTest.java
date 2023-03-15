package processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import exception.ReportException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import storage.DataStorage;

public class ProcessorImplTest {
    private final List<String> input = new ArrayList<>();

    @Before
    public void addLegend() {
        input.add("type,fruit,quantity");
    }

    @Test
    public void filter_legendLine_ok() {
        input.add("b,banana,20");
        input.add("p,banana,5");
        new ProcessorImpl(input).makeTransactions();
        assertEquals("Method must ignore first legend line",
                "b,banana,20", input.get(0));
    }

    @Test
    public void makeTransaction_noLegendLine_ok() {
        input.clear();
        input.add("b,banana,20");
        Integer expectedAmount = 20;
        new ProcessorImpl(input).makeTransactions();
        Integer actualAmount = DataStorage.FRUIT_MAP.get("banana");
        assertEquals("Should set correct balance when no legend line present",
                expectedAmount, actualAmount);
    }

    @Test(expected = ReportException.class)
    public void makeTransaction_invalidOperation_notOk() {
        input.add("d,banana,5");
        new ProcessorImpl(input).makeTransactions();
        fail("Expected " + ReportException.class.getName()
                + " to be thrown for invalidOperation \"d\"");
    }

    @Test(expected = ReportException.class)
    public void makeTransaction_invalidLine_notOk() {
        input.add("b,^_^,30");
        new ProcessorImpl(input).makeTransactions();
        fail("Expected " + ReportException.class.getName()
                + " to be thrown for invalid line \"b,^_^,30\"");
    }

    @Test(expected = ReportException.class)
    public void makeTransaction_emptyFruit_notOk() {
        input.add("b,,30");
        new ProcessorImpl(input).makeTransactions();
        fail("Expected " + ReportException.class.getName()
                + " to be thrown for invalid line \"b,,30\"");
    }

    @Test(expected = ReportException.class)
    public void makeTransaction_emptyOperation_notOk() {
        input.add(",banana,30");
        new ProcessorImpl(input).makeTransactions();
        fail("Expected " + ReportException.class.getName()
                + " to be thrown for invalid line \",banana,30\"");
    }

    @Test(expected = ReportException.class)
    public void makeTransaction_emptyAmount_notOk() {
        input.add("b,banana,");
        new ProcessorImpl(input).makeTransactions();
        fail("Expected " + ReportException.class.getName()
                + " to be thrown for invalid line \"b,banana,\"");
    }

    @Test
    public void makeTransaction_single_ok() {
        input.add("b,banana,20");
        Integer expectedAmount = 20;
        new ProcessorImpl(input).makeTransactions();
        Integer actualAmount = DataStorage.FRUIT_MAP.get("banana");
        assertEquals("Should set banana balance to 20 for input b,banana,20",
                expectedAmount, actualAmount);
    }

    @Test
    public void makeTransaction_inSequence_ok() {
        input.add("b,banana,20");
        input.add("p,banana,5");
        input.add("s,banana,5");
        input.add("r,banana,5");
        Integer expectedAmount = 25;
        new ProcessorImpl(input).makeTransactions();
        Integer actualAmount = DataStorage.FRUIT_MAP.get("banana");
        assertEquals("Should have fruit amount 25 after: b=20, p=5, s=5, r=5",
                expectedAmount, actualAmount);
    }
}
