package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationStrategyImpl;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/output.csv";

    public static void main(String[] args) {
        File file = new File(INPUT_FILE_NAME);

        ReaderService readerService = new ReaderServiceImpl();
        List<String> records = readerService.readFromFile(file.getPath());

        ParseService parseService = new ParseServiceImpl();
        List<FruitTransaction> fruitTransactionDtos = parseService.parse(records);

        Map<FruitTransaction.Operation, OperationHandler> operationMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler()
        );

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationMap);
        for (FruitTransaction fruitTransaction : fruitTransactionDtos) {
            OperationHandler operationHandler = operationStrategy
                    .get(fruitTransaction.getOperation());
            operationHandler.handle(fruitTransaction);
        }

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.createReport();

        WriterService writerService = new WriterServiceImpl();
        writerService.write(report, OUTPUT_FILE_NAME);
    }
}
