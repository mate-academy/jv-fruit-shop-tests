package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.storage.FruitStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> STRATEGY_MAP
            = new HashMap<>();
    private static final FruitShopService FRUIT_SHOP_SERVICE
            = new FruitShopServiceImpl(STRATEGY_MAP);
    private static final List<FruitTransaction> LIST = new ArrayList<>();
    private static final List<FruitTransaction> NEXT_OCCURENCE_BALANCELIST = new ArrayList<>();
    private static final String VALID_EXCEPTION_MESSAGE =
            "Balance cannot be changed during the day";
    private static final String VALID_FRUIT_BANANA = "banana";
    private static final String VALID_FRUIT_APPLE = "apple";
    private static final int VALID_BANANA_QUANTITY = 20;
    private static final int VALID_APPLE_QUANTITY = 80;

    @BeforeAll
    static void beforeAll() {
        LIST.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        LIST.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 80));
        NEXT_OCCURENCE_BALANCELIST.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        NEXT_OCCURENCE_BALANCELIST.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100));
        STRATEGY_MAP.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
    }

    @BeforeEach
    void setUp() {
        FruitStorage.STORAGE_MAP.clear();
    }

    @Test
    void processAll_validInput_isOk() {
        FRUIT_SHOP_SERVICE.processAll(LIST);
        assertEquals(VALID_BANANA_QUANTITY,
                FruitStorage.STORAGE_MAP.get(VALID_FRUIT_BANANA));
        assertEquals(VALID_APPLE_QUANTITY,
                FruitStorage.STORAGE_MAP.get(VALID_FRUIT_APPLE));
    }

    @Test
    void processAll_nextOccuranceBalance_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            FRUIT_SHOP_SERVICE.processAll(NEXT_OCCURENCE_BALANCELIST);
        });
        assertEquals(VALID_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @AfterAll
    static void afterAll() {
        FruitStorage.STORAGE_MAP.clear();
    }
}
