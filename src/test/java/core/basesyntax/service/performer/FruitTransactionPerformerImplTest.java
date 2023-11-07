package core.basesyntax.service.performer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitQuantityDao;
import core.basesyntax.dao.FruitQuantityDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.BalanceOperationHandlerImpl;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseOperationHandlerImpl;
import core.basesyntax.operation.ReturnOperationHandlerImpl;
import core.basesyntax.operation.SupplyOperationHandlerImpl;
import core.basesyntax.service.parser.DataParser;
import core.basesyntax.service.parser.FruitTransactionDataParserImpl;
import core.basesyntax.service.validator.FruitQuantityValidatorImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionPerformerImplTest {
    private static final int ZERO_SIZE = 0;
    private static OperationStrategy operationStrategy;
    private static FruitQuantityDao fruitQuantityDao;
    private static Performer performer;
    private static List<FruitTransaction> transactions;
    private static DataParser parser;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, OperationHandler> operationOperationHandlerMap = new HashMap<>();
        fruitQuantityDao = new FruitQuantityDaoImpl();
        operationOperationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandlerImpl(
                fruitQuantityDao));
        operationOperationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandlerImpl(
                fruitQuantityDao));
        operationOperationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandlerImpl(
                fruitQuantityDao));
        operationOperationHandlerMap.put(Operation.RETURN, new ReturnOperationHandlerImpl(
                fruitQuantityDao));
        parser = new FruitTransactionDataParserImpl(new FruitQuantityValidatorImpl());
        operationStrategy = new OperationStrategyImpl(operationOperationHandlerMap);
        performer = new FruitTransactionPerformerImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        FruitStorage.fruitQuantity.clear();
    }

    @Test
    void performAll_validData_ok() {
        transactions = parser.parseData(List.of(
                "b,apple,130",
                "b,banana,30",
                "s,banana,40",
                "r,banana,40",
                "p,apple,100"
        ));
        performer.performAll(transactions);
        int bananaActualQuantity = fruitQuantityDao.get("banana");
        int appleActualQuantity = fruitQuantityDao.get("apple");
        int bananaExpectedQuantity = 110;
        int appleExpectedQuantity = 30;
        assertEquals(appleExpectedQuantity, appleActualQuantity);
        assertEquals(bananaExpectedQuantity, bananaActualQuantity);
    }

    @Test
    void performAll_emptyTransactionList_ok() {
        transactions = Collections.emptyList();
        performer.performAll(transactions);
        assertEquals(ZERO_SIZE, FruitStorage.fruitQuantity.size());
    }
}
