package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.impl.BalanceOperation;
import core.basesyntax.operation.impl.OperationHandler;
import core.basesyntax.operation.impl.PurchaseOperation;
import core.basesyntax.operation.impl.ReturnOperation;
import core.basesyntax.operation.impl.SupplyOperation;
import core.basesyntax.service.*;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Main {
    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read("src/main/resources/fruits.csv");

        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> transactions = null;
        try {
            transactions = dataConverter.convertToTransaction(inputReport);
        } catch (RuntimeException e) {
            System.out.println("Wrong data format");
            System.exit(-1);
        }
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        Map<String, Integer> processedMap = shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport(processedMap);

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, "finalReport.csv");
    }
}
