package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.service.impl.transactions.BalanceHandlerImpl;
import core.basesyntax.service.impl.transactions.PurchaseHandlerImpl;
import core.basesyntax.service.impl.transactions.ReturnHandlerImpl;
import core.basesyntax.service.impl.transactions.SupplyHandlerImpl;
import core.basesyntax.strategy.TransactionStrategyImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Feel free to remove this class and create your own.
 */
public class FruitShopTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
    private static final String READ_PATH_TO_FILE = "src/main/resources/inputdata.csv";
    private static final String WRITE_PATH_TO_FILE = "src/main/resources/database.csv";
    private static List<String> listData;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeAll() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
    }

    @Test
    public void get_checkBalanceHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.BALANCE;
        OperationHandler balanceHandler = new BalanceHandlerImpl();
        balanceHandler.getBalance(0 , 20);
        int expected = 20;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap).get(key).getBalance(0, 20));
    }

    @Test
    public void get_checkPurchaseHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.PURCHASE;
        OperationHandler purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.getBalance(50 , 20);
        int expected = 30;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap).get(key).getBalance(50, 20));
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
        returnHandler.getBalance(20 , 10);
        int expected = 30;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap).get(key).getBalance(20, 10));
    }

    @Test
    public void get_checkSupplyHandler_Ok() {
        FruitTransaction.Operation key = FruitTransaction.Operation.SUPPLY;
        OperationHandler supplyHandler = new SupplyHandlerImpl();
        supplyHandler.getBalance(30 , 10);
        int expected = 40;
        Assert.assertEquals(expected, new TransactionStrategyImpl(operationHandlerMap).get(key).getBalance(30, 10));
    }

    @Test
    public void getDataFromLine_isRightListOfFruitTransaction_Ok() {
        TransactionParser transactionParser = new TransactionParserImpl();
        listData = new ArrayList<>();
        listData.add("b,banana,20");
        listData.add("b,apple,30");
        List<FruitTransaction> fruitTransactionList = transactionParser.getDataFromLine(listData);
        Assert.assertEquals("banana", fruitTransactionList.get(0).getFruit().getName());
        Assert.assertEquals("apple", fruitTransactionList.get(1).getFruit().getName());
        Assert.assertEquals(20, fruitTransactionList.get(0).getQuantity());
        Assert.assertEquals(30, fruitTransactionList.get(1).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactionList.get(0).getOperation());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactionList.get(1).getOperation());
    }

//    @Test
//    public void readFromFile_readFileFromPath_Ok() throws IOException {
//        FileReader fileReader = new FileReaderImpl();
//        File file = Files.createTempFile("test", ".csv", new File("src/main/resources/"));
//        BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(file));
//        bufferedWriter.write("b,banana,50");
//        bufferedWriter.newLine();
//        bufferedWriter.write("p,banana,30");
//        List<String> actual = fileReader.readFromFile(file.getPath());
//        Assert.assertEquals(2, actual.size());
//    }
}
