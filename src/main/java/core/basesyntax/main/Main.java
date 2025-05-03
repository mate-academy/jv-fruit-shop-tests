package core.basesyntax.main;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.Parser;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main {
    private static final String OUTPUT_PATH = "src/main/resources/output.csv";
    private static final String INPUT_PATH = "src/main/resources/input.csv";

    public static void main(String[] args) {
        StorageDao storageDao = new StorageDaoImpl();
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler(storageDao));
        operationHandlerMap.put("p", new PurchaseOperationHandler(storageDao));
        operationHandlerMap.put("r", new ReturnOperationHandler(storageDao));
        operationHandlerMap.put("s", new SupplyOperationHandler(storageDao));
        FileReader fileReader = new FileReaderImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FruitTransactionService fruitTransactionService =
                new FruitTransactionServiceImpl(operationStrategy);
        Function<List<String>, List<FruitTransaction>> parser = new Parser();
        fruitTransactionService.transfer(parser.apply(fileReader.read(INPUT_PATH)));
        ReportService reportService = new ReportServiceImpl(storageDao);
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(OUTPUT_PATH, reportService.report());
    }
}
