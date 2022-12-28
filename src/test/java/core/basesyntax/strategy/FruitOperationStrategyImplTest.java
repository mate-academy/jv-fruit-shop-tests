package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransfer;
import core.basesyntax.operationhandler.BalanceOperationHandler;
import core.basesyntax.operationhandler.OperationHandler;
import core.basesyntax.operationhandler.PurchaseOperationHandler;
import core.basesyntax.operationhandler.ReturnOperationHandler;
import core.basesyntax.operationhandler.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitOperationStrategyImplTest {
    private static FruitOperationStrategy fruitOperationStrategy;

    @Before
    public void setUp() throws Exception {
        fruitOperationStrategy = new FruitOperationStrategyImpl(createMap());
    }

    @Test
    public void operation_Balance_IsOk() {
        Map<Fruit,Integer> expect = Map.of(new Fruit("avocado"),100);
        List<FruitTransfer> fruitTransfers = List.of(
                createFruitTransfer(FruitTransfer.Operation.BALANCE, "avocado", 100)
        );
        for (FruitTransfer fruitTransfer: fruitTransfers) {
            OperationHandler operationHandler = fruitOperationStrategy.get(
                    fruitTransfer.getOperation());
            operationHandler.apply(fruitTransfer);
        }
        Assert.assertEquals(expect,Storage.storageMap);
    }

    @Test
    public void operation_SupplyIsOk() {
        Map<Fruit,Integer> expect = Map.of(new Fruit("avocado"),100);
        List<FruitTransfer> fruitTransfers = List.of(
                createFruitTransfer(FruitTransfer.Operation.SUPPLY, "avocado", 100)
        );
        for (FruitTransfer fruitTransfer: fruitTransfers) {
            OperationHandler operationHandler = fruitOperationStrategy.get(
                    fruitTransfer.getOperation());
            operationHandler.apply(fruitTransfer);
        }
        Assert.assertEquals(expect,Storage.storageMap);
    }

    @Test
    public void operation_ReturnIsOk() {
        Map<Fruit,Integer> expect = Map.of(new Fruit("avocado"),100);
        List<FruitTransfer> fruitTransfers = List.of(
                createFruitTransfer(FruitTransfer.Operation.RETURN, "avocado", 100)
        );
        Storage.storageMap.put(new Fruit("avocado"),0);
        for (FruitTransfer fruitTransfer: fruitTransfers) {
            OperationHandler operationHandler = fruitOperationStrategy.get(
                    fruitTransfer.getOperation());
            operationHandler.apply(fruitTransfer);
        }
        Assert.assertEquals(expect,Storage.storageMap);
    }

    @Test
    public void operation_PurchaseIsOk() {
        Map<Fruit,Integer> expect = Map.of(new Fruit("avocado"),100);
        List<FruitTransfer> fruitTransfers = List.of(
                createFruitTransfer(FruitTransfer.Operation.PURCHASE, "avocado", 100)
        );
        Storage.storageMap.put(new Fruit("avocado"),200);
        for (FruitTransfer fruitTransfer: fruitTransfers) {
            OperationHandler operationHandler = fruitOperationStrategy.get(
                    fruitTransfer.getOperation());
            operationHandler.apply(fruitTransfer);
        }
        Assert.assertEquals(expect,Storage.storageMap);
    }

    @Test
    public void operation_PurchaseIsNotOk() {
        List<FruitTransfer> fruitTransfers = List.of(
                createFruitTransfer(FruitTransfer.Operation.BALANCE, "avocado", 100),
                createFruitTransfer(FruitTransfer.Operation.PURCHASE, "avocado", 105)
        );
        Assert.assertThrows("avocado,100 are not enough to by",
                RuntimeException.class, () -> {
                    fruitTransfers.forEach(s ->
                            fruitOperationStrategy.get(s.getOperation()).apply(s));
                });
    }

    private Map<FruitTransfer.Operation, OperationHandler> createMap() {
        Map<FruitTransfer.Operation, OperationHandler> operationStrategyMap = Map.of(
                FruitTransfer.Operation.BALANCE,new BalanceOperationHandler(),
                FruitTransfer.Operation.PURCHASE,new PurchaseOperationHandler(),
                FruitTransfer.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransfer.Operation.SUPPLY, new SupplyOperationHandler()
        );
        return operationStrategyMap;
    }

    private FruitTransfer createFruitTransfer(
            FruitTransfer.Operation operation, String fruit, int amount) {
        return new FruitTransfer(operation,new Fruit(fruit),amount);
    }

    @After
    public void clearMap() {
        Storage.storageMap.clear();
    }

}
