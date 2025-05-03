package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.CsvFileWriterService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.util.List;
import java.util.Map;

public class Main {
    private static final int INPUT_FILE_PATH_ARG_INDEX = 0;
    private static final int OUTPUT_FILE_PATH_ARG_INDEX = 1;

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Please provide input and "
                    + "output file paths as arguments.");
        }

        String inputFilePath = args[INPUT_FILE_PATH_ARG_INDEX];
        String outputFilePath = args[OUTPUT_FILE_PATH_ARG_INDEX];

        CsvFileReaderService readerService = new CsvFileReaderServiceImpl();
        FruitTransactionParser parser = new FruitTransactionParserImpl();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        CsvFileWriterService writerService = new CsvFileWriterServiceImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler()
        );

        FruitService fruitService = new FruitServiceImpl(operationHandlers);

        List<String> lines = readerService.readFromFile(inputFilePath);
        List<FruitTransaction> transactions = parser.parseLines(lines);

        fruitService.applyTransactions(transactions);

        List<String> report = reportGenerator.generateReport(fruitService.getReportData());
        writerService.writeToFile(outputFilePath, report);
    }
}
