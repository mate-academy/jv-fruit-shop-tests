package core.basesyntax;

import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FileReaderImpl fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read("src/main/resources/reportRead.csv");

        DataConverterImpl dataConverter = new DataConverterImpl();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopServiceImpl shopService = new ShopServiceImpl(operationStrategy);

        shopService.process(transactions);

        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport();

        FileWriterImpl fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, "src/main/resources/finalReport.csv");
    }
}
