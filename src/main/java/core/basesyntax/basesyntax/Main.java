package core.basesyntax.basesyntax;

import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.model.Operation;
import core.basesyntax.basesyntax.service.ReaderService;
import core.basesyntax.basesyntax.service.ReportCreator;
import core.basesyntax.basesyntax.service.TransactionParser;
import core.basesyntax.basesyntax.service.TransactionProcessor;
import core.basesyntax.basesyntax.service.WriterService;
import core.basesyntax.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.basesyntax.strategy.OperationStrategy;
import core.basesyntax.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH
            = "src/main/java/core/basesyntax/resources/InputFile.csv";
    private static final String OUTPUT_FILE_PATH
            = "src/main/java/core/basesyntax/resources/OutputFile.csv";

    public static void main(String[] args) {
        ReaderService reader = new ReaderServiceImpl();
        List<String> dataFromFile = reader.readFile(INPUT_FILE_PATH);

        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> fruitTransactions = transactionParser.parseData(dataFromFile);

        OperationStrategy operationStrategy = new OperationStrategy(Map.of(
                Operation.BALANCE, new BalanceHandlerImpl(),
                Operation.SUPPLY, new SupplyHandlerImpl(),
                Operation.PURCHASE, new PurchaseHandlerImpl(),
                Operation.RETURN, new ReturnHandlerImpl()));

        TransactionProcessor transactionProcessor = new TransactionProcessorImpl(operationStrategy);
        transactionProcessor.process(fruitTransactions);

        ReportCreator reportCreator = new ReportCreatorImpl();
        String report = reportCreator.createReport();

        WriterService writerService = new WriterServiceImpl();
        writerService.writeDataToFile(report, OUTPUT_FILE_PATH);
    }
}
