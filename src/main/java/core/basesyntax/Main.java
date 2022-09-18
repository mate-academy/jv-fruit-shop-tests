package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CreatorReportService;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitTransactionParserService;
import core.basesyntax.service.impl.CreatorReportServiceImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitTransactionParserServiceImpl;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.operations.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.operations.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.operations.impl.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILEPATH = "src/main/java/core/resources/Input.csv";
    private static final String REPORT_FILEPATH = "src/main/java/core/resources/Report.csv";

    public static void main(String[] args) {
        StorageDao storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(storageDao));

        FileReaderService fileReader = new FileReaderServiceImpl();
        List<String> dateFromFile = fileReader.readFromFile(INPUT_FILEPATH);
        FruitTransactionParserService parserService = new FruitTransactionParserServiceImpl();
        List<FruitTransaction> fruitTransactionList = parserService.parse(dateFromFile);
        Strategy operationStrategy = new StrategyImpl(operationHandlerMap);
        fruitTransactionList.forEach(f -> operationStrategy
                .get(f.getOperation())
                .handler(f));
        CreatorReportService creatorReportService = new CreatorReportServiceImpl(storageDao);
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeToFile(creatorReportService.createReport(), REPORT_FILEPATH);
    }
}
