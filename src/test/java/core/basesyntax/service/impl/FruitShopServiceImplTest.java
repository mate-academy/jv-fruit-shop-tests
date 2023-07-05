package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ParserOperationService;
import core.basesyntax.service.operation.OperationHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static final List<String> VALID_DATA =
            Arrays.asList("b,banana,20",
                    "s,banana,100",
                    "p,banana,13",
                    "r,apple,10");
    private OperationStrategyImpl operationStrategy;
    private FruitShopService fruitShopService;
    private final FruitTransaction fruitTransaction = new FruitTransaction();
    private List<FruitTransaction> fruitTransactionList;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void test_Get_WhenOperationsIsNull_NotOk() {
        fruitTransactionList = null;
        assertThrows(NullPointerException.class,
                () -> fruitShopService.processOfOperations(fruitTransactionList));
    }

    @Test
    public void test_Get_WhenOperationsIsEmpty_NotOk() {
        fruitTransactionList = new ArrayList<>();
        assertThrows(NullPointerException.class,
                () -> fruitShopService.processOfOperations(fruitTransactionList));
    }

    @Test
    public void test_Get_UnknownOperations_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fruitTransaction.setOperation(FruitTransaction
                        .Operation.getOperationByCode("pa")));
    }

    @Test
    public void test_Get_OperationHandler_For_Each_Operation_Ok() {
        ParserOperationService parserOperationService = new ParserOperationServiceImpl();
        List<FruitTransaction> fruitTransactionList =
                parserOperationService.parser(VALID_DATA);
        for (FruitTransaction current : fruitTransactionList) {
            FruitTransaction.Operation operation = current.getOperation();
            OperationHandler actualHeandler = operationStrategy.get(operation);
            assertNotNull(actualHeandler,
                    "Operation handler should not be null for operation: "
                            + operation);
            assertInstanceOf(OperationHandler.class, actualHeandler,
                    "Operation handler should be an instance of OperationHandler"
                            + " for operation: " + operation);
        }
    }
}
