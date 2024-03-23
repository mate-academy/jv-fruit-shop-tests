package core.basesyntax.strategy;

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
import core.basesyntax.strategy.impl.OperationStategyImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransationProcessorImplTest {
    private Storage storage;
    private FruitService fruitService;
    private SupplyOperationHandler supplyOperationHandler;
    private ReturnOperationHandler returnOperationHandler;
    private BalanceOperationHandler balanceOperationHandler;
    private PurchaseOperationHandler purchaseOperationHandler;
    private OperationStategy operationStategy;
    private TransationProcessor processor;

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
        processor = new TransationProcessorImpl(operationStategy);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void process_dtoIsValid_isOk() {
        List<FruitTransactionDto> transactions = Arrays.asList(
                    new FruitTransactionDto("b", "apple", 10),
                    new FruitTransactionDto("b", "banana",20),
                    new FruitTransactionDto("p", "apple", 10),
                    new FruitTransactionDto("r", "apple", 10),
                    new FruitTransactionDto("s", "banana", 20));

        processor.process(transactions);
        int actual1 = storage.getFruitQuantity("apple");
        int actual2 = storage.getFruitQuantity("banana");
        assertEquals(10, actual1);
        assertEquals(40, actual2);
    }
}
