package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.service.DataProcessor;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.CsvReaderImpl;
import core.basesyntax.service.impl.CsvWriterImpl;
import core.basesyntax.service.impl.DataProcessorImpl;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.MinusOperationHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PlusOperationHandler;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(final String[] args) {
        HashMap<DataProcessorImpl.OperationType, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(DataProcessorImpl.OperationType.BALANCE, new PlusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.SUPPLY, new PlusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.PURCHASE, new MinusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.RETURN, new PlusOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(strategyMap);

        StorageDao storageDao = new StorageDaoImpl();
        final FileReaderService csvReader = new CsvReaderImpl();
        final ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl(storageDao);
        final FileWriterService csvWriter = new CsvWriterImpl(reportCreatorService);
        final DataProcessor dataProcessor = new DataProcessorImpl(operationStrategy);
        List<String> parsedDataFromFile = csvReader.parse(
                "./src/main/java/core/basesyntax/resources/input.csv"
        );
        dataProcessor.createFruits(parsedDataFromFile);
        csvWriter.writeToFile("./src/main/java/core/basesyntax/resources/report.csv");
    }
}
