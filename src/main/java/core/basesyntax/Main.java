package core.basesyntax;

import core.basesyntax.file.DataConverter;
import core.basesyntax.file.DataConverterImpl;
import core.basesyntax.file.FileReader;
import core.basesyntax.file.FileReaderImpl;
import core.basesyntax.file.FileWriter;
import core.basesyntax.file.FileWriterImpl;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String reportToRead = "src/main/resources/reportToRead.csv";
    private static final String finalReport = "src/main/resources/finalReport.csv";

    public static void main(String[] args) {

        // 1. Read the data from the input CSV file
        FileReader fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read(reportToRead);

        // 2. Convert the incoming data into FruitTransactions list

        // 3. Create and feel the map with all OperationHandler implementations
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(shopService));

        // 4. Process the incoming transactions with applicable OperationHandler implementations
        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        shopService.process(transactions);

        // 5.Generate report based on the current Storage state
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport(shopService);

        // 6. Write the received report into the destination file
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, finalReport);
    }
}
