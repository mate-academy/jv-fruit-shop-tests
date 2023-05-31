package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.MovementType;
import core.basesyntax.storage.FruitDao;
import core.basesyntax.storage.impl.FruitDaoImpl;
import core.basesyntax.strategy.GoodHandler;
import core.basesyntax.strategy.Strategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class PostingStrategyTest {
    private static Strategy strategy;

    @BeforeClass
    public static void setUp() {
        strategy = new PostingStrategy(getHandlerMap(new FruitDaoImpl()));
    }

    @Test
    public void getHandlerForMovement_Balance_Ok() {
        GoodHandler actual = strategy.getHandlerForMovement(MovementType.BALANCE);
        assertEquals("Should return BalanceMovementHandler.",
                actual.getClass(), BalanceMovementHandler.class);
    }

    @Test
    public void getHandlerForMovement_Purchase_Ok() {
        GoodHandler actual = strategy.getHandlerForMovement(MovementType.PURCHASE);
        assertEquals("Should return PurchaseMovementHandler.",
                actual.getClass(), PurchaseMovementHandler.class);
    }

    @Test
    public void getHandlerForMovement_Return_Ok() {
        GoodHandler actual = strategy.getHandlerForMovement(MovementType.RETURN);
        assertEquals("Should return ReturnMovementHandler.",
                actual.getClass(), ReturnMovementHandler.class);
    }

    @Test
    public void getHandlerForMovement_Supply_Ok() {
        GoodHandler actual = strategy.getHandlerForMovement(MovementType.SUPPLY);
        assertEquals("Should return SupplyMovementHandler.",
                actual.getClass(), SupplyMovementHandler.class);
    }

    private static Map<MovementType, GoodHandler> getHandlerMap(FruitDao dao) {
        Map<MovementType, GoodHandler> strategies = new HashMap<>();
        strategies.put(MovementType.BALANCE, new BalanceMovementHandler(dao));
        strategies.put(MovementType.PURCHASE, new PurchaseMovementHandler(dao));
        strategies.put(MovementType.RETURN, new ReturnMovementHandler(dao));
        strategies.put(MovementType.SUPPLY, new SupplyMovementHandler(dao));
        return strategies;
    }
}
