package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceStrategyOperationImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseStrategyOperationImpl;
import core.basesyntax.strategy.impl.ReturnStrategyOperationImpl;
import core.basesyntax.strategy.impl.SupplyStrategyOperationImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {

    private static final String outFileName = "src/test/java/resources/notCalculatedInstance.csv";
    private static final String comparedFileName = "src/test/java/resources/Calculated.csv";
    private Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap;

    private FruitServiceImpl fruitService;

    private List<FruitTransaction> fruitTransactionList;
    private FruitTransaction fruitTransaction;
    private FruitTransaction fruitTransaction2;

    @BeforeEach
    void setUp() {
        operationOperationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceStrategyOperationImpl(),
                FruitTransaction.Operation.SUPPLY, new SupplyStrategyOperationImpl(),
                FruitTransaction.Operation.PURCHASE, new PurchaseStrategyOperationImpl(),
                FruitTransaction.Operation.RETURN, new ReturnStrategyOperationImpl()
        );
        fruitService
                = new FruitServiceImpl(new OperationStrategyImpl(operationOperationHandlerMap));
        fruitTransaction = new FruitTransaction();
        fruitTransaction2 = new FruitTransaction();
        fruitTransactionList = new ArrayList<>();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        fruitTransaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction2.setFruit("banana");
        fruitTransaction2.setQuantity(50);
        fruitTransactionList.add(fruitTransaction);
        fruitTransactionList.add(fruitTransaction2);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void fruitService_calculateFruit_Ok() {
        fruitService.calculateFruit(fruitTransactionList);
        Integer expected = 50;
        Integer actual = Storage.fruits.get("banana");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void fruitService_emptyList_notOk() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        Assertions.assertThrows(RuntimeException.class, ()
                -> fruitService.calculateFruit(emptyList));
    }

    @Test
    void fruitService_nullList_notOk() {
        Assertions.assertThrows(RuntimeException.class,()
                -> fruitService.calculateFruit(null));
    }
}
