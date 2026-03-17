package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.ShopStorage;
import core.basesyntax.model.ShopTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.CsvFileReader;
import core.basesyntax.service.impl.CsvFileWriter;
import core.basesyntax.service.impl.DefaultShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.ShopOperationHandler;
import core.basesyntax.strategy.impl.ShopBalanceOperationHandler;
import core.basesyntax.strategy.impl.ShopOperationStrategy;
import core.basesyntax.strategy.impl.ShopPurchaseOperationHandler;
import core.basesyntax.strategy.impl.ShopReturnOperationHandler;
import core.basesyntax.strategy.impl.ShopSupplyOperationHandler;
import core.basesyntax.util.Mapper;
import core.basesyntax.util.ReportGenerator;
import core.basesyntax.util.impl.CsvShopReportGenerator;
import core.basesyntax.util.impl.ShopTransactionMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    private static final String INPUT_FILE = "src/main/resources/input-example.csv";
    private static final String OUTPUT_FILE = "src/main/resources/shop-report.csv";

    public static void main(String[] args) {
        Storage shopStorage = new ShopStorage();

        FileReader fileReader = new CsvFileReader();
        List<String> input = fileReader.read(INPUT_FILE);

        Map<ShopTransaction.Operation, ShopOperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(ShopTransaction.Operation.BALANCE, new ShopBalanceOperationHandler());
        operationHandlers.put(ShopTransaction.Operation.SUPPLY, new ShopSupplyOperationHandler());
        operationHandlers.put(ShopTransaction.Operation.PURCHASE,
                new ShopPurchaseOperationHandler());
        operationHandlers.put(ShopTransaction.Operation.RETURN, new ShopReturnOperationHandler());

        OperationStrategy operationStrategy = new ShopOperationStrategy(operationHandlers);
        ShopService shopService = new DefaultShopService(shopStorage, operationStrategy);

        Mapper<String, ShopTransaction> shopTransactionMapper = new ShopTransactionMapper();
        List<ShopTransaction> transactions = input.stream()
                .skip(1)
                .filter(line -> !line.isBlank())
                .map(shopTransactionMapper::map)
                .toList();
        shopService.process(transactions);

        ReportGenerator reportGenerator = new CsvShopReportGenerator();
        String report = reportGenerator.generate(shopStorage);

        FileWriter fileWriter = new CsvFileWriter();
        fileWriter.write(report, OUTPUT_FILE);
    }
}
