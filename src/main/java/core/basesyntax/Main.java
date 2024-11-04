package core.basesyntax;

import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.converter.FileReaderImpl;
import core.basesyntax.converter.FileWriter;
import core.basesyntax.converter.FileWriterImpl;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.report.ReportGeneratorImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.FruitStorageImpl;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.transaction.FruitTransaction;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final Path path = Paths.get("src/main/java/core/basesyntax/resources/reportToRead.csv");
        final Path finalPath = Paths.get("src/main/java/core/basesyntax/resources/finalToRead.csv");
        FileReaderImpl fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader
                .read(path.toString());

        DataConverterImpl dataConverterimpl = new DataConverterImpl();
        final List<FruitTransaction> transactions = dataConverterimpl
                .converterToTransaction(inputReport);
        FruitStorage fruitStorage = new FruitStorageImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitStorage));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(fruitStorage));
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitStorage));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitStorage));

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);

        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl(fruitStorage);
        String resultingReport = reportGenerator.generateReport();

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport,
                finalPath.toString());
    }
}
