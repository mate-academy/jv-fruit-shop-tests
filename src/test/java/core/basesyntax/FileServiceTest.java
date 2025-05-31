package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.calculationservice.FruitsServiceImpl;
import core.basesyntax.service.handler.OperationHandlerImpl;
import core.basesyntax.service.handler.OperationHandlers;
import core.basesyntax.service.handlerservice.HandlerService;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileServiceTest {
    private FruitsServiceImpl fruitsService;
    private FruitsDao fruitsDao;

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
        Map<FruitTransaction.Operation, OperationHandlers> handlers = new HashMap<>();
        OperationHandlerImpl commonHandler = new OperationHandlerImpl();
        for (FruitTransaction.Operation op : FruitTransaction.Operation.values()) {
            handlers.put(op, commonHandler);
        }

        HandlerService handlerService = new HandlerServiceImpl(handlers);
        fruitsDao = new FruitsDaoImpl();
        fruitsService = new FruitsServiceImpl(fruitsDao, handlerService);
    }

    @Test
    void storageShouldBeEmptyInitially() {
        assertTrue(fruitsDao.getAllFruits().isEmpty());
    }

    @Test
    void processTransactions_shouldHandleAllOperationsCorrectly() {
        List<FruitTransaction> transactions = List.of(
                makeTx("banana", 100, FruitTransaction.Operation.BALANCE),
                makeTx("banana", 20, FruitTransaction.Operation.SUPPLY),
                makeTx("banana", 10, FruitTransaction.Operation.PURCHASE),
                makeTx("banana", 5, FruitTransaction.Operation.RETURN),
                makeTx("apple", 50, FruitTransaction.Operation.BALANCE),
                makeTx("apple", 15, FruitTransaction.Operation.PURCHASE)
        );

        fruitsService.processTransactions(transactions);

        Map<String, Integer> expected = Map.of(
                "banana", 100 + 20 - 10 + 5,
                "apple", 50 - 15
        );

        assertEquals(expected, fruitsDao.getAllFruits());
    }

    @Test
    void mapFruits_shouldGroupTransactionsByFruit() throws Exception {
        FruitTransaction tx1 = makeTx("banana", 10, FruitTransaction.Operation.SUPPLY);
        FruitTransaction tx2 = makeTx("apple", 20, FruitTransaction.Operation.SUPPLY);
        FruitTransaction tx3 = makeTx("banana", 5, FruitTransaction.Operation.RETURN);

        Method method = FruitsServiceImpl.class.getDeclaredMethod("mapFruits", List.class);
        method.setAccessible(true);

        Map<String, List<FruitTransaction>> grouped =
                (Map<String, List<FruitTransaction>>) method
                        .invoke(fruitsService, List.of(tx1, tx2, tx3));

        assertEquals(2, grouped.size());
        assertEquals(2, grouped.get("banana").size());
        assertEquals(1, grouped.get("apple").size());
    }

    @Test
    void processTransactions_shouldThrowException_whenNotEnoughStockForPurchase() {
        List<FruitTransaction> transactions = List.of(
                makeTx("banana", 5, FruitTransaction.Operation.BALANCE),
                makeTx("banana", 10, FruitTransaction.Operation.PURCHASE) // більше ніж є
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fruitsService.processTransactions(transactions));

        assertEquals("Not enough stock for purchase of banana", exception.getMessage());
    }

    private FruitTransaction makeTx(String fruit, int quantity, FruitTransaction
            .Operation operation) {
        FruitTransaction tx = new FruitTransaction();
        tx.setFruit(fruit);
        tx.setQuantity(quantity);
        tx.setOperation(operation);
        return tx;
    }

    @Test
    void processTransactions_shouldAddFruitOnBalanceOnly() {
        List<FruitTransaction> transactions = List.of(
                makeTx("kiwi", 77, FruitTransaction.Operation.BALANCE)
        );

        fruitsService.processTransactions(transactions);

        assertEquals(77, fruitsDao.getAllFruits().get("kiwi"));
    }

    @Test
    void processTransactions_shouldHandleReturnWithoutBalance() {
        List<FruitTransaction> transactions = List.of(
                makeTx("grape", 20, FruitTransaction.Operation.RETURN)
        );

        fruitsService.processTransactions(transactions);

        assertEquals(20, fruitsDao.getAllFruits().get("grape"));
    }

    @Test
    void processTransactions_shouldHandleZeroQuantityGracefully() {
        List<FruitTransaction> transactions = List.of(
                makeTx("peach", 0, FruitTransaction.Operation.BALANCE)
        );

        fruitsService.processTransactions(transactions);

        assertEquals(0, fruitsDao.getAllFruits().get("peach"));
    }

    @Test
    void processTransactions_shouldIgnoreEmptyTransactionList() {
        fruitsService.processTransactions(new ArrayList<>());
        assertTrue(fruitsDao.getAllFruits().isEmpty());
    }

    @Test
    void processTransactions_shouldOverwriteBalanceIfMultipleBalanceOpsExist() {
        List<FruitTransaction> transactions = List.of(
                makeTx("orange", 100, FruitTransaction.Operation.BALANCE),
                makeTx("orange", 200, FruitTransaction.Operation.BALANCE) // overwrite
        );

        fruitsService.processTransactions(transactions);

        assertEquals(200, fruitsDao.getAllFruits().get("orange"));
    }

    @Test
    void processTransactions_purchaseExactlyAllStock_shouldResultInZero() {
        List<FruitTransaction> transactions = List.of(
                makeTx("cherry", 30, FruitTransaction.Operation.BALANCE),
                makeTx("cherry", 30, FruitTransaction.Operation.PURCHASE)
        );

        fruitsService.processTransactions(transactions);

        assertEquals(0, fruitsDao.getAllFruits().get("cherry"));
    }

    @Test
    void processTransactions_returnAfterPurchase_shouldBeCorrectlyCounted() {
        List<FruitTransaction> transactions = List.of(
                makeTx("melon", 50, FruitTransaction.Operation.BALANCE),
                makeTx("melon", 30, FruitTransaction.Operation.PURCHASE),
                makeTx("melon", 10, FruitTransaction.Operation.RETURN)
        );

        fruitsService.processTransactions(transactions);

        assertEquals(30, fruitsDao.getAllFruits().get("melon"));
    }

    @Test
    void processTransactions_supplyWithoutInitialBalance_shouldAddValue() {
        List<FruitTransaction> transactions = List.of(
                makeTx("plum", 15, FruitTransaction.Operation.SUPPLY)
        );

        fruitsService.processTransactions(transactions);

        assertEquals(15, fruitsDao.getAllFruits().get("plum"));
    }

    @Test
    void processTransactions_mixedCaseOperations_shouldHandleConsistently() {
        List<FruitTransaction> transactions = List.of(
                makeTx("apple", 50, FruitTransaction.Operation.BALANCE),
                makeTx("apple", 20, FruitTransaction.Operation.PURCHASE),
                makeTx("apple", 10, FruitTransaction.Operation.RETURN)
        );

        fruitsService.processTransactions(transactions);

        assertEquals(40, fruitsDao.getAllFruits().get("apple"));
    }
}
