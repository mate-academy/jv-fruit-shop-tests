package core.basesyntax.service.performer;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.parser.Parser;
import core.basesyntax.service.parser.ParserImpl;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerformerImplTest {
    private static Parser parser;
    private static StorageDao storageDao;
    private static OperationStrategy operationStrategy;
    private static Performer performer;

    @BeforeEach
    void setUp() {
        parser = new ParserImpl();
        storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(storageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(storageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(storageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(storageDao));
        operationStrategy = new OperationStrategy(handlers);
        performer = new PerformerImpl(operationStrategy);
    }

    @Test
    void performValidData_Ok() {
        List<String> stringList = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "s,apple,13",
                "p,banana,20",
                "p,apple,30",
                "r,apple,1",
                "r,banana,1");
        List<FruitTransaction> list = parser.parseStringToFruitTransaction(stringList);
        performer.performTransaction(list);
        Assertions.assertEquals(101, storageDao.getQuantity("banana"));
        Assertions.assertEquals(84, storageDao.getQuantity("apple"));
    }

    @Test
    void performEmptyList_NotOk() {
        performer.performTransaction(Collections.EMPTY_LIST);
        Assertions.assertEquals(0, Storage.FRUITS.size());
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
