package core.basesyntax.store;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.OperationType;
import core.basesyntax.store.strategy.*;
import core.basesyntax.validator.quantity.QuantityValidator;
import core.basesyntax.validator.quantity.QuantityValidatorImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class TypeStrategyImplTest {
    private static TypeStrategy typeStrategy;
    private static Map<OperationType, TypeHandler> typeHandlerMap;
    private static final OperationType BALANCE = OperationType.B;

    @BeforeClass
    public static void beforeClass() {
        typeHandlerMap = new HashMap<>();
        FruitDao fruitDao = new FruitDaoImpl();
        QuantityValidator quantityValidator = new QuantityValidatorImpl();
        typeHandlerMap.put(OperationType.B, new BalanceHandler(fruitDao));
        typeHandlerMap.put(OperationType.S, new SupplyHandler(fruitDao));
        typeHandlerMap.put(OperationType.P, new PurchaseHandler(fruitDao, quantityValidator));
        typeHandlerMap.put(OperationType.R, new ReturnHandler(fruitDao));
        typeStrategy = new TypeStrategyImpl(typeHandlerMap);
    }

    @Test
    public void getTest_Ok() {
        TypeHandler expected = typeHandlerMap.get(BALANCE);
        TypeHandler actual = typeStrategy.get(BALANCE);
        assertEquals(expected, actual);
    }
}