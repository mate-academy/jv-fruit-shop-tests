package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.TransactionHandlerService;
import core.basesyntax.service.impl.CreateReportImpl;
import core.basesyntax.service.impl.FileReaderCsvImpl;
import core.basesyntax.service.impl.FileWriterCsvImpl;
import core.basesyntax.service.impl.ParseImpl;
import core.basesyntax.service.impl.TransactionHandlerImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String inputFilePath = "src/main/resources/inputFile.csv";
    private static final String outputFilePath = "src/main/resources/exportFile.csv";

    public static void main(String[] args) {
        Map<FruitTransaction.OperationType, OperationHandler> handlerMap = Map.of(
                FruitTransaction.OperationType.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.OperationType.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.OperationType.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.OperationType.RETURN, new ReturnOperationHandler());

        FileReaderService fileReader = new FileReaderCsvImpl();
        ParseService parse = new ParseImpl();
        CreateReportService createReport = new CreateReportImpl();
        FileWriterService fileWriter = new FileWriterCsvImpl(createReport);
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        List<String> dataFromFile = fileReader.readFile(inputFilePath);
        List<FruitTransaction> transactions = parse.parse(dataFromFile);
        TransactionHandlerService transactionHandler =
                new TransactionHandlerImpl(operationStrategy);
        transactionHandler.handleTransactions(transactions);
        fileWriter.writeFile(outputFilePath);
    }
}
