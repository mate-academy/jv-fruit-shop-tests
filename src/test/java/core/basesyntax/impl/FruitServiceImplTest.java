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
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {

    private static final String outFileName = "src/test/java/resources/notCalculatedInstance.csv";
    private static final String comparedFileName = "src/test/java/resources/Calculated.csv";

    private static FruitServiceImpl fruitService;

    private static List<FruitTransaction> fruitTransactionList;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceStrategyOperationImpl(),
                FruitTransaction.Operation.SUPPLY, new SupplyStrategyOperationImpl(),
                FruitTransaction.Operation.PURCHASE, new PurchaseStrategyOperationImpl(),
                FruitTransaction.Operation.RETURN, new ReturnStrategyOperationImpl()
        );
        fruitService
                = new FruitServiceImpl(new OperationStrategyImpl(operationOperationHandlerMap));
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransactionList = new ArrayList<>();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction2.setFruit("banana");
        fruitTransaction2.setQuantity(50);
        fruitTransactionList.add(fruitTransaction);
        fruitTransactionList.add(fruitTransaction2);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void fruitService_calculateFruit_Ok() {
        fruitService.calculateFruit(fruitTransactionList);
        Integer expected = 50;
        Integer actual = Storage.fruits.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fruitService_emptyList_notOk() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        fruitService.calculateFruit(emptyList);
    }

    @Test(expected = RuntimeException.class)
    public void fruitService_nullList_notOk() {
        fruitService.calculateFruit(null);
    }
}
