package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class TransactionExecutorTest {
    private static final String DEFAULT_VALID_FILE = "src/main/resources/example.csv";
    private FruitTransactionParserImpl fruitTransactionParser = new FruitTransactionParserImpl();
    private FileReaderCsv fileReaderCsv = new FileReaderCsv();

    @Test
    void execute_validData_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        TransactionExecutor transactionExecutor = new TransactionExecutor(strategyMap);
        transactionExecutor.execute(fruitTransactionParser.parse(fileReaderCsv
                .read(DEFAULT_VALID_FILE)));
        assertEquals(transactionExecutor.getStrategy()
                .get(FruitTransaction.Operation.BALANCE).getClass(), BalanceHandler.class);
    }
}
