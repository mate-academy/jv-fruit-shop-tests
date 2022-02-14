package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReaderServiceImpl;
import core.basesyntax.service.ReportServiceImpl;
import core.basesyntax.service.WriteServiceImpl;
import core.basesyntax.strategy.BalanceTransaction;
import core.basesyntax.strategy.PurchaseTransaction;
import core.basesyntax.strategy.SupplyTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FruitShopTest {
    private final ReaderServiceImpl readerService = new ReaderServiceImpl();
    private final WriteServiceImpl writeService = new WriteServiceImpl();
    private final ReportServiceImpl report = new ReportServiceImpl();
    private final BalanceTransaction balanceTransaction = new BalanceTransaction();
    private final PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
    private final SupplyTransaction supplyTransaction = new SupplyTransaction();
    private final String inputFileName = "src/test/resources/input.csv";
    private final String outputFileName = "src/test/resources/output.csv";

    @Test(expected = RuntimeException.class)
    public void readFromFileNotOk() {
        String fileName = "Test";
        readerService.readFromFile(fileName);
    }

    @Test
    public void readFromFileOk() {
        List<String> expected = Arrays.asList("10", "20", "30");
        List<String> actual = readerService.readFromFile(inputFileName);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_NotOk() {
        String text = "Test Test Test";
        writeService.writeFile("", text);
    }

    @Test
    public void writeFile_Ok() {
        String report = "1\n2\n3\n4\n5";
        List<String> expected = Arrays.asList("1", "2", "3", "4", "5");
        writeService.writeFile(outputFileName, report);
        List<String> actual = readerService.readFromFile(outputFileName);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void report_NotOk() {
        List<String> wrongDataList = new ArrayList<>();
        wrongDataList.add("Test");
        wrongDataList.add("apple,banana,10");
        report.report(wrongDataList);
    }

    @Test
    public void report_Ok() {
        List<String> correct = new ArrayList<>();
        correct.add("type,fruit,quantity");
        correct.add("b,banana,1");
        correct.add("b,apple,2");
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,1"
                + System.lineSeparator()
                + "apple,2";
        String actual = report.report(correct);
        assertEquals(expected, actual);
    }

    @Test
    public void balanceTransaction_NotOk() {
        Integer expected = 5;
        balanceTransaction.operation("banana", 20);
        Integer actual = Storage.fruitMap.get("banana");
        assertNotEquals(expected, actual);
    }

    @Test
    public void balanceTransaction_Ok() {
        Integer expected = 10;
        balanceTransaction.operation("apple", 10);
        Integer actual = Storage.fruitMap.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void purchaseTransaction_NotOk() {
        Integer expected = 5;
        balanceTransaction.operation("banana", 20);
        purchaseTransaction.operation("banana", 10);
        Integer actual = Storage.fruitMap.get("banana");
        assertNotEquals(expected, actual);
    }

    @Test
    public void purchaseTransaction_Ok() {
        Integer expected = 5;
        balanceTransaction.operation("apple", 10);
        purchaseTransaction.operation("apple", 5);
        Integer actual = Storage.fruitMap.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void supplyTransaction_NotOk() {
        Integer expected = 8;
        balanceTransaction.operation("banana", 20);
        supplyTransaction.operation("banana", 10);
        Integer actual = Storage.fruitMap.get("banana");
        assertNotEquals(expected, actual);
    }

    @Test
    public void supplyTransaction_Ok() {
        Integer expected = 15;
        balanceTransaction.operation("apple", 10);
        supplyTransaction.operation("apple", 5);
        Integer actual = Storage.fruitMap.get("apple");
        assertEquals(expected, actual);
    }
}
