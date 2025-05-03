package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopFileReader;
import core.basesyntax.service.ShopFileWriter;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.TransactionStrategy;
import core.basesyntax.service.impl.ShopFileReaderCsvImpl;
import core.basesyntax.service.impl.ShopFileWriterCsvImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.service.impl.TransactionStrategyImpl;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import core.basesyntax.service.strategy.ReturnHandler;
import core.basesyntax.service.strategy.SupplyHandler;
import core.basesyntax.service.strategy.TransactionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShopTest {
    private static ShopFileReader shopFileReader;
    private static ShopFileWriter shopFileWriter;
    private static ShopService shopService;
    private static TransactionParser transactionParser;
    private static TransactionService transactionService;
    private static TransactionStrategy transactionStrategy;
    private static TransactionHandler transactionHandler;

    @Before
    public void setUp() throws Exception {
        shopFileReader = new ShopFileReaderCsvImpl();
        shopFileWriter = new ShopFileWriterCsvImpl();
        shopService = new ShopServiceImpl();
        transactionParser = new TransactionParserImpl();
        Map<FruitTransaction.Operation, TransactionHandler> activitiesHandlerMap = new HashMap<>();
        activitiesHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        transactionStrategy = new TransactionStrategyImpl(activitiesHandlerMap);
        transactionService = new TransactionServiceImpl(transactionStrategy);
    }

    @Test(expected = RuntimeException.class)
    public void shopFileReader_wrongFileName_notOk() {
        shopFileReader.readFromFile("");
    }

    @Test
    public void shopFileReader_readFile_oK() {
        List<String> expected = new ArrayList<>();
        expected.add("lineone");
        expected.add(System.lineSeparator());
        expected.add("linetwo");
        ShopFileReader shopFileReader = new ShopFileReaderCsvImpl();
        List<String> actual = shopFileReader.readFromFile("src/main/resources/read_test.csv");
        Assert.assertTrue(expected.containsAll(actual));
    }

    @Test(expected = RuntimeException.class)
    public void shopFileWriter_wrongName_notOk() {
        shopFileWriter.writeToFile("", "");
    }

    @Test
    public void shopFileWriter_writeFile_oK() {
        shopFileWriter.writeToFile("src/main/resources/write_test.csv",
                "line-one" + System.lineSeparator() + "line-two");
        List<String> expected = new ArrayList<>();
        expected.add("line-one");
        expected.add(System.lineSeparator());
        expected.add("line-two");
        List<String> actual = shopFileReader.readFromFile("src/main/resources/write_test.csv");
        Assert.assertTrue(expected.containsAll(actual));
    }

    @Test
    public void shopService_makeReport_oK() {
        Storage.STORAGE.put("banana", 10);
        Storage.STORAGE.put("apple", 20);
        String actual = shopService.getReport();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,10"
                + System.lineSeparator()
                + "apple,20"
                + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transactionParser_oK() {
        List<String> list = new ArrayList<>();
        list.add("fruit,quantity");
        list.add("b,banana,20");
        FruitTransaction expect = new FruitTransaction();
        expect.setFruit("banana");
        expect.setQuantity(20);
        expect.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> actual = transactionParser.parse(list);
        Assert.assertEquals(expect, actual.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transactionParse_illegalOperation_notOk() {
        List<String> list = new ArrayList<>();
        list.add("fruit,quantity");
        list.add("x,banana,20");
        transactionParser.parse(list);
    }

    @Test
    public void transactionService_oK() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        transactionService.makeTransactions(fruitTransactionList);
        Assert.assertEquals(20, (int) Storage.STORAGE.get("banana"));
    }

    @Test
    public void transactionStrategy_ok() {
        TransactionHandler actual =
                transactionStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        TransactionHandler expect = new BalanceHandler();
        Assert.assertEquals(expect.getClass(), actual.getClass());
    }

    @Test
    public void purcheseHandler_oK() {
        Storage.STORAGE.put("apple", 20);
    }

    @Test
    public void balanceHadler_oK() {
        transactionHandler = new BalanceHandler();
        transactionHandler.transaction("apple",5);
        Assert.assertEquals(5, (int) Storage.STORAGE.get("apple"));
    }

    @Test
    public void purchaseHandler_oK() {
        transactionHandler = new PurchaseHandler();
        Storage.STORAGE.put("banana", 10);
        transactionHandler.transaction("banana",5);
        Assert.assertEquals(5, (int) Storage.STORAGE.get("banana"));
    }

    @Test
    public void returnHandler_oK() {
        transactionHandler = new ReturnHandler();
        Storage.STORAGE.put("apple", 7);
        transactionHandler.transaction("apple",3);
        Assert.assertEquals(10, (int) Storage.STORAGE.get("apple"));
    }

    @Test
    public void supplyHandler_oK() {
        transactionHandler = new SupplyHandler();
        Storage.STORAGE.put("apple", 50);
        transactionHandler.transaction("apple", 50);
        Assert.assertEquals(100, (int) Storage.STORAGE.get("apple"));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_negativeBalance_notOk() {
        transactionHandler = new PurchaseHandler();
        Storage.STORAGE.put("banana", 0);
        transactionHandler.transaction("banana",10);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.STORAGE.clear();
    }
}
