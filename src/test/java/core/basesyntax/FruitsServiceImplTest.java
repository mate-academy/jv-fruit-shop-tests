package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.calculationservice.FruitsServiceImpl;
import core.basesyntax.service.handler.OperationHandlerImpl;
import core.basesyntax.service.handler.OperationHandlers;
import core.basesyntax.service.handlerservice.HandlerService;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitsServiceImplTest {
    private FruitsServiceImpl fruitsService;
    private FruitsDao fruitsDao;
    private HandlerService handlerService;
    private OperationHandlers mockHandler;

    @BeforeEach
    void setUp() {
        // Очистка спільного сховища перед кожним тестом
        core.basesyntax.db.Storage.fruitStorage.clear();

        // Ініціалізація FruitsDao (реальна реалізація)
        fruitsDao = new FruitsDaoImpl();

        // Ініціалізація обробників для кожного типу операції
        Map<FruitTransaction.Operation, OperationHandlers> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new OperationHandlerImpl(),
                FruitTransaction.Operation.SUPPLY, new OperationHandlerImpl(),
                FruitTransaction.Operation.PURCHASE, new OperationHandlerImpl(),
                FruitTransaction.Operation.RETURN, new OperationHandlerImpl()
        );

        // Ініціалізація HandlerService з цими обробниками
        handlerService = new HandlerServiceImpl(handlers);

        // Створення сервісу з реальними залежностями
        fruitsService = new FruitsServiceImpl(fruitsDao, handlerService);
    }

    @Test
    void processTransactions_shouldProcessAllTransactionsCorrectly() {
        List<FruitTransaction> transactions = List.of(
                createTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                createTransaction(FruitTransaction.Operation.SUPPLY, "apple", 20),
                createTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                createTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );

        fruitsService.processTransactions(transactions);

        // Перевіряємо кінцеву кількість: 50 + 20 - 30 + 10 = 50
        Integer finalQuantity = fruitsDao.getAllFruits().get("apple");
        assertNotNull(finalQuantity);
        assertEquals(50, finalQuantity);
    }

    @Test
    void processTransactions_shouldThrowExceptionIfHandlerIsMissing() {
        // Створюємо сервіс без обробника для PURCHASE
        Map<FruitTransaction.Operation, OperationHandlers> partialHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new OperationHandlerImpl()
        );
        handlerService = new HandlerServiceImpl(partialHandlers);
        fruitsService = new FruitsServiceImpl(fruitsDao, handlerService);

        List<FruitTransaction> transactions = List.of(
                createTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10)
        );

        // Оскільки handler для PURCHASE відсутній, getHandler поверне null,
        // і спроба виклику handle() на null викличе NullPointerException.
        assertThrows(NullPointerException.class, () -> fruitsService
                .processTransactions(transactions));
    }

    @Test
    void processTransactions_shouldHandleEmptyListWithoutErrors() {
        assertDoesNotThrow(() -> fruitsService.processTransactions(List.of()));
    }

    private FruitTransaction createTransaction(FruitTransaction.Operation operation,
                                               String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

}
