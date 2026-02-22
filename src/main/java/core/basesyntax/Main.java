package core.basesyntax;

import core.basesyntax.handler.BalanceOperation;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperation;
import core.basesyntax.handler.ReturnOperation;
import core.basesyntax.handler.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read("reportToRead.csv");

        DataConverter dataConverter = new DataConverterImpl();
        final List<FruitTransaction> fruitTransactionList = dataConverter.convert(inputReport);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(fruitTransactionList);

        Map<String, Integer> storage = shopService.getStorage();

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        List<String> resultingReport = reportGenerator.getReport(storage);

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, "finalReport.csv");
    }
}
