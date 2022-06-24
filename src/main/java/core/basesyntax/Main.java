package core.basesyntax;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CreateReport;
import core.basesyntax.service.DataHandler;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.CreateReportImpl;
import core.basesyntax.service.impl.DataHandlerImpl;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.strategy.FruitHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String OPERATION_PATH = "src/main/resources/operation.csv";
    private static final String REPORT_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FruitsDao fruitsDao = new FruitsDaoImpl();
        Map<FruitTransaction.Operation, FruitHandler> fruitHandlerMap = new HashMap<>();
        fruitHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitsDao));
        fruitHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitsDao));
        fruitHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitsDao));
        fruitHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitsDao));

        FileReaderService fileReaderService = new FileReaderImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(fruitHandlerMap);
        DataHandler dataHandler = new DataHandlerImpl();
        List<FruitTransaction> list =
                dataHandler.handleData(fileReaderService.readTheFruitsStorage(OPERATION_PATH));
        for (FruitTransaction fruitTransaction : list) {
            operationStrategy.get(fruitTransaction.getOperation())
                    .handleOperation(fruitTransaction);
        }
        CreateReport reportCreator = new CreateReportImpl();
        dataHandler.handleData(fileReaderService.readTheFruitsStorage(OPERATION_PATH));

        List<String> listOfFruits = reportCreator.createReport(fruitsDao.getCurrentFruitAmount());
        FileWriterService fileWriterService = new FileWriterImpl();
        fileWriterService.writeToFile(REPORT_PATH, listOfFruits);
    }
}
