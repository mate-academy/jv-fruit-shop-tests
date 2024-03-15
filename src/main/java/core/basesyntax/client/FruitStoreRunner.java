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
    private static final String DAILY_ACTIVITY_FILE_PATH
            = "src/main/resources/dailyactivities.csv";
    private static final String REPORT_FILE_PATH
            = "src/main/resources/finalreport.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationHandlerMap = Map.of(
                Operation.RETURN, new ReturnHandler(),
                Operation.SUPPLY, new SupplyHandler(),
                Operation.PURCHASE, new PurchaseHandler(),
                Operation.BALANCE, new BalanceHandler());
        FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoCsvImpl(
                DAILY_ACTIVITY_FILE_PATH,REPORT_FILE_PATH);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        ReportService reportService = new ReportServiceImpl(fruitTransactionDao, operationStrategy);
        reportService.generateFinalReport();
    }
}
