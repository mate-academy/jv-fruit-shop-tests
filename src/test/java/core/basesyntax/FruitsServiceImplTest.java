package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.calculationservice.FruitService;
import core.basesyntax.service.calculationservice.FruitsServiceImpl;
import core.basesyntax.service.handler.BalanceOperation;
import core.basesyntax.service.handler.PurchaseOperation;
import core.basesyntax.service.handler.ReturnOperation;
import core.basesyntax.service.handler.SupplyOperation;
import core.basesyntax.service.handlerservice.HandlerService;
import core.basesyntax.service.handlerservice.HandlerServiceImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitsServiceImplTest {
    private FruitsDao fruitsDao;
    private HandlerService handlerService;
    private FruitService fruitService;

    @BeforeEach
    void setup() {
        Storage.fruitStorage.clear();
        fruitsDao = new FruitsDaoImpl();
        handlerService = new HandlerServiceImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        ));
        fruitService = new FruitsServiceImpl(fruitsDao, handlerService);
    }

    @Test
    void processTransactions_correctlyUpdatesStorage() {
        List<FruitTransaction> transactions = List.of(
                createTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                createTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                createTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );

        fruitService.processTransactions(transactions);
        assertEquals(80, fruitsDao.getAllFruits().get("apple"));

    }

    @Test
    void processTransactions_multipleFruits_updatesSeparately() {
        List<FruitTransaction> transactions = List.of(
                createTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                createTransaction(FruitTransaction.Operation.BALANCE, "banana", 40),
                createTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20),
                createTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10)
        );

        fruitService.processTransactions(transactions);
        assertEquals(40, fruitsDao.getAllFruits().get("apple"));
        assertEquals(60, fruitsDao.getAllFruits().get("banana"));
    }

    @Test
    void processTransactions_negativeResult_possibleWithPurchase() {
        List<FruitTransaction> transactions = List.of(
                createTransaction(FruitTransaction.Operation.BALANCE, "orange", 10),
                createTransaction(FruitTransaction.Operation.PURCHASE, "orange", 20)
        );

        fruitService.processTransactions(transactions);
        assertEquals(-10, fruitsDao.getAllFruits().get("orange"));
    }

    @Test
    void processTransactions_zeroNetEffect_resultIsZero() {
        List<FruitTransaction> transactions = List.of(
                createTransaction(FruitTransaction.Operation.BALANCE, "kiwi", 100),
                createTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 60),
                createTransaction(FruitTransaction.Operation.RETURN, "kiwi", 60),
                createTransaction(FruitTransaction.Operation.SUPPLY, "kiwi", 0)
        );

        fruitService.processTransactions(transactions);
        assertEquals(100, fruitsDao.getAllFruits().get("kiwi"));
    }

    @Test
    void processTransactions_emptyList_storageUnchanged() {
        fruitService.processTransactions(List.of());
        assertEquals(0, fruitsDao.getAllFruits().size());
    }

    @Test
    void processTransactions_onlyPurchases_negativeTotal() {
        List<FruitTransaction> transactions = List.of(
                createTransaction(FruitTransaction.Operation.PURCHASE, "grape", 50)
        );

        fruitService.processTransactions(transactions);
        assertEquals(-50, fruitsDao.getAllFruits().get("grape"));
    }

    private FruitTransaction createTransaction(FruitTransaction.Operation op,
                                               String fruit,
                                               int quantity) {
        FruitTransaction tx = new FruitTransaction();
        tx.setOperation(op);
        tx.setFruit(fruit);
        tx.setQuantity(quantity);
        return tx;
    }
}
