package core.basesyntax.service.impl;

import static core.basesyntax.model.Operation.BALANCE;
import static core.basesyntax.model.Operation.PURCHASE;
import static core.basesyntax.model.Operation.RETURN;
import static core.basesyntax.model.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.HandlerStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationProcessorImplTest {
    private static StorageDao storageDao = new StorageDaoImpl();
    private static HandlerStrategy strategy = new HandlerStrategy(storageDao);
    private static TransactionProcessor processor = new OperationProcessorImpl(strategy);

    private static List<FruitTransaction> transactionList = new ArrayList<>();
    private static Map<String, Integer> mockStorage = new HashMap<>();
    private static final Class<InvalidInputDataException> EXPECTED_EXCEPTION_CLASS
            = InvalidInputDataException.class;
    @BeforeAll
    static void setUp() {
        strategy.fillStrategyMap();
    }

    @AfterEach
    void clearInstances() {
        transactionList.clear();
        mockStorage.clear();
    }

    @Test
    void process_AllValidConditions_Ok() {
        transactionList.add(new FruitTransaction(BALANCE, "apple", 100));
        transactionList.add((new FruitTransaction(PURCHASE, "apple", 30)));
        transactionList.add((new FruitTransaction(RETURN, "apple", 10)));
        transactionList.add((new FruitTransaction(SUPPLY, "apple", 20)));
        transactionList.add(new FruitTransaction(BALANCE, "potato", 15));

        mockStorage.put("apple", 100);
        mockStorage.put("potato", 15);
        processor.process(transactionList);

        assertEquals(mockStorage, storageDao.getStorageState());
    }

    @Test
    void process_nullInput_throwsException() {
        assertThrows(EXPECTED_EXCEPTION_CLASS,
                () -> processor.process(null));
    }

    @Test
    void process_emptyListInput_throwsException() {
        assertThrows(EXPECTED_EXCEPTION_CLASS,
                () -> processor.process(new ArrayList<>()));
    }
}
