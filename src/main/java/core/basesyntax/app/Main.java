package core.basesyntax.app;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.CsvFileReportService;
import core.basesyntax.service.CsvFileWriterService;
import core.basesyntax.service.FruitTransactionHandler;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.CsvFileReportServiceImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.service.impl.FruitTransactionHandlerImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceImpl;
import core.basesyntax.strategy.impl.PurchaseImpl;
import core.basesyntax.strategy.impl.ReturnImpl;
import core.basesyntax.strategy.impl.SupplyImpl;
import core.basesyntax.testservice.ConfigReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static ConfigReader configReader;

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationHandler = new HashMap<>();
        operationHandler.put(Operation.BALANCE, new BalanceImpl());
        operationHandler.put(Operation.PURCHASE, new PurchaseImpl());
        operationHandler.put(Operation.SUPPLY, new SupplyImpl());
        operationHandler.put(Operation.RETURN, new ReturnImpl());
        OperationStrategy operationStrategy = new OperationStrategy(operationHandler);

        CsvFileReaderService fileReader = new CsvFileReaderServiceImpl();
        configReader = new ConfigReader();
        List<String> fileLines =
                fileReader.readDataFromFile(configReader.getValueByKey("file.read.from"));

        FruitTransactionParser fruitTransactionParser = new FruitTransactionParserImpl();
        List<FruitTransaction> fruitTransactions =
                fruitTransactionParser.parseTransactions(fileLines);

        FruitTransactionHandler fruitTransactionHandler =
                new FruitTransactionHandlerImpl(operationStrategy);
        fruitTransactionHandler.handleFruitTransactions(fruitTransactions);

        FruitDao fruitDao = new FruitDaoImpl();
        CsvFileReportService csvFileReportService = new CsvFileReportServiceImpl();
        String report = csvFileReportService.createReport(fruitDao.getAllFruits());

        CsvFileWriterService csvFileWriterService = new CsvFileWriterServiceImpl();
        csvFileWriterService.writeDataToFile(configReader.getValueByKey("file.write.to"), report);
    }
}
