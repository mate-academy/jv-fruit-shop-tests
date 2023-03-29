package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ProcessTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ProcessTransactionImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.service.impl.transactions.BalanceHandlerImpl;
import core.basesyntax.service.impl.transactions.PurchaseHandlerImpl;
import core.basesyntax.service.impl.transactions.ReturnHandlerImpl;
import core.basesyntax.service.impl.transactions.SupplyHandlerImpl;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Feel free to remove this class and create your own.
 */
public class FruitShopTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            new HashMap<>();
    private static final String TEMP_PATH = "src/main/resources/input.csv";
    private static Storage storage;
    private static StorageDao storageDao;
    private static List<String> listData;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
    }

    @Test
    public void get_checkBalanceHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.BALANCE;
        OperationHandler balanceHandler = new BalanceHandlerImpl();
        balanceHandler.getBalance(0, 20);
        int expected = 20;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap)
                .get(key).getBalance(0, 20));
    }

    @Test
    public void get_checkPurchaseHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.PURCHASE;
        OperationHandler purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.getBalance(50, 20);
        int expected = 30;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap)
                .get(key).getBalance(50, 20));
    }

    @Test
    public void get_purchaseHandlerThrowRuntimeException_NotOk() {
        FruitTransaction.Operation key = FruitTransaction.Operation.PURCHASE;
        OperationHandler purchaseHandler = new PurchaseHandlerImpl();
        int balance = 10 - 20;
        exception.expect(RuntimeException.class);
        exception.expectMessage("Quantity of purchase can't be bigger then balance in storage: "
                + "storage - "
                + balance + " purchase - "
                + 20);
        new TransactionStrategyImpl(operationHandlerMap).get(key).getBalance(10, 20);
    }

    @Test
    public void get_checkReturnHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.RETURN;
        OperationHandler returnHandler = new ReturnHandlerImpl();
        returnHandler.getBalance(20, 10);
        int expected = 30;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap)
                .get(key).getBalance(20, 10));
    }

    @Test
    public void get_checkSupplyHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.SUPPLY;
        OperationHandler supplyHandler = new SupplyHandlerImpl();
        supplyHandler.getBalance(30, 10);
        int expected = 40;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap)
                .get(key).getBalance(30, 10));
    }

    @Test
    public void getDataFromLine_isRightListOfFruitTransaction_Ok() {
        listData = new ArrayList<>();
        listData.add("b,banana,20");
        listData.add("b,apple,30");
        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> fruitTransactionList = transactionParser.getDataFromLine(listData);
        Assert.assertEquals("banana", fruitTransactionList.get(0).getFruit().getName());
        Assert.assertEquals("apple", fruitTransactionList.get(1).getFruit().getName());
        Assert.assertEquals(20, fruitTransactionList.get(0).getQuantity());
        Assert.assertEquals(30, fruitTransactionList.get(1).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactionList.get(0)
                .getOperation());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactionList.get(1)
                .getOperation());
    }

    @Test
    public void readFromFile_readFileFromPath_Ok() throws IOException {
        FileReader fileReader = new FileReaderImpl();
        File file = new File(TEMP_PATH);
        BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(file));
        bufferedWriter.write("b,banana,50");
        bufferedWriter.newLine();
        bufferedWriter.write("p,banana,30");
        bufferedWriter.close();
        List<String> actual = fileReader.readFromFile(file.getPath());
        Assert.assertEquals(2, actual.size());
        file.delete();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_fileIsAbsent_NotOk() {
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.readFromFile(TEMP_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void writeIntoFile_fileIsAbsent_NotOk() {
        FileWriter fileWriter = new FileWriterImpl();
        listData = new ArrayList<>();
        listData.add("s,banana,30");
        String path = "src/resources/input.csv";
        fileWriter.writeIntoFile(listData, path);
    }

    @Test
    public void writeIntoFile_addDataInFile_Ok() throws IOException {
        FileWriter fileWriter = new FileWriterImpl();
        String expected = "s,banana,30";
        listData = new ArrayList<>();
        listData.add(expected);
        File file = new File(TEMP_PATH);
        fileWriter.writeIntoFile(listData, file.getPath());
        BufferedReader reader = new BufferedReader(new java.io.FileReader(file.getPath()));
        Assert.assertEquals(expected, reader.readLine());
        reader.close();
        file.delete();
    }

    @Test
    public void addDataIntoStorage_moveDataIntoStorage_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        Fruit fruit = new Fruit("apple");
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(20);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        ProcessTransaction processTransaction = new ProcessTransactionImpl(storageDao,
                new TransactionStrategyImpl(operationHandlerMap));
        processTransaction.addDataIntoStorage(fruitTransactionList);
        Integer actual = storageDao.getQuantity(fruit);
        Assert.assertEquals(20, (int) actual);
        Assert.assertTrue(Storage.getStorage().containsKey(fruit));
    }

    @Test
    public void generateReport_getReport_0k() {
        Fruit fruit = new Fruit("apple");
        storageDao.add(fruit, 50);
        String expected = "apple,50";
        List<String> list = new ReportGeneratorImpl().generateReport(storageDao);
        Assert.assertEquals(storageDao.size(), list.size());
        Assert.assertEquals(expected, list.get(0));
    }

    @Test
    public void equals_fruitIsNull_NotOk() {
        Fruit fruit0 = new Fruit("apple");
        Fruit fruit1 = null;
        Assert.assertNotEquals(fruit0, fruit1);
    }
}
