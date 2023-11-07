package fruitshop.operation;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.model.Operation;
import fruitshop.suppliers.OperationStrategySupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OperationHandlerTest {
    private OperationStrategy operationStrategy;

    @BeforeAll
    void setUp() {
        operationStrategy = new OperationStrategySupplier().get();
    }

    @Test
    void balanceOperation_Ok() {
        Operation operation = Operation.BALANCE;
        String fruit = "banana";
        int amount = 4;
        int newAmount = 10;
        Storage.setAmount(fruit, amount);

        FruitTransaction fruitTransaction = FruitTransaction.of(operation, fruit, newAmount);
        operationStrategy.get(fruitTransaction.getOperation()).accept(fruitTransaction);

        Assertions.assertEquals(Storage.getFruitsAndAmount().get(fruit), newAmount,
                operation
                        + " operation should set a new amount "
                        + newAmount
                        + " for "
                        + fruit
                        + " fruit. But it didn't.");
    }

    @Test
    void returnOperation_Ok() {
        Operation operation = Operation.RETURN;
        String fruit = "apple";
        int amount = 2;
        int returnedAmount = 10;
        int expectedResult = amount + returnedAmount;
        Storage.setAmount(fruit, amount);

        FruitTransaction fruitTransaction = FruitTransaction.of(operation,
                fruit,
                returnedAmount);
        operationStrategy.get(fruitTransaction.getOperation()).accept(fruitTransaction);

        Assertions.assertEquals(Storage.getFruitsAndAmount().get(fruit), expectedResult,
                operation
                        + " operation should increase stored amount of "
                        + amount
                        + " by "
                        + returnedAmount
                        + " for "
                        + fruit
                        + " fruit. But it didn't.");
    }

    @Test
    void supplyOperation_Ok() {
        Operation operation = Operation.SUPPLY;
        String fruit = "apple";
        int amount = 20;
        int suppliedAmount = 10;
        int expectedResult = amount + suppliedAmount;
        Storage.setAmount(fruit, amount);

        FruitTransaction fruitTransaction = FruitTransaction.of(operation,
                fruit,
                suppliedAmount);
        operationStrategy.get(fruitTransaction.getOperation()).accept(fruitTransaction);

        Assertions.assertEquals(Storage.getFruitsAndAmount().get(fruit), expectedResult,
                operation
                        + " operation should increase stored amount of "
                        + amount
                        + " by "
                        + suppliedAmount
                        + " for "
                        + fruit
                        + " fruit. But it didn't.");
    }

    @Test
    void purchaseOperation_Ok() {
        Operation operation = Operation.PURCHASE;
        String fruit = "apple";
        int amount = 8;
        int purchasedAmount = 4;
        int expectedResult = amount - purchasedAmount;
        Storage.setAmount(fruit, amount);

        FruitTransaction fruitTransaction = FruitTransaction.of(operation,
                fruit,
                purchasedAmount);
        operationStrategy.get(fruitTransaction.getOperation()).accept(fruitTransaction);

        Assertions.assertEquals(Storage.getFruitsAndAmount().get(fruit), expectedResult,
                operation
                        + " operation should decrease stored amount of "
                        + amount
                        + " by "
                        + purchasedAmount
                        + " for "
                        + fruit
                        + " fruit. But it didn't.");
    }

    @Test
    void purchaseWhenBalanceIsNotSufficient_NotOK() {
        Operation operation = Operation.PURCHASE;
        String fruit = "banana";
        int amount = 5;
        Storage.setAmount(fruit, amount);

        FruitTransaction fruitTransaction = FruitTransaction.of(operation,
                fruit,
                amount + 1);
        operationStrategy.get(fruitTransaction.getOperation()).accept(fruitTransaction);

        Assertions.assertEquals(Storage.getFruitsAndAmount().get(fruit), amount,
                "Amount of "
                        + fruit
                        + " was bigger while performing "
                        + operation
                        + " then it is in the storage, but operation still proceeded");
    }
}
