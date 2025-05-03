package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationService;
import core.basesyntax.service.WriteService;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReadServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriteServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH =
            "src/main/java/core/basesyntax/resources/input.csv";
    private static final String REPORT_FILE_PATH =
            "src/main/java/core/basesyntax/resources/report.csv";

    public static void main(String[] args) {
        Map<FruitTransaction.Operation, OperationHandler>
                operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());

        List<String> inputData = new ReadServiceImpl().readInputData(INPUT_FILE_PATH);
        List<FruitTransaction> parseData = new ParseServiceImpl().parseInputData(inputData);

        OperationStrategy strategy = new OperationStrategy(operationHandlerMap);
        OperationService service = new OperationService(strategy);
        service.toFormStorage(parseData);

        String report = new ReportServiceImpl().createReport();
        System.out.println(report);

        WriteService writeService = new WriteServiceImpl();
        writeService.writeReport(report, REPORT_FILE_PATH);

    }
}
