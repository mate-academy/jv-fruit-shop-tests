package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.DataHandlerStrategy;
import core.basesyntax.strategy.DataHandlerStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceDataHandler;
import core.basesyntax.strategy.handlers.DataHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataHandlerStrategyTest {
    private static DataHandlerStrategy dataHandlerStrategy;
    private static Map<FruitTransaction.Operation, DataHandler> enumHandlerMap;

    @BeforeAll
    static void setHandlerMap() {
        enumHandlerMap = new HashMap<>();
        DataHandler balance = new BalanceDataHandler();
        enumHandlerMap.put(FruitTransaction.Operation.BALANCE, balance);
        dataHandlerStrategy = new DataHandlerStrategyImpl(enumHandlerMap);
    }

    @Test
    void getHandler_isBalanceDataHandler_okay() {
        DataHandler strategyDataHandler =
                dataHandlerStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceDataHandler.class, strategyDataHandler.getClass());
    }
}
