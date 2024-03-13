package core.basesyntax.client;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoCsvImpl;
import core.basesyntax.entity.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.quantity.handlers.BalanceHandler;
import core.basesyntax.service.quantity.handlers.OperationHandler;
import core.basesyntax.service.quantity.handlers.PurchaseHandler;
import core.basesyntax.service.quantity.handlers.ReturnHandler;
import core.basesyntax.service.quantity.handlers.SupplyHandler;
import java.util.Map;

public class FruitStoreRunner {

    public static void main(String[] args) {
        final String PATH_TO_DAILY_ACTIVITY_FILE
                = "src/main/resources/dailyactivities.csv";
        final String PATH_TO_REPORT_FILE
                = "src/main/resources/finalreport.csv";
        Map<Operation, OperationHandler> operationHandlerMap = Map.of(
                Operation.RETURN, new ReturnHandler(),
                Operation.SUPPLY, new SupplyHandler(),
                Operation.PURCHASE, new PurchaseHandler(),
                Operation.BALANCE, new BalanceHandler());
        FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoCsvImpl(PATH_TO_DAILY_ACTIVITY_FILE,PATH_TO_REPORT_FILE);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        ReportService reportService = new ReportServiceImpl(fruitTransactionDao, operationStrategy);
        reportService.generateFinalReport();
    }
}
