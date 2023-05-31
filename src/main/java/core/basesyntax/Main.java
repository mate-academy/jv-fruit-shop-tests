package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceOperation;
import core.basesyntax.handler.impl.PurchaseOperation;
import core.basesyntax.handler.impl.ReturnOperation;
import core.basesyntax.handler.impl.SupplyOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.FileReaderService;
import core.basesyntax.services.FileWriterService;
import core.basesyntax.services.OperationProcessor;
import core.basesyntax.services.ParserService;
import core.basesyntax.services.ReportGenerator;
import core.basesyntax.services.impl.FileReaderServiceImpl;
import core.basesyntax.services.impl.FileWriterServiceImpl;
import core.basesyntax.services.impl.OperationProcessorImpl;
import core.basesyntax.services.impl.ParserServiceImpl;
import core.basesyntax.services.impl.ReportGeneratorImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_PATH_FILE
            = "src/main/java/core/basesyntax/resources/transactions.csv";
    private static final String OUTPUT_PATH_FILE
            = "src/main/java/core/basesyntax/resources/report.csv";

    public static void main(String[] args) {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerOperationMap = new HashMap<>();
        handlerOperationMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitDao));
        handlerOperationMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitDao));
        handlerOperationMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitDao));
        handlerOperationMap.put(
                FruitTransaction.Operation.RETURN, new ReturnOperation(fruitDao));

        FileReaderService reader = new FileReaderServiceImpl();
        List<String> transactions = reader.read(INPUT_PATH_FILE);
        ParserService parserService = new ParserServiceImpl();
        List<FruitTransaction> dataTransactions = parserService.parse(transactions);
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerOperationMap);
        OperationProcessor processor = new OperationProcessorImpl(operationStrategy);
        processor.process(dataTransactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl(fruitDao);
        String report = reportGenerator.generate();
        FileWriterService writer = new FileWriterServiceImpl();
        writer.writeToFile(OUTPUT_PATH_FILE, report);
    }
}
