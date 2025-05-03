package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileRead;
import core.basesyntax.service.FileWrite;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileReadImpl;
import core.basesyntax.service.impl.FileWriteImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.FruitOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import core.basesyntax.validation.DataValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitShopApplication {
    private static final Map<String, Integer> inventory = new HashMap<>();
    private static final FileRead fileRead = new FileReadImpl();
    private static final DataValidator dataValidation = new DataValidator();
    private static final DataConverter dataConverter = new DataConverterImpl(dataValidation);
    private static final ReportGenerator reportGenerator = new ReportGeneratorImpl();
    private static final FileWrite fileWriter = new FileWriteImpl();

    public static void main(String[] arg) {
        List<String> inputReport = fileRead.readDataFromFile("reportToRead.csv");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        processTransactions(transactions);
        String resultingReport = reportGenerator.getReport(inventory);
        fileWriter.write(resultingReport, "finalReport.csv");
    }

    private static void processTransactions(List<FruitTransaction> transactions) {
        Map<Operation, FruitOperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.processFruitTransactions(transactions, inventory);
    }
}
