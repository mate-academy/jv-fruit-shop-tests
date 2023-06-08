package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReadService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriteService;
import core.basesyntax.service.impl.ParseServiceImpl;
import core.basesyntax.service.impl.ReadServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriteServiceImpl;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.FruitStrategyImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String inputFile = "src/main/resources/inputData.csv";
    private static final String outputFile = "src/main/resources/report.csv";

    public static void main(String[] args) {

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        FruitStrategy fruitStrategy = new FruitStrategyImpl(operationHandlerMap);
        ParseService parser = new ParseServiceImpl();
        ReadService reader = new ReadServiceImpl();

        List<String> stringsFromInputData = reader.readFile(inputFile);

        List<FruitTransaction> fruitTransactions = parser.parseTransactions(stringsFromInputData);

        for (FruitTransaction transaction : fruitTransactions) {
            fruitStrategy.get(transaction.getOperation()).operate(transaction);
        }

        ReportService reportService = new ReportServiceImpl();
        List<String> report = reportService.getReport();

        WriteService writer = new WriteServiceImpl();
        writer.writeFile(outputFile, report);
    }
}
