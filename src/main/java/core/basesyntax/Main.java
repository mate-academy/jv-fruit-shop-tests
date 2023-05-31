package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.OperationStrategy;
import core.basesyntax.service.operations.OperationStrategyImpl;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String FILE_PATH_READ_FROM = "src/main/java/resources/FruitShop.csv";
    public static final String FILE_PATH_WRITE_TO = "src/main/java/resources/FruitShopReport.csv";

    public static void main(String[] args) {
        ReaderService fileReader = new ReaderServiceImpl();
        List<String> dataFromFile = fileReader.readFromFile(FILE_PATH_READ_FROM);
        TransactionParser transactionParser = new TransactionParserImpl();

        Map<FruitTransaction.Operation, OperationHandler> strategies = new HashMap<>();
        strategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        strategies.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        strategies.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        strategies.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        List<FruitTransaction> fruitTransactionsList = transactionParser.parse(dataFromFile);
        OperationStrategy operationStrategy = new OperationStrategyImpl(strategies);

        for (FruitTransaction fruitTransaction : fruitTransactionsList) {
            OperationHandler handler = operationStrategy
                    .get(fruitTransaction.getOperation());
            handler.operate(fruitTransaction);
        }
        ReportService reportService = new ReportServiceImpl();
        String report = reportService.createReport();
        WriterService writerService = new WriterServiceImpl();
        writerService.writeToFile(FILE_PATH_WRITE_TO, report);
    }
}
