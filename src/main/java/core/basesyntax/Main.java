package core.basesyntax;

import core.basesyntax.db.StorageDao;
import core.basesyntax.db.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.StorageServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "Data.csv";
    private static final String OUTPUT_FILE = "Report.csv";
    private static final Map<Operation, OperationHandler> operations = new HashMap<>();
    private static final StorageDao dao = new StorageDaoImpl();

    public static void main(String[] args) {

        operations.put(Operation.BALANCE, new BalanceOperationHandler(dao));
        operations.put(Operation.PURCHASE, new PurchaseOperationHandler(dao));
        operations.put(Operation.RETURN, new ReturnOperationHandler(dao));
        operations.put(Operation.SUPPLY, new SupplyOperationHandler(dao));
        // 1.Reading data from CSV file
        FileReader fileReader = new FileReaderImpl();
        List<String> transactionList = fileReader.readTransactionsFromFile(INPUT_FILE);
        // 2. Convert data from String List to Fruit Transaction List
        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> fruitTransactions = dataConverter
                .convertToTransaction(transactionList);
        // 3. Update the Storage according to transactions
        StorageService storageService = new StorageServiceImpl(operations);
        storageService.process(fruitTransactions);
        // 4. Generate report
        ReportGenerator generator = new ReportGeneratorImpl(dao);
        String report = generator.generateReport();
        // 5. Save report to file
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeReportToFile(report, OUTPUT_FILE);
    }
}
