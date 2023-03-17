package core.basesyntax;

import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationTypeHandler;
import core.basesyntax.handlers.PurchaseHandler;
import core.basesyntax.handlers.ReturnHandler;
import core.basesyntax.handlers.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionParserService;
import core.basesyntax.service.WriterService;
import core.basesyntax.serviceimpl.ReaderServiceImpl;
import core.basesyntax.serviceimpl.ReportServiceImpl;
import core.basesyntax.serviceimpl.TransactionParserServiceImpl;
import core.basesyntax.serviceimpl.WriterServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final File INPUT_FILE = new File(
            "src/main/java/core/basesyntax/resources/input.csv");
    private static final File OUTPUT_FILE = new File(
            "src/main/java/core/basesyntax/resources/output.csv");
    private static final int FIRST_VALID_LINE = 1;
    private static ReaderService reader = new ReaderServiceImpl();
    private static WriterService writer = new WriterServiceImpl();

    private static ReportService reporting = new ReportServiceImpl();
    private static Map<FruitTransaction.Operation, OperationTypeHandler> strategy = new HashMap<>();
    private static OperationStrategy operationStrategy = new OperationStrategyImpl(strategy);
    private static TransactionParserService parser = new TransactionParserServiceImpl();

    public static void main(String[] args) {
        strategy.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        strategy.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        strategy.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        strategy.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());

        List<String> content = reader.read(INPUT_FILE);
        for (int i = FIRST_VALID_LINE; i < content.size(); i++) {
            FruitTransaction fruitTransaction = parser.saveToStorage(content.get(i));
            operationStrategy.getHandlerByOperation(fruitTransaction.getOperation())
                    .handle(fruitTransaction);
        }
        String report = reporting.newReport();
        writer.write(OUTPUT_FILE, report);
    }
}
