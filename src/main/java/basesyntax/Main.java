package basesyntax;

import basesyntax.strategy.FruitBalance;
import basesyntax.strategy.FruitHandler;
import basesyntax.strategy.FruitPurchase;
import basesyntax.strategy.FruitReturn;
import basesyntax.strategy.FruitSupply;
import basesyntax.strategy.FruitsTransactionStrategy;
import basesyntax.strategy.FruitsTransactionStrategyImpl;
import java.util.Map;

public class Main {
    private static final String FILE_TO_READ_PATH = "src/main/resources/file.csv";
    private static final String FILE_TO_WRITE_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<String, FruitHandler> fruitHandlerMap = Map.of(
                FruitTransactionServiceImpl.Operation.BALANCE.getCode(), new FruitBalance(),
                FruitTransactionServiceImpl.Operation.PURCHASE.getCode(), new FruitPurchase(),
                FruitTransactionServiceImpl.Operation.SUPPLY.getCode(), new FruitSupply(),
                FruitTransactionServiceImpl.Operation.RETURN.getCode(), new FruitReturn()
        );

        FruitsTransactionStrategy fruitsTransactionStrategy = new FruitsTransactionStrategyImpl(
                fruitHandlerMap
        );

        FruitTransactionServiceImpl fruitTransaction = new FruitTransactionServiceImpl(
                fruitsTransactionStrategy
        );
        fruitTransaction.handleTransactions(FILE_TO_READ_PATH);
        fruitTransaction.writeReportToFile(FILE_TO_WRITE_PATH);
    }
}
