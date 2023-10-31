package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Reader;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ReaderImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import core.basesyntax.service.impl.WriterImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILE_NAME_READ = "src/main/resources/fruitshop.csv";
    private static final String FILE_NAME_WRITE = "src/main/resources/balance.csv";
    private static final Reader reader = new ReaderImpl();
    private static final TransactionParser transactionParser = new TransactionParserImpl();
    private static final Writer writer = new WriterImpl();
    private static final ReportCreator creator = new ReportCreatorImpl();

    public static void main(String[] args) {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationHandlerMap.put("b", new BalanceOperation());
        operationHandlerMap.put("s", new SupplyOperation());
        operationHandlerMap.put("p", new PurchaseOperation());
        operationHandlerMap.put("r", new ReturnOperation());
        List<String> dataFromFile = reader.readFromFile(FILE_NAME_READ);
        List<FruitTransaction> transactions = transactionParser.getTransactions(dataFromFile);
        for (FruitTransaction transaction : transactions) {
            operationStrategy.get(transaction.getOperation().getCode())
                    .handleTransaction(transaction);
        }
        String content = creator.createReport();
        writer.writeToFile(FILE_NAME_WRITE, content);
    }
}
