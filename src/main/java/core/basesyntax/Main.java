package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.TransactionParseImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandlerOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerOperation;
import core.basesyntax.strategy.impl.ReturnHandlerOperation;
import core.basesyntax.strategy.impl.SupplyHandlerOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/input_file";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/report_file";

    public static void main(String[] args) {
        FileService fileReadService = new FileServiceImpl();
        List<String> dataFromFile = fileReadService.parseDataFromFile(INPUT_FILE_PATH);

        Map<FruitTransaction.Operation, OperationHandler> typeOperationHandlerMap = new HashMap<>();
        typeOperationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandlerOperation());
        typeOperationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandlerOperation());
        typeOperationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandlerOperation());
        typeOperationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandlerOperation());

        TransactionParser parser = new TransactionParseImpl();
        List<FruitTransaction> fruitTransactionToList = parser.parse(dataFromFile);

        OperationStrategy operationStrategy = new OperationStrategyImpl(typeOperationHandlerMap);

        for (FruitTransaction fruitTransaction : fruitTransactionToList) {
            OperationHandler handler = operationStrategy.get(fruitTransaction.getOperation());
            handler.handle(fruitTransaction);
        }

        ReportService reportService = new ReportServiceImpl();
        String fileReport = reportService.createReport();

        FileService fileWriteService = new FileServiceImpl();
        fileWriteService.writeDataToFile(fileReport, OUTPUT_FILE_PATH);
    }
}
