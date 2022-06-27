package basesyntax;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.model.FruitTransaction;
import basesyntax.service.Parser;
import basesyntax.service.Reader;
import basesyntax.service.ReportCreateService;
import basesyntax.service.Writer;
import basesyntax.service.impl.ParserImpl;
import basesyntax.service.impl.ReaderImpl;
import basesyntax.service.impl.ReportCreateServiceImpl;
import basesyntax.service.impl.WriterImpl;
import basesyntax.strategy.BalanceOperationHandlerImpl;
import basesyntax.strategy.OperationHandler;
import basesyntax.strategy.OperationStrategy;
import basesyntax.strategy.OperationStrategyImpl;
import basesyntax.strategy.PurchaseOperationHandlerImpl;
import basesyntax.strategy.ReturnOperationHandlerImpl;
import basesyntax.strategy.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILEPATH_DAILY_RECORDS
            = "src/main/java/resources/DataDuringDay.csv";
    private static final String FILEPATH_DAILY_REPORT
            = "src/main/java/resources/ReportAfterDay.csv";

    public static void main(String[] args) {
        StorageDao storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(storageDao));

        Reader reader = new ReaderImpl();
        List<String> dataFromFile = reader.fileReader(FILEPATH_DAILY_RECORDS);
        Parser parser = new ParserImpl();
        List<FruitTransaction> fruitTransactionList = parser.parseData(dataFromFile);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitTransactionList.forEach(fruitTransaction -> operationStrategy
                .getOperationType(fruitTransaction.getOperation())
                .changeQuantity(fruitTransaction));

        ReportCreateService reportCreateService = new ReportCreateServiceImpl(storageDao);
        Writer writer = new WriterImpl();
        writer.writeToFile(reportCreateService.createReport(), FILEPATH_DAILY_REPORT);
    }
}
