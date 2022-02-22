package core.basesyntax.service.strategy;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TransactionStrategyImplTest {
    private Map<String, TransactionHandler> handlerMap;
    private TransactionStrategy transactionStrategy;

    @Before
    public void setUp() {
        handlerMap = new HashMap<>();
        handlerMap.put("b", new TransactionBalance());
        handlerMap.put("p", new TransactionSell());
        handlerMap.put("r", new TransactionReturn());
        handlerMap.put("s", new TransactionSupply());
        transactionStrategy = new TransactionStrategyImpl(handlerMap);
    }

    @Test
    public void get_strategyTransactionHandler_ok() {
        assertEquals(transactionStrategy.get("b").getClass(), TransactionBalance.class);
        assertEquals(transactionStrategy.get("p").getClass(), TransactionSell.class);
        assertEquals(transactionStrategy.get("r").getClass(), TransactionReturn.class);
        assertEquals(transactionStrategy.get("s").getClass(), TransactionSupply.class);
    }

    @Test(expected = RuntimeException.class)
    public void get_wrongKeyForTransaction_notOk() {
        transactionStrategy.get("wrong key");
    }

    @Test(expected = RuntimeException.class)
    public void get_byNullValue_notOk() {
        transactionStrategy.get(null);
    }
}
