package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.transaction.BalanceTransactionHandler;
import service.transaction.PurchaseTransactionHandler;
import service.transaction.ReturnTransactionHandler;
import service.transaction.SupplyTransactionHandler;
import service.transaction.TransactionHandler;

public class ReportServiceImplTest {
    private static final Map<FruitTransaction.Operation,
            TransactionHandler> TRANSACTION_HANDLER_MAP = new HashMap<>();
    private static ReportService reportService;
    private static TransactionStrategy transactionStrategy;

    @BeforeClass
    public static void beforeClass() {
        TRANSACTION_HANDLER_MAP.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        TRANSACTION_HANDLER_MAP.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        TRANSACTION_HANDLER_MAP.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        TRANSACTION_HANDLER_MAP.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        transactionStrategy = new TransactionStrategyImpl(TRANSACTION_HANDLER_MAP);
    }

    @Test
    public void createReport_nullTransactionStrategy_Ok() {
        reportService = new ReportServiceImpl(null);
        List<FruitTransaction> fruitTransactions = initCorrectFruitTransactionList();
        List<String> expectedReport = new ArrayList<>();
        List<String> actualReport = reportService.createReport(fruitTransactions);
        Assert.assertEquals("If field transactionStrategy null,"
                + "should return empty list", expectedReport, actualReport);
    }

    @Test
    public void createReport_nullFruitTransactionList_Ok() {
        reportService = new ReportServiceImpl(transactionStrategy);
        List<String> expectedReport = new ArrayList<>();
        List<String> actualReport = reportService.createReport(null);
        Assert.assertEquals("If null transfered instead of transactions,"
                + "should return empty list", expectedReport, actualReport);
    }

    @Test
    public void createReport_emptyFruitTransactionList_Ok() {
        reportService = new ReportServiceImpl(transactionStrategy);
        List<FruitTransaction> emptyList = new ArrayList<>();
        List<String> expectedReport = new ArrayList<>();
        List<String> actualReport = reportService.createReport(emptyList);
        Assert.assertEquals("If empty list transfered,"
                + "should return empty list", expectedReport, actualReport);
    }

    @Test
    public void createReport_incorrectFruitTransactionList_NotOk() {
        reportService = new ReportServiceImpl(transactionStrategy);
        try {
            reportService.createReport(initIncorrectFruitTransactionList());
        } catch (NoSuchElementException e) {
            return;
        }
        Assert.assertEquals("If transactions incorrect,"
                + "should throw NoSuchElementException.", true, false);
    }

    @Test
    public void createReport_correctFruitTransactionList_Ok() {
        reportService = new ReportServiceImpl(transactionStrategy);
        List<FruitTransaction> fruitTransactions = initCorrectFruitTransactionList();
        List<String> expectedReport = List.of("banana,150", "apple,110");
        List<String> actualReport = reportService.createReport(fruitTransactions);
        Assert.assertEquals("For transferred FruitTransaction list,"
                + "result should be different.", expectedReport, actualReport);
    }

    private List<FruitTransaction> initCorrectFruitTransactionList() {
        FruitTransaction balanceBananaTransaction =
                new FruitTransaction("b", "banana", 100);
        FruitTransaction balanceAppleTransaction =
                new FruitTransaction("b", "apple", 70);
        FruitTransaction supplyBananaTransaction =
                new FruitTransaction("s", "banana", 50);
        FruitTransaction supplyAppleTransaction =
                new FruitTransaction("s", "apple", 60);
        FruitTransaction purchaseBananaTransaction =
                new FruitTransaction("p", "banana", 80);
        FruitTransaction purchaseAppleTransaction =
                new FruitTransaction("p", "apple", 30);
        FruitTransaction returnBananaTransaction =
                new FruitTransaction("r", "banana", 80);
        FruitTransaction returnAppleTransaction =
                new FruitTransaction("r", "apple", 10);
        return List.of(balanceBananaTransaction, balanceAppleTransaction,
                        supplyBananaTransaction, supplyAppleTransaction,
                        purchaseBananaTransaction, purchaseAppleTransaction,
                        returnBananaTransaction, returnAppleTransaction);
    }

    private List<FruitTransaction> initIncorrectFruitTransactionList() {
        FruitTransaction firstIncorrectTransaction =
                new FruitTransaction("q", "lemon", 30);
        FruitTransaction secondIncorrectTransaction =
                new FruitTransaction("m", "plum", 25);
        FruitTransaction thirdIncorrectTransaction =
                new FruitTransaction("c", "carrot", 13);
        return List.of(firstIncorrectTransaction,
                        secondIncorrectTransaction,
                        thirdIncorrectTransaction);
    }
}
