package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.DataHandlerStrategy;
import core.basesyntax.strategy.DataHandlerStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceDataHandler;
import core.basesyntax.strategy.handlers.DataHandler;
import core.basesyntax.strategy.handlers.PurchaseDataHandler;
import core.basesyntax.strategy.handlers.ReturnDataHandler;
import core.basesyntax.strategy.handlers.SupplyDataHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataHandlerStrategyTest {
    private DataHandlerStrategy dataHandlerStrategy;
    private Map<FruitTransaction.Operation, DataHandler> enumHandlerMap;

    @BeforeEach
    void setUp() {
        enumHandlerMap = new HashMap<>();
        DataHandler balance = new BalanceDataHandler();
        enumHandlerMap.put(FruitTransaction.Operation.BALANCE, balance);
        DataHandler returns = new ReturnDataHandler();
        enumHandlerMap.put(FruitTransaction.Operation.RETURN, returns);
        DataHandler purchase = new PurchaseDataHandler();
        enumHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchase);
        DataHandler supply = new SupplyDataHandler();
        enumHandlerMap.put(FruitTransaction.Operation.SUPPLY, supply);
        dataHandlerStrategy = new DataHandlerStrategyImpl(enumHandlerMap);
    }

    @Test
    void getHandler_isBalanceDataHeader_okay() {
        DataHandler balanceDataHandler = new BalanceDataHandler();
        DataHandler strategyDataHandler =
                dataHandlerStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(balanceDataHandler, strategyDataHandler);
    }

    @Test
    void getHandler_isSupplyDataHeader_okay() {
        DataHandler supplyDataHandler = new SupplyDataHandler();
        DataHandler strategyDataHandler =
                dataHandlerStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(supplyDataHandler, strategyDataHandler);
    }

    @Test
    void getHandler_isPurchaseDataHeader_okay() {
        DataHandler purchaseDataHandler = new PurchaseDataHandler();
        DataHandler strategyDataHandler =
                dataHandlerStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(purchaseDataHandler, strategyDataHandler);
    }

    @Test
    void getHandler_isReturnDataHeader_okay() {
        DataHandler returnDataHandler = new ReturnDataHandler();
        DataHandler strategyDataHandler =
                dataHandlerStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertEquals(returnDataHandler, strategyDataHandler);
    }
}
