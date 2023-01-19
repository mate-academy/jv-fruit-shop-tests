package core.basesyntax;

import core.basesyntax.service.FileService;
import core.basesyntax.service.FileServiceImpl;
import core.basesyntax.service.StoreService;
import core.basesyntax.service.StoreServiceImpl;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.TransactionParserImpl;
import core.basesyntax.transactionhandler.BalanceTransactionHandler;
import core.basesyntax.transactionhandler.PurchaseTransactionHandler;
import core.basesyntax.transactionhandler.ReturnSupplyTransactionHandler;
import core.basesyntax.transactionhandler.TransactionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_PATH = "src/main/resources/input.csv";
    private static final String OUTPUT_PATH = "src/main/java/core/basesyntax/resources/output";

    public static void main(String[] args) {
        Map<String, TransactionHandler> transactionStrategyMap = new HashMap<>();
        transactionStrategyMap.put("b", new BalanceTransactionHandler());
        transactionStrategyMap.put("s", new ReturnSupplyTransactionHandler());
        transactionStrategyMap.put("p", new PurchaseTransactionHandler());
        transactionStrategyMap.put("r", new ReturnSupplyTransactionHandler());

        FileService file = new FileServiceImpl();
        List<String> valueFromFile = file.readFromFile(INPUT_PATH);
        TransactionParser validate = new TransactionParserImpl();

        valueFromFile.stream()
                .map(validate::parse)
                .forEach(t -> transactionStrategyMap.get(t.getTransactionName()).operate(t));

        StoreService storeService = new StoreServiceImpl();
        String report = storeService.getReport();

        file.writeToFile(OUTPUT_PATH, report);
    }
}
