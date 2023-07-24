package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CustomFileReader;
import core.basesyntax.service.CustomFileWriter;
import core.basesyntax.service.OperationProcess;
import core.basesyntax.service.ReadParser;
import core.basesyntax.service.WriteParser;
import core.basesyntax.service.impl.CsvFileReader;
import core.basesyntax.service.impl.CsvFileWriter;
import core.basesyntax.service.impl.OperationProcessImpl;
import core.basesyntax.service.impl.ReadParserImpl;
import core.basesyntax.service.impl.WriteParserImpl;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitShopTest {
    private BalanceHandler balanceHandler;
    private OperationProcess operationProcess;
    private PurchaseHandler purchaseHandler;
    private ReturnHandler returnHandler;
    private ReadParser readParser;
    private SupplyHandler supplyHandler;
    private WriteParser writeParser;

    @Before
    public void setUp() {
        balanceHandler = new BalanceHandler();
        operationProcess = new OperationProcessImpl();
        purchaseHandler = new PurchaseHandler();
        returnHandler = new ReturnHandler();
        readParser = new ReadParserImpl();
        supplyHandler = new SupplyHandler();
        writeParser = new WriteParserImpl();
        Storage.storageMap.clear();
    }

    @Test
    public void readParse_ValidData_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,apple,10");
        lines.add("p,banana,5");
        lines.add("s,orange,20");
        List<FruitTransaction> transactions = readParser.parse(lines);
        assertNotNull(transactions);
        assertEquals(3, transactions.size());
        FruitTransaction transaction1 = transactions.get(0);
        assertEquals(Operation.BALANCE, transaction1.getOperation());
        assertEquals("apple", transaction1.getFruit());
        assertEquals(10, transaction1.getQuantity());
        FruitTransaction transaction2 = transactions.get(1);
        assertEquals(Operation.PURCHASE, transaction2.getOperation());
        assertEquals("banana", transaction2.getFruit());
        assertEquals(5, transaction2.getQuantity());
        FruitTransaction transaction3 = transactions.get(2);
        assertEquals(Operation.SUPPLY, transaction3.getOperation());
        assertEquals("orange", transaction3.getFruit());
        assertEquals(20, transaction3.getQuantity());
    }

    @Test
    public void read_ValidFile_Ok() {
        CustomFileReader fileReader = new CsvFileReader();
        List<String> lines = fileReader.read("src/test/resources/valid_input.csv");
        assertNotNull(lines);
        assertEquals(4, lines.size());
    }

    @Test
    public void read_NonexistentFile_ExceptionThrown() {
        CustomFileReader fileReader = new CsvFileReader();
        assertThrows(RuntimeException.class, () -> fileReader.read("nonexistent_file.csv"));
    }

    @Test
    public void write_ValidData_Ok() {
        CustomFileWriter fileWriter = new CsvFileWriter();
        String report = "fruit,quantity\napple,5\nbanana,10\n";
        fileWriter.write(report, "src/test/resources/test_output.csv");
    }

    @Test
    public void processData_ValidTransactions_Ok() {
        Storage.storageMap.put("apple", 10);
        Storage.storageMap.put("banana", 20);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.PURCHASE, "apple", 5));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 10));
        transactions.add(new FruitTransaction(Operation.RETURN, "apple", 2));
        operationProcess.processData(transactions);
        assertEquals(7, (int) Storage.storageMap.get("apple"));
        assertEquals(30, (int) Storage.storageMap.get("banana"));
    }

    @Test
    public void purchase_ValidData_Ok() {
        Storage.storageMap.put("apple", 10);
        purchaseHandler.doOperation("apple", 5);
        assertEquals(5, (int) Storage.storageMap.get("apple"));
    }

    @Test
    public void balance_ValidData_Ok() {
        balanceHandler.doOperation("apple", 10);
        assertTrue(Storage.storageMap.containsKey("apple"));
        assertEquals(10, (int) Storage.storageMap.get("apple"));
    }

    @Test
    public void purchase_NegativeBalance_ExceptionThrown() {
        Storage.storageMap.put("orange", 5);
        assertThrows(RuntimeException.class, () -> purchaseHandler.doOperation("orange", 10));
    }

    @Test
    public void return_ValidData_Ok() {
        Storage.storageMap.put("grape", 8);
        returnHandler.doOperation("grape", 2);
        assertEquals(10, (int) Storage.storageMap.get("grape"));
    }

    @Test
    public void supply_ValidData_Ok() {
        Storage.storageMap.put("apple", 10);
        supplyHandler.doOperation("apple", 5);
        assertEquals(15, (int) Storage.storageMap.get("apple"));
    }

    @Test
    public void writeParse_ValidData_Ok() {
        Storage.storageMap.put("apple", 5);
        Storage.storageMap.put("banana", 10);
        String report = writeParser.parse();
        String expectedReport = "fruit,quantity\napple,5\nbanana,10\n";
        assertEquals(expectedReport, report);
    }
}
