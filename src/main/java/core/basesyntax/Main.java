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
import core.basesyntax.util.FileWriter;
import core.basesyntax.util.FileWriterImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_PATH = "src/main/resources/reportToRead.csv";
    private static final String OUTPUT_FIlE_PATH = "src/main/resources/reportToComplete.csv";

    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        List<String> inputLines = fileReader.read(INPUT_FILE_PATH);

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

        StringBuilder reportBuilder = new StringBuilder("fruit,quantity\n");
        Storage.getAll().forEach((fruit, quantity) ->
                reportBuilder.append(fruit).append(",").append(quantity).append("\n"));

        String report = reportBuilder.toString();

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(OUTPUT_FIlE_PATH, report);
    }

}
