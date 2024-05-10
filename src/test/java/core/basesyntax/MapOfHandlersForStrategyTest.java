package core.basesyntax;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.RETURN;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.exception.MapOfHandlersForStrategyException;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationService;
import core.basesyntax.strategy.impl.BalanceOperationService;
import core.basesyntax.strategy.impl.IncomingOperationService;
import core.basesyntax.strategy.impl.MapOfHandlersForStrategyImpl;
import core.basesyntax.strategy.impl.OutgoingOperationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapOfHandlersForStrategyTest {
    private static final int EXPECTED_SIZE = 4;
    private static Map<String, Integer> storageForTest;
    private static Map<FruitTransaction.Operation, OperationService> alternativeMapOfHandlers;
    private static DaoStorage daoStorageForTest;
    private static OperationService operationServiceForTest;
    private MapOfHandlersForStrategy mapForStrategy;
    private int defaultSizeHandlers;

    @BeforeAll
    public static void setUp() {
        storageForTest = new HashMap<>();
        operationServiceForTest = fruitTransaction -> {};
        daoStorageForTest = new DaoStorage() {
            @Override
            public void setNewValue(String fruit, Integer quantity) {
                storageForTest.put(fruit, quantity);
            }

            @Override
            public void concatenateValue(String fruit, Integer quantity) {
                storageForTest.merge(fruit, quantity, Integer::sum);
            }

            @Override
            public int getValue(String fruit) {
                return storageForTest.get(fruit);
            }

            @Override
            public Set<Map.Entry<String, Integer>> getStatistic() {
                return storageForTest.entrySet();
            }

            @Override
            public void clear() {
                storageForTest.clear();
            }
        };

    }

    @BeforeEach
    public void beforeTest() {
        alternativeMapOfHandlers = Map.of(BALANCE, operationServiceForTest);
        mapForStrategy = new MapOfHandlersForStrategyImpl(daoStorageForTest);
        defaultSizeHandlers = mapForStrategy.getHandlers().size();
    }

    @Test
    public void mapOfHandlersForStrategy_daoStorageNull_notOk() {
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> new MapOfHandlersForStrategyImpl(null));
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> new MapOfHandlersForStrategyImpl(alternativeMapOfHandlers,null));
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
        MapOfHandlersForStrategy actual = new MapOfHandlersForStrategyImpl(
                alternativeMapOfHandlers, daoStorageForTest);

        assertAll("Test failed! Alternative Map should be contains handler " + BALANCE
                        + " with new service operation.",
                () -> assertTrue(actual.getHandlers().containsKey(BALANCE)),
                () -> assertEquals(operationServiceForTest, actual.getHandlers().get(BALANCE))
        );
    }

    @Test
    public void mapOfHandlersForStrategy_getEmptyHandlers_notOk() {
        List.of(BALANCE, SUPPLY, RETURN, PURCHASE).forEach(operation
                -> mapForStrategy.removeHandler(operation));

        assertThrows(MapOfHandlersForStrategyException.class,
                () -> mapForStrategy.getHandlers());
    }

    @Test
    public void mapOfHandlersForStrategy_getDefaultHandlers_Ok() {
        Map.of(BALANCE, BalanceOperationService.class,
                SUPPLY, IncomingOperationService.class,
                RETURN, IncomingOperationService.class,
                PURCHASE, OutgoingOperationService.class).forEach(((operation, serviceClass) ->
                assertAll("Test failed! Default Map should be contains " + operation,
                        () -> assertTrue(mapForStrategy.getHandlers().containsKey(operation)),
                        () -> assertEquals(serviceClass, mapForStrategy.getHandlers()
                                .get(operation).getClass())
                )));
        assertEquals(EXPECTED_SIZE, defaultSizeHandlers);
    }

    @Test
    public void mapOfHandlersForStrategy_putNullHandler_notOk() {
        assertAll("Test failed! Map shouldn't be contains " + RETURN + " handler with NULL.",
                () -> assertThrows(MapOfHandlersForStrategyException.class,
                        () -> mapForStrategy.putHandler(RETURN, null)),
                () -> assertNotNull(mapForStrategy.getHandlers().get(RETURN)),
                () -> assertEquals(defaultSizeHandlers, mapForStrategy.getHandlers().size())
        );
        assertAll("Test failed! Map shouldn't be contains handler NULL.",
                () -> assertThrows(MapOfHandlersForStrategyException.class,
                        () -> mapForStrategy.putHandler(null, operationServiceForTest)),
                () -> assertFalse(mapForStrategy.getHandlers().containsKey(null)),
                () -> assertEquals(defaultSizeHandlers, mapForStrategy.getHandlers().size())
        );
    }

    @Test
    public void mapOfHandlersForStrategy_putHandler_Ok() {
        mapForStrategy.putHandler(RETURN, operationServiceForTest);

        assertAll("Test failed! Map should be contains handler " + RETURN
                        + " with new service operation.",
                () -> assertEquals(defaultSizeHandlers, mapForStrategy.getHandlers().size()),
                () -> assertTrue(mapForStrategy.getHandlers().containsKey(RETURN)),
                () -> assertEquals(operationServiceForTest,
                        mapForStrategy.getHandlers().get(RETURN))
        );
    }

    @Test
    public void mapOfHandlersForStrategy_removeHandlerArgumentNull_notOk() {
        assertThrows(MapOfHandlersForStrategyException.class,
                () -> mapForStrategy.removeHandler(null));
    }

    @Test
    public void mapOfHandlersForStrategy_removeHandler_Ok() {
        mapForStrategy.removeHandler(BALANCE);

        assertAll("Test failed! Map shouldn't be contains handler "
                        + BALANCE + " after remove this key.",
                () -> assertFalse(mapForStrategy.getHandlers().containsKey(BALANCE)),
                () -> assertEquals(--defaultSizeHandlers, mapForStrategy.getHandlers().size())
        );
    }
}
