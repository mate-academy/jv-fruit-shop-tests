package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.service.impl.FruitTransactionProcessorImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnsOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH =
            "src/main/java/core/basesyntax/resources/daily_summary.csv";
    private static final String REPORT_FILE_PATH =
            "src/main/java/core/basesyntax/resources/reportFile.csv";

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnsOperationHandler());
        FruitTransactionProcessor fruitTransactionProcessor =
                new FruitTransactionProcessorImpl(operationHandlerMap);

        List<String> data = new FileReaderServiceImpl().read(INPUT_FILE_PATH);
        List<FruitTransaction> fruitTransactionData = new FruitTransactionParserImpl()
                .parseList(data);
        fruitTransactionData.forEach(f -> fruitTransactionProcessor.get(f.getOperation())
                .handle(f));
        String report = new ReportServiceImpl().getReport();
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.write(report, REPORT_FILE_PATH);
    }
}
