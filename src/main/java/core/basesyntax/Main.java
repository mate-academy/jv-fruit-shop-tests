package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String DATABASE_CSV_FILE_NAME =
            "src/main/java/core/basesyntax/database.csv";
    private static final String REPORT_CSV_FILE_NAME =
            "src/main/java/core/basesyntax/report.csv";

    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        List<String> stringsFromCsvFile = new FileReaderServiceImpl()
                .readFromFile(DATABASE_CSV_FILE_NAME);
        List<FruitTransaction> fruitTransactions = new ParseServiceImpl().parse(stringsFromCsvFile);
        List<String> report = new ReportServiceImpl().create(fruitTransactions,
                operationStrategy);
        new FileWriterServiceImpl().writeToFile(report, REPORT_CSV_FILE_NAME);
    }
}
