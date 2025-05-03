package core.basesyntax;

import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.model.TransactionDto;
import core.basesyntax.fruitshop.service.operation.BalanceOperationHandler;
import core.basesyntax.fruitshop.service.operation.OperationHandler;
import core.basesyntax.fruitshop.service.operation.PurchaseOperationHandler;
import core.basesyntax.fruitshop.service.operation.ReturnOperationHandler;
import core.basesyntax.fruitshop.service.operation.SupplyOperationHandler;
import core.basesyntax.fruitshop.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationTest {
    private TransactionDto dtoFirst;
    private TransactionDto dtoSecond;
    private Fruit fruit;
    private OperationHandler toReturn;
    private OperationHandler purchase;
    private OperationHandler supply;
    private OperationHandler balance;

    @Before
    public void beforeAll() {
        supply = new SupplyOperationHandler();
        balance = new BalanceOperationHandler();
        purchase = new PurchaseOperationHandler();
        toReturn = new ReturnOperationHandler();
    }

    @Test
    public void operationBalance_IsOk() {
        fruit = new Fruit("banana");
        dtoFirst = new TransactionDto(OperationType.BALANCE, fruit, 58);
        balance.applyOperation(dtoFirst);
        int expected = 58;
        int actual = Storage.fruitBalance.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operationSupply_IfStorageIsEmpty_IsOk() {
        fruit = new Fruit("banana");
        dtoFirst = new TransactionDto(OperationType.SUPPLY, fruit, 36);
        supply.applyOperation(dtoFirst);
        int expected = 36;
        int actual = Storage.fruitBalance.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operationSupply_IfStorageIsNotEmpty_IsOk() {
        fruit = new Fruit("banana");
        dtoFirst = new TransactionDto(OperationType.SUPPLY, fruit, 36);
        dtoSecond = new TransactionDto(OperationType.BALANCE, fruit, 45);
        balance.applyOperation(dtoSecond);
        supply.applyOperation(dtoFirst);
        int expected = 81;
        int actual = Storage.fruitBalance.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operationPurchase_IsOk() {
        fruit = new Fruit("banana");
        dtoFirst = new TransactionDto(OperationType.PURCHASE, fruit, 23);
        dtoSecond = new TransactionDto(OperationType.BALANCE, fruit, 45);
        balance.applyOperation(dtoSecond);
        purchase.applyOperation(dtoFirst);
        int expected = 22;
        int actual = Storage.fruitBalance.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void operationPurchase_NotOk() {
        fruit = new Fruit("banana");
        dtoFirst = new TransactionDto(OperationType.PURCHASE, fruit, 23);
        dtoSecond = new TransactionDto(OperationType.BALANCE, fruit, 15);
        balance.applyOperation(dtoSecond);
        purchase.applyOperation(dtoFirst);
    }

    @Test
    public void operationReturn_IsOk() {
        fruit = new Fruit("banana");
        dtoFirst = new TransactionDto(OperationType.RETURN, fruit, 10);
        dtoSecond = new TransactionDto(OperationType.BALANCE, fruit, 45);
        balance.applyOperation(dtoSecond);
        toReturn.applyOperation(dtoFirst);
        int expected = 55;
        int actual = Storage.fruitBalance.get(fruit.getName());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruitBalance.clear();
    }
}
