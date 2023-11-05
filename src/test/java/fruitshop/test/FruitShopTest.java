package fruitshop.test;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.FruitTransaction;
import model.Operation;
import operation.OperationHandler;
import operation.impl.BalanceOperationHandlerImpl;
import operation.impl.OperationStrategyImpl;
import operation.impl.PurchaseOperationHandlerImpl;
import operation.impl.ReturnOperationHandlerImpl;
import operation.impl.SupplyOperationHandlerImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import service.Parser;
import service.TransactionProcessor;
import service.impl.CsvParser;
import service.impl.ReaderServiceImpl;
import service.impl.TransactionProcessorImpl;
import service.impl.WriterServiceImpl;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FruitShopTest {
    private static final int FIRST_SYMBOL_INDEX = 0;
    private static final int DECREASED_INDEX = 2;
    private static final String READ_TEST_FILE_NAME = "testData.csv";
    private static final String WRITE_TEST_FILE_NAME = "testFile.csv";
    private Map<Operation, OperationHandler> operationHandlers;
    private OperationStrategyImpl operationStrategy;
    private Parser csvParser;
    private ReaderServiceImpl reader;
    private WriterServiceImpl writer;
    private TransactionProcessor transactionProcessorImpl;

    @BeforeAll
    void setUp() {
        writer = new WriterServiceImpl();
        reader = new ReaderServiceImpl();
        csvParser = new CsvParser();
        operationHandlers = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlers);

        operationHandlers.put(Operation.BALANCE, new BalanceOperationHandlerImpl());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperationHandlerImpl());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        operationHandlers.put(Operation.RETURN, new ReturnOperationHandlerImpl());

        transactionProcessorImpl = new TransactionProcessorImpl(operationStrategy);
    }

    @Test
    void fruitTransactionWithNegativeAmount_NotOk() {
        int negativeAmount = -1;
        Assert.assertThrows(RuntimeException.class,
                () -> new FruitTransaction(Operation.RETURN, "banana", negativeAmount));
    }

    @Test
    void readerServiceImplWorks_Ok() {
        String[] expectedArray = {
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,150",
                "p,banana,13",
                "r,apple,10"};
        List<String> expected = List.of(expectedArray);

        Assertions.assertEquals(reader.readFromFile(READ_TEST_FILE_NAME),
                expected,
                "ReaderServiceImpl doesn't work properly");
    }

    @Test
    void operationStrategySuppliesRightImpl_Ok() {
        Operation purchaseOperation = Operation.PURCHASE;
        Class<PurchaseOperationHandlerImpl> expectedOperation = PurchaseOperationHandlerImpl.class;
        Class<? extends OperationHandler> actualOperation = operationStrategy.get(purchaseOperation)
                .getClass();
        String message = "Operation strategy should have returned "
                + expectedOperation.getName()
                + " but returned "
                + actualOperation.getName();

        Assertions.assertTrue(expectedOperation.isAssignableFrom(operationStrategy
                        .get(purchaseOperation)
                        .getClass()),
                message);
    }

    @Test
    void balanceOperation_Ok() {
        Operation operation = Operation.BALANCE;
        String fruit = "banana";
        int amount = 4;
        int newAmount = 10;
        Storage.setAmount(fruit, amount);

        FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, newAmount);
        operationStrategy.get(fruitTransaction.operation()).accept(fruitTransaction);

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

        FruitTransaction fruitTransaction = new FruitTransaction(operation,
                fruit,
                returnedAmount);
        operationStrategy.get(fruitTransaction.operation()).accept(fruitTransaction);

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

        FruitTransaction fruitTransaction = new FruitTransaction(operation,
                fruit,
                suppliedAmount);
        operationStrategy.get(fruitTransaction.operation()).accept(fruitTransaction);

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

        FruitTransaction fruitTransaction = new FruitTransaction(operation,
                fruit,
                purchasedAmount);
        operationStrategy.get(fruitTransaction.operation()).accept(fruitTransaction);

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

        FruitTransaction fruitTransaction = new FruitTransaction(operation,
                fruit,
                amount + 1);
        operationStrategy.get(fruitTransaction.operation()).accept(fruitTransaction);

        Assertions.assertEquals(Storage.getFruitsAndAmount().get(fruit), amount,
                "Amount of "
                        + fruit
                        + " was bigger while performing "
                        + operation
                        + " then it is in the storage, but operation still proceeded");
    }

    @Test
    void transactionProcessorImplProcessesPurchase_Ok() {
        Operation operation = Operation.PURCHASE;
        String fruit = "apple";
        int amount = 100;
        int purchasedAmount = 10;
        int expectedResult = amount - purchasedAmount;
        FruitTransaction newFruitTransaction = new FruitTransaction(operation,
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
    void transactionProcessorImplProcessesReturn_Ok() {
        Operation operation = Operation.RETURN;
        String fruit = "apple";
        int amount = 100;
        int returnedAmount = 10;
        int expectedResult = amount + returnedAmount;
        FruitTransaction newFruitTransaction = new FruitTransaction(operation,
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
    void transactionProcessorImplProcessesSupply_Ok() {
        Operation operation = Operation.SUPPLY;
        String fruit = "apple";
        int amount = 100;
        int suppliedAmount = 10;
        int expectedResult = amount + suppliedAmount;
        FruitTransaction newFruitTransaction = new FruitTransaction(operation,
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
    void transactionProcessorImplProcessesBalance_Ok() {
        Operation operation = Operation.BALANCE;
        String fruit = "apple";
        int amount = 100;
        int expectedResult = 10;
        FruitTransaction newFruitTransaction = new FruitTransaction(operation,
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

    @Test
    void writerServiceImpl_Ok() {
        String expected = "TEST TEXT"
                + System.lineSeparator()
                + "12345";
        writer.writeToFile(expected, WRITE_TEST_FILE_NAME);
        String actual = reader.readFromFile(WRITE_TEST_FILE_NAME)
                .stream()
                .map(string -> string + System.lineSeparator())
                .collect(Collectors.joining());
        actual = actual.substring(FIRST_SYMBOL_INDEX,
                actual.length() - DECREASED_INDEX);

        Assertions.assertEquals(actual, expected,
                "Writer should have written "
                        + expected
                        + " but wrote "
                        + actual);
    }
}
