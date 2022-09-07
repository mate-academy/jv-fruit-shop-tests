package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvReportService;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ParseDataService;
import core.basesyntax.service.impl.CsvReportServiceImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ParseDataServiceImpl;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String INPUT_FILE = "src/main/java/core/basesyntax/resources/input.csv";
    public static final String OUT_PUT_FILE = "src/main/java/core/basesyntax/resources/report.csv";

    public static void main(String[] args) {
        File result = new File(OUT_PUT_FILE);
        result.delete();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap
                .put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap
                .put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap
                .put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap
                .put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> listFromInputFile = fileReaderService.readFromFile(INPUT_FILE);
        ParseDataService parseDataService = new ParseDataServiceImpl();
        List<FruitTransaction> fruitTransactions = parseDataService.parseData(listFromInputFile);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitShopService.process(fruitTransactions);
        CsvReportService reportService = new CsvReportServiceImpl();
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeToFile(OUT_PUT_FILE, reportService.createReport(Storage.storage));
    }
}
