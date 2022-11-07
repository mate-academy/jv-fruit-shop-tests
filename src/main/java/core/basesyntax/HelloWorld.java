package core.basesyntax;

import core.basesyntax.dao.DaoFruit;
import core.basesyntax.dao.FruitImplemDao;
import core.basesyntax.db.Storage;
import core.basesyntax.impl.FruitTransactionParserImpl;
import core.basesyntax.impl.FruitTransactionServiceImpl;
import core.basesyntax.impl.ReportServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FruitParser;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.strategy.Strategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorld {
    private static final String INPUT_PATH_TOFILE = "src/main/resources/input.csv";
    private static final String OUTPUT_PATH_TOFILE = "src/main/resources/report.csv";

    public static void main(String[] args) {
        DaoFruit fruitDao = new FruitImplemDao();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(fruitDao));
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(fruitDao));
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(fruitDao));
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(fruitDao));
        FileReader fileReader = new FileReaderImpl();

        List<String> inputStrings = fileReader.readFromFile(INPUT_PATH_TOFILE);

        FruitParser parser = new FruitTransactionParserImpl();
        List<FruitTransaction> fruitTransactions =
                parser.parse(inputStrings);

        Strategy strategy = new Strategy(operationHandlersMap);
        FruitService fruitService =
                new FruitTransactionServiceImpl(strategy);
        fruitService.process(fruitTransactions);

        ReportService reportService = new ReportServiceImpl();
        String report = reportService.writeReport(Storage.fruits);

        FileWriter writer = new FileWriterImpl();
        writer.writeToFile(report, INPUT_PATH_TOFILE);

    }
}
