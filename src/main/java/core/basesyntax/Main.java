package core.basesyntax;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ActivityStrategy;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.activity.ActivityHandler;
import core.basesyntax.service.activity.BalanceActivityHandler;
import core.basesyntax.service.activity.PurchaseActivityHandler;
import core.basesyntax.service.activity.ReturnActivityHandler;
import core.basesyntax.service.activity.SupplyActivityHandler;
import core.basesyntax.service.impl.ActivityStrategyImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Paths to files
        final String dataPath = "src/main/resources/database.Csv";
        final String reportPath = "src/main/resources/report.Csv";
        // Map for ActivityStrategy
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        Map<FruitTransaction.Operation, ActivityHandler> operationActivityHandlerMap =
                new HashMap<>();
        operationActivityHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceActivityHandler(fruitStorageDao));
        operationActivityHandlerMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseActivityHandler(fruitStorageDao));
        operationActivityHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyActivityHandler(fruitStorageDao));
        operationActivityHandlerMap.put(
                FruitTransaction.Operation.RETURN, new ReturnActivityHandler(fruitStorageDao));
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(operationActivityHandlerMap);
        ReaderService readerService = new ReaderServiceImpl();
        FruitTransactionParser fruitTransactionParser = new FruitTransactionParserImpl();
        ShopService shopService = new ShopServiceImpl(activityStrategy);
        ReportService reportService = new ReportServiceImpl();
        WriterService writerService = new WriterServiceImpl();
        List<String> dataFromFile = readerService.read(dataPath);
        List<FruitTransaction> transactions = fruitTransactionParser.parse(dataFromFile);
        shopService.processTransactions(transactions);
        String report = reportService.createReport(fruitStorageDao.getAll());
        writerService.write(report, reportPath);
    }
}
