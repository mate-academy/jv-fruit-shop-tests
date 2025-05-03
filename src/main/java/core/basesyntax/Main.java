package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.impl.BalanceHandler;
import core.basesyntax.operation.impl.PurchaseHandler;
import core.basesyntax.operation.impl.ReturnHandler;
import core.basesyntax.operation.impl.SupplyHandler;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/file.csv";
    private static final String REPORT_FILE_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitDao));

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        ReaderService readerService = new ReaderServiceImpl();
        ParserService parseService = new ParseServiceImpl();

        List<String> stringsFromFile = readerService.readFromFile(INPUT_FILE_PATH);
        List<FruitTransaction> fruitTransactions = parseService.parse(stringsFromFile);

        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.process(fruitTransaction.getOperation()).getHandler(fruitTransaction);
        }

        WriterService writerService = new WriterServiceImpl();
        ReportService reportService = new ReportServiceImpl();
        writerService.writeToFile(REPORT_FILE_PATH,
                reportService.getReport(fruitDao.getAll()));
    }
}
