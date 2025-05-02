package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionMapperService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.activities.BalanceHandler;
import core.basesyntax.strategy.activities.OperationHandler;
import core.basesyntax.strategy.activities.PurchaseHandler;
import core.basesyntax.strategy.activities.ReturnHandler;
import core.basesyntax.strategy.activities.SupplyHandler;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String REPORT_PATH = "src/main/resources/dailyReport.csv";
    private static final String FILE_PATH = "src/main/resources/file.csv";
    private static final FileReaderService readFile = new FileReaderServiceImpl();
    private static final FileWriterService fileWriter = new FileWriterServiceImpl();
    private static final ReportService reportService = new ReportServiceImpl();
    private static final TransactionMapperService transactionMapperService =
            new TransactionMapperService();

    public static void main(String[] args) {
        String inputFileLines;
        try {
            inputFileLines = readFile.read(FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<FruitTransaction> transactions =
                transactionMapperService.stringToFruitTransaction(inputFileLines);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        FruitService fruitService = new FruitServiceImpl(operationStrategy);
        fruitService.processTransactions(transactions);

        String report = reportService.generateReport();

        fileWriter.writeToFile(report, REPORT_PATH);
    }
}
