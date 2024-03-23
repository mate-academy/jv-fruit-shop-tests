package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.impl.BalanceOperationHandler;
import core.basesyntax.service.operation.impl.PurchaseOperationHandler;
import core.basesyntax.service.operation.impl.ReturnOperationHandler;
import core.basesyntax.service.operation.impl.SupplyOperationHandler;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationStategy;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStategyImplTest {
    private Storage storage;
    private FruitService fruitService;
    private SupplyOperationHandler supplyOperationHandler;
    private ReturnOperationHandler returnOperationHandler;
    private BalanceOperationHandler balanceOperationHandler;
    private PurchaseOperationHandler purchaseOperationHandler;
    private OperationStategy operationStategy;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruitService = new FruitServiceImpl(storage);
        returnOperationHandler = new ReturnOperationHandler(storage, fruitService);
        balanceOperationHandler = new BalanceOperationHandler(fruitService);
        purchaseOperationHandler = new PurchaseOperationHandler(storage, fruitService);
        supplyOperationHandler = new SupplyOperationHandler(storage, fruitService);
        Map<Operation, OperationHandler> operationStrategyMap =
                Map.of(Operation.BALANCE, balanceOperationHandler,
                        Operation.PURCHASE, purchaseOperationHandler,
                        Operation.SUPPLY, supplyOperationHandler,
                        Operation.RETURN, returnOperationHandler);
        operationStategy = new OperationStategyImpl(operationStrategyMap);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void get_dtoIsValid_isOk() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "apple", 10);
        OperationHandler actual = operationStategy.get(dto);
        assertEquals(purchaseOperationHandler, actual);
    }
}
