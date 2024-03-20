package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionMapper;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.StrategyService;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.FruitMapperImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.StrategyServiceImpl;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_FILE = "src/main/resources/fileToRead.csv";
    private static final String PATH_TO_REPORT_FILE = "src/main/resources/reportFile.csv";
    private static final ReaderService readerService = new ReaderServiceImpl();
    private static final FruitTransactionMapper parseService = new FruitMapperImpl();
    private static final ReportService reportService = new ReportServiceImpl();
    private static final WriterService writerService = new WriterServiceImpl();

    private static StrategyService strategyService;
    private static TransactionProcessor transactionService;

    public static void main(String[] args) {
        Map<Operation, OperationHandler> operationMap =
                Map.of(Operation.BALANCE, new BalanceOperation(),
                        Operation.PURCHASE, new PurchaseOperation(),
                        Operation.SUPPLY, new SupplyOperation(),
                        Operation.RETURN, new ReturnOperation());
        List<String> dataFromFile = readerService.readFromFile(PATH_TO_FILE);
        List<FruitTransaction> values = parseService.map(dataFromFile);

        strategyService = new StrategyServiceImpl(operationMap);
        transactionService = new TransactionProcessorImpl(strategyService);

        transactionService.process(values);
        String report = reportService.createReport();

        writerService.writeToFile(PATH_TO_REPORT_FILE,report);
    }
}
