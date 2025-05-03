package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpL;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.transactionsservice.OperationBalance;
import core.basesyntax.transactionsservice.OperationHandler;
import core.basesyntax.transactionsservice.OperationPurchase;
import core.basesyntax.transactionsservice.OperationReturn;
import core.basesyntax.transactionsservice.OperationSupply;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationGetterIpmlTest {
    private static FruitStorageDao fruitStorageDao = new FruitStorageDaoImpL();
    private static OperationGetter operationGetter;
    private Transaction transaction;
    private OperationHandler operationHandler;

    @BeforeAll
    static void init() {
        Map<Transaction.TransactionType, OperationHandler> transactionMap = new HashMap<>();
        transactionMap.put(Transaction.TransactionType.BALANCE,
                new OperationBalance(fruitStorageDao));
        transactionMap.put(Transaction.TransactionType.PURCHASE,
                new OperationPurchase(fruitStorageDao));
        transactionMap.put(Transaction.TransactionType.RETURN,
                new OperationReturn(fruitStorageDao));
        transactionMap.put(Transaction.TransactionType.SUPPLY,
                new OperationSupply(fruitStorageDao));
        operationGetter = new OperationGetterIpml(transactionMap);
    }

    @Test
    void getOperationValidInput() {
        transaction = new Transaction(Transaction.TransactionType.BALANCE,
                new Fruit("apple"),100);
        operationHandler = new OperationBalance(fruitStorageDao);
        assertEquals(operationHandler.getClass(),
                operationGetter.getOperation(transaction).getClass());
    }
}
