package core.basesyntax;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.RETURN;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.exception.MapOfHandlersForStrategyException;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationService;
import core.basesyntax.strategy.impl.BalanceOperationService;
import core.basesyntax.strategy.impl.IncomingOperationService;
import core.basesyntax.strategy.impl.MapOfHandlersForStrategyImpl;
import core.basesyntax.strategy.impl.OutgoingOperationService;
import core.basesyntax.testclasses.DaoStorageForTest;
import core.basesyntax.testclasses.OperationServiceForTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapOfHandlersForStrategyTest {
    private static DaoStorage daoStorageForTest;
    private static OperationService operationServiceForTest;
    private static Map<FruitTransaction.Operation, OperationService> alternativeMap;
    private MapOfHandlersForStrategy mapForStrategy;
    private int defaultSizeHandlers;

    @BeforeAll
    public static void setUp() {
        daoStorageForTest = new DaoStorageForTest();
        operationServiceForTest = new OperationServiceForTest();
    }

    @BeforeEach
    public void beforeTest() {
        alternativeMap = Map.of(BALANCE,
                new OperationServiceForTest());
        mapForStrategy = new MapOfHandlersForStrategyImpl(daoStorageForTest);
        defaultSizeHandlers = mapForStrategy.getHandlers().size();
    }

    @Test
    public void mapOfHandlersForStrategy_putNullHandlers_notOk() {
        assertAll("Test failed! Map shouldn't be contains "
                        + "handler with NULL.",
                () -> assertThrows(MapOfHandlersForStrategyException.class,
                        () -> mapForStrategy.putHandler(null, operationServiceForTest)),
                () -> assertThrows(MapOfHandlersForStrategyException.class,
                        () -> mapForStrategy.putHandler(RETURN, null)),
                () -> assertEquals(defaultSizeHandlers, mapForStrategy.getHandlers().size())
        );
    }

    @Test
    public void mapOfHandlersForStrategy_daoStorageNull_notOk() {
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> new MapOfHandlersForStrategyImpl(null));
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> new MapOfHandlersForStrategyImpl(alternativeMap,null));
    }

    @Test
    public void mapOfHandlersForStrategy_getEmptyHandlers_notOk() {
        mapForStrategy.removeHandler(BALANCE);
        mapForStrategy.removeHandler(SUPPLY);
        mapForStrategy.removeHandler(RETURN);
        mapForStrategy.removeHandler(PURCHASE);
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> mapForStrategy.getHandlers());
    }

    @Test
    public void mapOfHandlersForStrategy_removeHandlerArgumentNull_notOk() {
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> mapForStrategy.removeHandler(null));
    }

    @Test
    public void mapOfHandlersForStrategy_removeHandler_Ok() {
        mapForStrategy.removeHandler(BALANCE);
        assertAll("Test failed! List shouldn't be contains handler "
                        + BALANCE + " after remove this key.",
                () -> assertFalse(mapForStrategy.getHandlers().containsKey(BALANCE)),
                () -> assertEquals(defaultSizeHandlers - 1, mapForStrategy.getHandlers().size())
        );
    }

    @Test
    public void mapOfHandlersForStrategy_getDefaultHandlers_Ok() {
        assertEquals(4, defaultSizeHandlers);
        assertEquals(IncomingOperationService.class,
                mapForStrategy.getHandlers().get(SUPPLY).getClass());
        assertEquals(IncomingOperationService.class,
                mapForStrategy.getHandlers().get(RETURN).getClass());
        assertEquals(BalanceOperationService.class,
                mapForStrategy.getHandlers().get(BALANCE).getClass());
        assertEquals(OutgoingOperationService.class,
                mapForStrategy.getHandlers().get(PURCHASE).getClass());
    }

    @Test
    public void mapOfHandlersForStrategy_putHandler_Ok() {
        mapForStrategy.putHandler(RETURN, operationServiceForTest);
        assertAll("Test failed! List shouldn't be contains handler "
                        + RETURN + " after put this key with new balance operation.",
                () -> assertEquals(defaultSizeHandlers, mapForStrategy.getHandlers().size()),
                () -> assertEquals(operationServiceForTest,
                        mapForStrategy.getHandlers().get(RETURN))
        );
    }

    @Test
    public void mapOfHandlersForStrategy_alternativeMapNullOrEmpty_notOk() {
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> new MapOfHandlersForStrategyImpl(null, daoStorageForTest));
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> new MapOfHandlersForStrategyImpl(new HashMap<>(), daoStorageForTest));
    }

    @Test
    public void mapOfHandlersForStrategy_alternativeMap_Ok() {
        OperationService actual = new MapOfHandlersForStrategyImpl(alternativeMap,
                daoStorageForTest).getHandlers().get(BALANCE);
        assertEquals(actual.getClass(), OperationServiceForTest.class);
    }
}
