package core.basesyntax.processingdata;

import core.basesyntax.database.TransactionServiceImpl;
import core.basesyntax.dataservice.FileReaderService;
import core.basesyntax.dataservice.FileReaderServiceImpl;
import core.basesyntax.dataservice.FileWriterService;
import core.basesyntax.dataservice.FileWriterServiceImpl;
import core.basesyntax.dataservice.ReportCreator;
import core.basesyntax.dataservice.ReportCreatorImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TransactionService;
import core.basesyntax.strategy.BalanceActivity;
import core.basesyntax.strategy.PurchaseActivity;
import core.basesyntax.strategy.SupplyActivity;
import core.basesyntax.strategy.TypeOfActivity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainShop {
    private static final String readFromFileName = "src/test/resources/dayFile.csv";
    private static final String writeToFileName = "src/test/resources/fileToreport.csv";
    private static final Map<String, TypeOfActivity> operationHandlerMap = new HashMap<>();
    private static final FileReaderService readFromFile = new FileReaderServiceImpl();
    private static final FileWriterService writeToFile = new FileWriterServiceImpl();
    private static final TransactionService transactionService = new TransactionServiceImpl();
    private static final ReportCreator withReport = new ReportCreatorImpl();

    public static void main(String[] args) {
        putOperationInMap();
        List<String> fromFileList = readFromFile.getDataFromFile(readFromFileName);
        List<FruitTransaction> fruitTransactionList
                = transactionService.transactionData(fromFileList);
        for (FruitTransaction fruitTransact : fruitTransactionList) {
            String activity = fruitTransact.getOperation();
            TypeOfActivity typeActivity = operationHandlerMap.get(activity);
            typeActivity.realizeType(fruitTransact);
        }
        writeToFile.writeDataToFile(writeToFileName,withReport.createReport());
    }

    public static void putOperationInMap() {
        operationHandlerMap.put("p", new PurchaseActivity());
        operationHandlerMap.put("b", new BalanceActivity());
        operationHandlerMap.put("s", new SupplyActivity());
        operationHandlerMap.put("r", new SupplyActivity());
    }
}
