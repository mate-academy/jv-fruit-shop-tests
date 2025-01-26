package core.basesyntax;

import core.basesyntax.convertor.DataConvertor;
import core.basesyntax.convertor.DataConvertorImpl;
import core.basesyntax.dao.Storage;
import core.basesyntax.files.CustomFileReader;
import core.basesyntax.files.CustomFileReaderImpl;
import core.basesyntax.files.CustomFileWriter;
import core.basesyntax.files.FileWriterImpl;
import core.basesyntax.generator.ReportGenerator;
import core.basesyntax.generator.ReportGeneratorImpl;
import core.basesyntax.handler.BalanceOperation;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperation;
import core.basesyntax.handler.ReturnOperation;
import core.basesyntax.handler.SupplyOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILE_TO_READ = "reportToRead.csv";
    private static final String FILE_TO_WRITE = "finalReport.csv";

    public static void main(String[] args) {

        CustomFileReader customFileReader = new CustomFileReaderImpl();
        List<String> inputReport = customFileReader.read(FILE_TO_READ);

        DataConvertor dataConverter = new DataConvertorImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();

        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport(Storage.storage);

        CustomFileWriter customFileWriter = new FileWriterImpl();
        customFileWriter.write("src\\main\\resources\\" + FILE_TO_WRITE,resultingReport);
    }

}
