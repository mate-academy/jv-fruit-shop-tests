package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ParserServiceCsvImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_FROM_FILE = "src/main/resources/inputData.csv";
    private static final String PATH_TO_FILE = "src/main/resources/fruitReport.csv";

    public static void main(String[] args) {
        FruitDao fruitDao = new FruitDaoImpl();
        ReaderServiceImpl readerService = new ReaderServiceImpl();
        List<String> data = readerService.readFromFile(PATH_FROM_FILE);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
        ParserService parserService = new ParserServiceCsvImpl();
        List<FruitTransaction> fruits = parserService.parseData(data);
        FruitService fruitService = new FruitServiceImpl(new FruitDaoImpl(),
                new TransactionStrategyImpl(operationHandlerMap));
        fruitService.processTransactions(fruits);
        List<String> reportList = fruitService.createReport();
        WriterService writerService = new WriterServiceImpl();
        writerService.writeToFile(PATH_TO_FILE, reportList);
    }
}
