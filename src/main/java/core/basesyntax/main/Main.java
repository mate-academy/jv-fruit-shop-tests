package core.basesyntax.main;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.implementation.BalanceHandler;
import core.basesyntax.operation.implementation.PurchaseHandler;
import core.basesyntax.operation.implementation.ReturnHandler;
import core.basesyntax.operation.implementation.SupplyHandler;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriteService;
import core.basesyntax.service.implementation.FruitServiceImpl;
import core.basesyntax.service.implementation.ParseServiceImpl;
import core.basesyntax.service.implementation.ReaderServiceImpl;
import core.basesyntax.service.implementation.ReportServiceImpl;
import core.basesyntax.service.implementation.WriteServiceImpl;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/fruitInput.csv";
    private static final String REPORT_FILE_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FruitDao fruitDao = new FruitDaoImpl();
        FruitService fruitService = new FruitServiceImpl(fruitDao);
        Map<FruitTransaction.Operation,
                        OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(fruitService));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(fruitService));

        ReaderService readerService = new ReaderServiceImpl();
        ParseService parseService = new ParseServiceImpl();
        List<String> stringsFromInput = readerService.readFromFile(INPUT_FILE_PATH);
        final List<FruitTransaction> fruitTransactionList = parseService.parse(stringsFromInput);

        Strategy strategy = new StrategyImpl(operationHandlerMap);
        for (FruitTransaction fruitTransaction: fruitTransactionList) {
            strategy.process(fruitTransaction.getOperation()).handle(fruitTransaction);
        }

        ReportService reportService = new ReportServiceImpl(fruitService);
        String report = reportService.getReport();
        WriteService writeService = new WriteServiceImpl();
        writeService.writeToFile(REPORT_FILE_PATH, report);
    }
}
