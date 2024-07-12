package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.DataBase;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static ShopService service;

    @BeforeAll
    static void beforeAll() {
        FruitTransactionDao dao = new FruitTransactionDaoImpl();
        OperationStrategy strategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(dao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(dao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(dao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(dao)
        ));
        service = new ShopServiceImpl(strategy);
    }

    @Test
    void process_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("banana", 120);
        expectedMap.put("apple", 100);
        service.process(transactions);
        assertEquals(expectedMap, DataBase.getStorage());
    }
}
