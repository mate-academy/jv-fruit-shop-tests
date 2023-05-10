package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperationStrategy;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FruitOperationStrategyImpl;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String READ_FROM_FILE_PATH = "src/main/resources/data.txt";
    private static final String WRITE_TO_FILE_PATH = "src/main/resources/report.txt";

    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());

        FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl();
        ReportService reportService = new ReportServiceImpl();
        WriterService writerService = new WriterServiceImpl();
        FruitOperationStrategy fruitOperationStrategy =
                new FruitOperationStrategyImpl(operationHandlerMap);


        List<FruitTransaction> fruitTransactions = fruitTransactionService
                .getFruitTransactionsFromFile(READ_FROM_FILE_PATH);

        for (FruitTransaction fruitTransaction : fruitTransactions) {
            fruitOperationStrategy.put(fruitTransaction).transaction(fruitTransaction);
        }
        String fruitBalanceReport = reportService.createFruitBalanceReport(Storage.fruitBalance);
        writerService.createReportFile(fruitBalanceReport, WRITE_TO_FILE_PATH);
    }
}
