package service;

import dao.DatabaseDao;
import dao.DatabaseDaoImpl;
import db.Database;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.FruitServiceImpl;
import strategy.OperationHandler;
import strategy.StrategyService;
import strategy.impl.BalanceOperationImpl;
import strategy.impl.PurchaseOperationImpl;
import strategy.impl.ReturnOperationImpl;
import strategy.impl.StrategyServiceImpl;
import strategy.impl.SupplyOperationImpl;

public class FruitServiceTest {
    private static FruitService fruitService;
    private static String expected;
    private static final String OUTPUT_FILE = "src/test/resources/after.csv";
    private static final String INPUT_FILE = "src/test/resources/before.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl());
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl());
        StrategyService strategyService = new StrategyServiceImpl(strategyMap);
        fruitService = new FruitServiceImpl(strategyService);
        expected = "fruit,quantity"
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
    }

    @Test
    public void fruitService_ProcessData_OK() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        fruitService.processData(INPUT_FILE);
        DatabaseDao dao = new DatabaseDaoImpl();
        Map<String, Integer> actual = dao.getAllFruits();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fruitService_CreateReport_OK() {
        Database.database.put("banana", 152);
        Database.database.put("apple", 90);
        String actual = fruitService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fruitService_SaveReport_OK() {
        Database.database.put("banana", 152);
        Database.database.put("apple", 90);
        fruitService.saveReport(OUTPUT_FILE);
        String actual = read();
        Assert.assertEquals(actual, expected);
    }

    private String read() {
        StringBuilder readData = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                readData.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("File " + OUTPUT_FILE + " not found", e);
        }
        return readData.toString().trim();
    }

    @AfterClass
    public static void afterClass() {
        Database.database.clear();
    }
}
