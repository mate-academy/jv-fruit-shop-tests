package service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.BeforeClass;
import service.transaction.BalanceTransactionHandler;
import service.transaction.PurchaseTransactionHandler;
import service.transaction.ReturnTransactionHandler;
import service.transaction.SupplyTransactionHandler;
import service.transaction.TransactionHandler;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    public static final Map<FruitTransaction.Operation,
                    TransactionHandler> transactionHandlerMap = new HashMap<>();
    private static final String PATH = "src" + File.separator
                                    + "test" + File.separator
                                    + "java" + File.separator
                                    + "resources";
    public static final File INPUT_FILE =
            new File(PATH + File.separator + "testInputData.csv");
    public static final File REPORT_FILE =
            new File(PATH + File.separator + "testReportFile.csv");
    public static final File EXPECTED_RESULT_FILE =
            new File(PATH + File.separator + "expectedResultFile.csv");

    @BeforeClass
    public static void beforeClass() {
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        TransactionStrategy transactionStrategy =
                new TransactionStrategyImpl(transactionHandlerMap);
    }
}