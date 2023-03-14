package service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import service.transaction.TransactionHandler;

public class FruitShopServiceImplTest {
    public static final Map<FruitTransaction.Operation,
                TransactionHandler> transactionHandlerMap = new HashMap<>();
    private static final String PATH = "src" + File.separator
                                    + "test" + File.separator
                                    + "resources";
    public static final File INPUT_FILE =
            new File(PATH + File.separator + "testInputData.csv");
    public static final File REPORT_FILE =
            new File(PATH + File.separator + "testReportFile.csv");
    public static final File EXPECTED_RESULT_FILE =
            new File(PATH + File.separator + "expectedResultFile.csv");

}