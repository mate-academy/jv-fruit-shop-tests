package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.BalanceOperation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.DataConverterImpl;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.PurchaseOperation;
import core.basesyntax.service.ReturnOperation;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.SupplyOperation;
import core.basesyntax.util.FileReader;
import core.basesyntax.util.FileReaderImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        List<String> inputLines = fileReader.read("reportToRead.csv");

        DataConverter dataConverter = new DataConverterImpl();
        final List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputLines);

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategy(handlers);

        ShopService shopService = new ShopService(strategy);
        shopService.processTransactions(transactions);

        Storage.getAll().forEach((fruit, quantity) ->
                System.out.println(fruit + " : " + quantity));
    }

}
