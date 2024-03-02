package core.basesyntax;

import core.basesyntax.dao.impl.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FileDataParser;
import core.basesyntax.service.impl.FileReader;
import core.basesyntax.service.impl.FileWriter;
import core.basesyntax.service.impl.FruitTransactionProcessor;
import core.basesyntax.service.impl.ReportService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_TO_FILE_READ = "src/main/resources/input_file.csv";
    private static final String PATH_TO_FILE_WRITE = "src/main/resources/output_file.csv";

    public static void main(String[] args) {
        FruitDao fruitDao = new FruitDaoImpl();

        FileReader readerFile = new FileReaderCsvImpl();
        FileWriter fileWriter = new FileWriterCsvImpl();
        FileDataParser fileDataParser = new FileDataParserCsvImpl();
        ReportService reportService = new ReportServiceImpl(fruitDao);

        FruitTransactionProcessor fruitTransactionProcessor =
                getFruitTransactionProcessor(fruitDao);

        List<String> dataFromFile = readerFile
                .readFromFile(PATH_TO_FILE_READ);

        List<FruitTransaction> transactions = fileDataParser.parseData(dataFromFile);
        fruitTransactionProcessor.processTransactions(transactions);

        String report = reportService.createReport();
        fileWriter.writeToFile(report, PATH_TO_FILE_WRITE);
    }

    private static FruitTransactionProcessor getFruitTransactionProcessor(FruitDao fruitDao) {
        OperationStrategy operationStrategy = new OperationStrategy(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(fruitDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(fruitDao),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(fruitDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(fruitDao)
        ));

        return new FruitTransactionProcessorImpl(operationStrategy);
    }
}
