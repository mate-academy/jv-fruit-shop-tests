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
    private static final int EXPECTED_BANANA_QUANTITY = 40;
    private static final int EXPECTED_APPLE_QUANTITY = 10;
    private Storage storage;
    private TransationProcessor processor;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        FruitService fruitService = new FruitServiceImpl(storage);
        SupplyOperationHandler supplyOperationHandler
                = new SupplyOperationHandler(storage, fruitService);
        ReturnOperationHandler returnOperationHandler
                = new ReturnOperationHandler(storage, fruitService);
        BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler(fruitService);
        PurchaseOperationHandler purchaseOperationHandler
                = new PurchaseOperationHandler(storage, fruitService);
        OperationStategy operationStategy;
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
        int actualAppleQuantity = storage.getFruitQuantity("apple");
        int actualBananaQuantity = storage.getFruitQuantity("banana");
        assertEquals(EXPECTED_APPLE_QUANTITY, actualAppleQuantity);
        assertEquals(EXPECTED_BANANA_QUANTITY, actualBananaQuantity);
    }
}
