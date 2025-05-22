package basesyntax;

import basesyntax.converter.DataConverter;
import basesyntax.converter.DataConverterImpl;
import basesyntax.model.FruitTransaction;
import basesyntax.model.Operation;
import basesyntax.reader.FileReaderImpl;
import basesyntax.reader.FileReaderInterface;
import basesyntax.report.FileWriterImpl;
import basesyntax.report.FileWriterInterface;
import basesyntax.report.ReportGenerator;
import basesyntax.report.ReportGeneratorImpl;
import basesyntax.service.OperationStrategy;
import basesyntax.service.OperationStrategyImpl;
import basesyntax.service.ShopService;
import basesyntax.service.ShopServiceImpl;
import basesyntax.service.handler.BalanceOperation;
import basesyntax.service.handler.OperationHandler;
import basesyntax.service.handler.PurchaseOperation;
import basesyntax.service.handler.ReturnOperation;
import basesyntax.service.handler.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/input.csv";
    private static final String REPORT_FILE_PATH = "src/main/resources/finalReport.csv";

    public static void main(String[] args) {
        FileReaderInterface fileReader = new FileReaderImpl();
        List<String> lines = fileReader.read(INPUT_FILE_PATH);

        DataConverter converter = new DataConverterImpl();
        final List<FruitTransaction> transactions = converter.convertToTransaction(lines);

        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());

        OperationStrategy strategy = new OperationStrategyImpl(operationHandlers);
        ShopService service = new ShopServiceImpl(strategy);
        service.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport();

        FileWriterInterface fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport,
                REPORT_FILE_PATH);
    }
}
