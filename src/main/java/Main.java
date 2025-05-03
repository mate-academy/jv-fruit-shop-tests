import core.basesyntax.db.FruitShopDao;
import core.basesyntax.db.FruitShopDaoCsvImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ConvertToFruitTransactionService;
import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.CsvFileWriterService;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.ReportGenerationService;
import core.basesyntax.service.impl.ConverterToFruitTransactionImpl;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.ReportGenerationServiceImpl;
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
    private static final String INPUT_FILE_NAME = "src/main/resources/input_file.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/output_file.csv";

    public static void main(String[] args) {
        FruitShopDao fruitShopDao = new FruitShopDaoCsvImpl();

        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler(fruitShopDao));
        operationHandlerMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler(fruitShopDao));
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler(fruitShopDao));
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler(fruitShopDao));

        FruitStrategy fruitStrategy = new FruitStrategyImpl(operationHandlerMap);

        CsvFileReaderService readerService = new CsvFileReaderServiceImpl();
        List<String> inputFileLines = readerService.readFromCsvFile(INPUT_FILE_NAME);

        ConvertToFruitTransactionService converterService = new ConverterToFruitTransactionImpl();
        List<FruitTransaction> transactions = converterService.convert(inputFileLines);

        FruitTransactionService processService =
                new FruitTransactionServiceImpl(fruitShopDao, fruitStrategy);
        processService.processTransactions(transactions);

        ReportGenerationService reportGenerationService =
                new ReportGenerationServiceImpl(fruitShopDao);
        List<String> report = reportGenerationService.generateReport();

        CsvFileWriterService writerService = new CsvFileWriterServiceImpl();
        writerService.writeToNewCsvFile(OUTPUT_FILE_NAME, report);
    }
}
