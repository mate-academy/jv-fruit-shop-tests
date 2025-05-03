package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FileService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.FileServiceImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.TransactionParseImpl;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String FILE_NAME = "src/main/resources/Fruits.csv";
    public static final String FILE_REPORT = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> strategies = new HashMap<>();
        strategies.put(Operation.BALANCE, new BalanceOperationHandler());
        strategies.put(Operation.PURCHASE, new PurchaseOperationHandler());
        strategies.put(Operation.SUPPLY, new SupplyOperationHandler());
        strategies.put(Operation.RETURN, new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(strategies);
        FileService fileService = new FileServiceImpl();
        List<String> dataFromFile = fileService.read(FILE_NAME);
        List<FruitTransaction> transactionList = new TransactionParseImpl().parse(dataFromFile);

        for (FruitTransaction fruitTransaction : transactionList) {
            OperationHandler handler = operationStrategy
                    .get(fruitTransaction.getOperation());
            handler.handle(fruitTransaction);
        }
        ReportService reportService = new ReportServiceImpl();
        String report = reportService.generateReport();
        fileService.write(FILE_REPORT, report);
    }
}
