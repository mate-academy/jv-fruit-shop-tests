package core.basesyntax;

import core.basesyntax.dao.FruitShopService;
import core.basesyntax.dao.FruitShopServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.StoreOperation;
import core.basesyntax.service.FileService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.StoreOperationStrategy;
import core.basesyntax.strategy.StoreOperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static final String storeFilePath = "src/main/resources/transactions.csv";
    static final String reportFilePath = "src/main/resources/report.csv";

    public static void main(String[] args) {
        // Reading data from file to List
        FileService fileReaderService = new FileServiceImpl();
        List<String> fileData = fileReaderService.readDataFromFile(storeFilePath);
        TransactionParser parser = new TransactionParser();
        final List<FruitTransaction> transactions = parser.parse(fileData);

        // Creating of operation handler Map
        Map<StoreOperation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(StoreOperation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(StoreOperation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(StoreOperation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(StoreOperation.RETURN, new ReturnOperationHandler());

        // Creating an object of StoreOperation Strategy
        StoreOperationStrategy operationStrategy = new StoreOperationStrategyImpl(handlerMap);

        // Processing of data and adding to Storage
        FruitShopService fruitStoreDao = new FruitShopServiceImpl(operationStrategy);
        fruitStoreDao.addDataToStorage(transactions);

        // Creating report list
        ReportService reportService = new ReportServiceImpl();
        String fruitReport = reportService.createReport();

        // Writing report data to csv file
        FileService fileWriterService = new FileServiceImpl();
        fileWriterService.writeDataToFile(reportFilePath, fruitReport);
    }
}
