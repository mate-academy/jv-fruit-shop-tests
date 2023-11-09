package fruitshop.service;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.model.Operation;
import fruitshop.operation.OperationStrategy;
import fruitshop.service.impl.TransactionProcessorImpl;
import fruitshop.suppliers.OperationStrategySupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionProcessorTest {
    private TransactionProcessor transactionProcessorImpl;

    @BeforeAll
    void setUp() {
        OperationStrategy operationStrategy = new OperationStrategySupplier().get();
        transactionProcessorImpl = new TransactionProcessorImpl(operationStrategy);
    }

    @Test
    void process_WritesPurchaseToStorage_Ok() {
        Operation operation = Operation.PURCHASE;
        String fruit = "apple";
        int amount = 100;
        int purchasedAmount = 10;
        int expectedResult = amount - purchasedAmount;
        FruitTransaction newFruitTransaction = FruitTransaction.of(operation,
                fruit,
                purchasedAmount);

        Storage.setAmount(fruit, amount);
        transactionProcessorImpl.process(newFruitTransaction);
        int actual = Storage.getFruitsAndAmount().get(fruit);

        Assertions.assertEquals(actual, expectedResult,
                "Invalid amount. Should have been "
                        + expectedResult
                        + " but was "
                        + actual
                        + " after processing.");
    }

    @Test
    void process_WritesReturnToStorage_Ok() {
        Operation operation = Operation.RETURN;
        String fruit = "apple";
        int amount = 100;
        int returnedAmount = 10;
        int expectedResult = amount + returnedAmount;
        FruitTransaction newFruitTransaction = FruitTransaction.of(operation,
                fruit,
                returnedAmount);

        Storage.setAmount(fruit, amount);
        transactionProcessorImpl.process(newFruitTransaction);
        int actual = Storage.getFruitsAndAmount().get(fruit);

        Assertions.assertEquals(actual, expectedResult,
                "Invalid amount. Should have been "
                        + expectedResult
                        + " but was "
                        + actual
                        + " after processing.");
    }

    @Test
    void process_WritesSupplyToStorage_Ok() {
        Operation operation = Operation.SUPPLY;
        String fruit = "apple";
        int amount = 100;
        int suppliedAmount = 10;
        int expectedResult = amount + suppliedAmount;
        FruitTransaction newFruitTransaction = FruitTransaction.of(operation,
                fruit,
                suppliedAmount);

        Storage.setAmount(fruit, amount);
        transactionProcessorImpl.process(newFruitTransaction);
        int actual = Storage.getFruitsAndAmount().get(fruit);

        Assertions.assertEquals(actual, expectedResult,
                "Invalid amount. Should have been "
                        + expectedResult
                        + " but was "
                        + actual
                        + " after processing.");
    }

    @Test
    void process_WritesBalanceToStorage_Ok() {
        Operation operation = Operation.BALANCE;
        String fruit = "apple";
        int amount = 100;
        int expectedResult = 10;
        FruitTransaction newFruitTransaction = FruitTransaction.of(operation,
                fruit,
                expectedResult);

        Storage.setAmount(fruit, amount);
        transactionProcessorImpl.process(newFruitTransaction);
        int actual = Storage.getFruitsAndAmount().get(fruit);

        Assertions.assertEquals(actual, expectedResult,
                "Invalid amount. Should have been "
                        + expectedResult
                        + " but was "
                        + actual
                        + " after processing.");
    }
}
