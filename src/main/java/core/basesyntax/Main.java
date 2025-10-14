package core.basesyntax;

import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.FileReaderImpl;
import core.basesyntax.dao.FileWriter;
import core.basesyntax.dao.FileWriterImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.DataConverterImpl;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationsStrategy;
import core.basesyntax.service.OperationsStrategyImpl;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String REPORT_OUTPUT_FILE = "finalReport.csv";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <inputCSV> <outputCSV>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        // 1. Read the data from the input CSV file
        FileReader fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read(inputFile);

        // 2. Convert the incoming data into FruitTransactions list
        DataConverter dataConverter = new DataConverterImpl();
        final List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        // 3. Prepare the storage and operation handlers
        Storage storage = new Storage();
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation(storage));
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation(storage));
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation(storage));
        operationHandlers.put(Operation.RETURN, new ReturnOperation(storage));
        OperationsStrategy operationStrategy = new OperationsStrategyImpl(operationHandlers);

        // 4. Process transactions via service
        ShopService shopService = new ShopServiceImpl(operationStrategy, storage);
        shopService.process(transactions);

        // 5. Generate report based on the current storage state
        ReportGenerator reportGenerator = new ReportGeneratorImpl(storage);
        String resultingReport = reportGenerator.getReport();

        // 6. Write the report to the destination file
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, REPORT_OUTPUT_FILE);

        System.out.println("Report successfully generated and saved to " + REPORT_OUTPUT_FILE);
    }
}
