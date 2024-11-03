package core.basesyntax;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.CsvFormatReportGenerator;
import core.basesyntax.service.impl.DataConverterServiceImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.operation.BalanceOperation;
import core.basesyntax.service.impl.operation.OperationHandler;
import core.basesyntax.service.impl.operation.PurchaseOperation;
import core.basesyntax.service.impl.operation.ReturnOperation;
import core.basesyntax.service.impl.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/reportToRead.csv";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/finalReport.csv";
    private final FileReaderService fileReader;
    private final DataConverterService dataConverter;
    private final OperationStrategy operationStrategy;
    private final ShopService shopService;
    private final ReportGenerator reportGenerator;
    private final FileWriterService fileWriter;

    public Main(FileReaderService fileReader,
                DataConverterService dataConverter,
                OperationStrategy operationStrategy,
                ShopService shopService,
                ReportGenerator reportGenerator,
                FileWriterService fileWriter) {
        this.fileReader = fileReader;
        this.dataConverter = dataConverter;
        this.operationStrategy = operationStrategy;
        this.shopService = shopService;
        this.reportGenerator = reportGenerator;
        this.fileWriter = fileWriter;
    }

    public void run(String inputFilePath, String outputFilePath) {
        // 1. Read the data from the input CSV file
        List<String> inputReport = fileReader.read(inputFilePath);

        // 2. Convert the incoming data into FruitTransactions list
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        // 3. Process the incoming transactions with applicable OperationHandler implementations
        shopService.process(transactions);

        // 4. Generate report based on the current Storage state
        String resultingReport = reportGenerator.create();

        // 5. Write the received report into the destination file
        fileWriter.writeToFile(outputFilePath, resultingReport);
    }

    public static void main(String[] args) {
        // Setup dependencies
        FruitStorageDao storageDao = new FruitStorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(storageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(storageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(storageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(storageDao)
        );

        FileReaderService fileReader = new FileReaderServiceImpl();
        DataConverterService dataConverter = new DataConverterServiceImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        ReportGenerator reportGenerator = new CsvFormatReportGenerator(storageDao);
        FileWriterService fileWriter = new FileWriterServiceImpl();

        // Create Main instance with all dependencies and run
        Main app = new Main(
                fileReader,
                dataConverter,
                operationStrategy,
                shopService,
                reportGenerator,
                fileWriter
        );
        app.run(INPUT_FILE_PATH, OUTPUT_FILE_PATH);
    }
}
