package core.basesyntax;

import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.CsvReaderImpl;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.service.DataProcessor;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.CsvWriter;
import core.basesyntax.service.impl.DataProcessorImpl;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(final String[] args) {
        HashMap<DataProcessorImpl.OperationType, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(DataProcessorImpl.OperationType.BALANCE, new BalanceOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.SUPPLY, new SupplyOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.PURCHASE, new PurchaseOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.RETURN, new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(strategyMap);

        StorageDao storageDao = new StorageDaoImpl();
        final FileReader csvReader = new CsvReaderImpl();
        final ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl(storageDao);
        final FileWriterService csvWriter = new CsvWriter(reportCreatorService);
        final DataProcessor dataProcessor = new DataProcessorImpl(operationStrategy);
        List<String> parsedDataFromFile = csvReader.parse(
                "./src/main/java/core/basesyntax/resources/input.csv"
        );
        dataProcessor.createFruits(parsedDataFromFile);
        csvWriter.writeToFile("./src/main/java/core/basesyntax/resources/report.csv");
    }
}
