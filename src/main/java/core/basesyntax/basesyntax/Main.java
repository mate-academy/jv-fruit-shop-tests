package core.basesyntax.basesyntax;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.service.FileReader;
import core.basesyntax.basesyntax.service.FileWriter;
import core.basesyntax.basesyntax.service.OperationHandlerProvider;
import core.basesyntax.basesyntax.service.Parser;
import core.basesyntax.basesyntax.service.ReportGenerator;
import core.basesyntax.basesyntax.service.ShopService;
import core.basesyntax.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.basesyntax.service.impl.OperationHandlerProviderImpl;
import core.basesyntax.basesyntax.service.impl.ParserImpl;
import core.basesyntax.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.basesyntax.strategy.BalanceOperation;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.basesyntax.strategy.ReturnOperation;
import core.basesyntax.basesyntax.strategy.SupplyOperation;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "reportToRead.csv";
    private static final String OUTPUT_FILE_PATH = "output/finalReport.csv";

    public static void main(String[] arg) {
        FileReader fileReader = new FileReaderImpl();
        InputStream inputStream =
                Main.class.getClassLoader().getResourceAsStream(INPUT_FILE_PATH);

        List<String> inputReport = fileReader.readFile(inputStream);

        Parser parser = new ParserImpl();
        List<FruitTransaction> transactions = parser.parse(inputReport);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationHandlerProvider operationHandlerProvider =
                new OperationHandlerProviderImpl(operationHandlers);

        StorageImpl storage = new StorageImpl();

        ShopService shopService = new ShopServiceImpl(operationHandlerProvider, storage);
        StorageImpl result = shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        List<String> resultingReport = reportGenerator.generate(result);

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeFile(resultingReport, OUTPUT_FILE_PATH);
    }
}
