package core.basesyntax.store;

import core.basesyntax.store.model.FruitTransaction;
import core.basesyntax.store.service.DataConverter;
import core.basesyntax.store.service.FileReader;
import core.basesyntax.store.service.FileWriter;
import core.basesyntax.store.service.ReportGenerator;
import core.basesyntax.store.service.ShopService;
import core.basesyntax.store.service.impl.DataConverterImpl;
import core.basesyntax.store.service.impl.FileReaderImpl;
import core.basesyntax.store.service.impl.FileWriterImpl;
import core.basesyntax.store.service.impl.ReportGeneratorImpl;
import core.basesyntax.store.service.impl.ShopServiceImpl;
import core.basesyntax.store.strategy.OperationHandler;
import core.basesyntax.store.strategy.OperationStrategy;
import core.basesyntax.store.strategy.impl.BalanceOperation;
import core.basesyntax.store.strategy.impl.OperationStrategyImpl;
import core.basesyntax.store.strategy.impl.PurchaseOperation;
import core.basesyntax.store.strategy.impl.ReturnOperation;
import core.basesyntax.store.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] arg) {
        // 1. Read the data from the input CSV file
        FileReader fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read("reportToRead.csv");

        // 2. Convert the incoming data into FruitTransactions list
        DataConverter dataConverter = new DataConverterImpl();
        final List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        // 3. Create and feel the map with all OperationHandler implementations
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        // 4. Process the incoming transactions with applicable OperationHandler implementations
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);

        // 5.Generate report based on the current Storage state
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport();

        // 6. Write the received report into the destination file
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, "finalReport.csv");
    }
}
