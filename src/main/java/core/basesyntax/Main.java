package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.service.ParsingService;
import core.basesyntax.service.ReadingService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WritingService;
import core.basesyntax.service.impl.ParsingServiceImpl;
import core.basesyntax.service.impl.ReadingServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WritingServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;

public class Main {
    private static final String FROM_FILE_PATH = "src/main/java/core.resources/fruitsInfo.csv";
    private static final String TO_FILE_PATH = "src/main/java/core.resources/report.csv";
    private static final int START_INDEX = 1;

    public static void main(String[] args) {

        FruitsDao fruitsDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(fruitsDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(fruitsDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(fruitsDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(fruitsDao));
        ReadingService readed = new ReadingServiceImpl();
        ParsingService parsed = new ParsingServiceImpl();
        List<String> list = readed.readFromFile(FROM_FILE_PATH);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        for (String line: list.subList(START_INDEX, list.size())) {
            FruitTransaction fruitTransaction = parsed.parse(line);
            OperationHandler operationHandler = operationStrategy
                    .get(fruitTransaction.getOperation());
            operationHandler.handle(fruitTransaction);
        }

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.createReport(fruitsDao.getAll());
        System.out.println(report);
        WritingService writingService = new WritingServiceImpl();
        writingService.writeToFile(report, TO_FILE_PATH);
    }
}