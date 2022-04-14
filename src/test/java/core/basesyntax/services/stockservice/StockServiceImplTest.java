package core.basesyntax.services.stockservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.OperationTypes;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.services.operations.Operation;
import core.basesyntax.services.operations.OperationBalanceImpl;
import core.basesyntax.services.operations.OperationException;
import core.basesyntax.services.operations.OperationPurchaseImpl;
import core.basesyntax.services.operations.OperationReturnImpl;
import core.basesyntax.services.operations.OperationSupplyImpl;
import core.basesyntax.services.operations.strategy.OperationsStrategy;
import core.basesyntax.services.operations.strategy.OperationsStrategyImpl;
import core.basesyntax.storage.Stock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StockServiceImplTest {
    private static OperationsStrategy strategyOperations;

    @BeforeClass
    public static void beforeClass() {
        Map<String, Operation> stringOperationMap = new HashMap<>();
        stringOperationMap.put(OperationTypes.BALANCE.getShortName(), new OperationBalanceImpl());
        stringOperationMap.put(OperationTypes.SUPPLY.getShortName(), new OperationSupplyImpl());
        stringOperationMap.put(OperationTypes.PURCHASE.getShortName(), new OperationPurchaseImpl());
        stringOperationMap.put(OperationTypes.RETURN.getShortName(), new OperationReturnImpl());
        strategyOperations = new OperationsStrategyImpl(stringOperationMap);
    }

    @After
    public void tearDown() {
        Stock.stockStorage.clear();
    }

    @Test
    public void applyOperationsOnFruitsDto_FirstOperationNotBalance_NotOk() {
        TransactionDto transactionDto = new TransactionDto(
                "p", "banana", 50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(transactionDto.getFruit(), 0);
        StockService stockService = new StockServiceImpl(strategyOperations);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto);
        stockService.applyOperationsOnFruitsDto(transactionDtoList);
        Map<String, Integer> actual = Stock.stockStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void applyOperationsOnFruitsDto_OperationBalance_Ok() {
        TransactionDto transactionDto = new TransactionDto(
                "b", "banana", 50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(transactionDto.getFruit(), transactionDto.getAmount());
        StockService stockService = new StockServiceImpl(strategyOperations);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto);
        stockService.applyOperationsOnFruitsDto(transactionDtoList);
        Map<String, Integer> actual = Stock.stockStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void applyOperationsOnFruitsDto_OperationSupply_Ok() {
        TransactionDto transactionDto = new TransactionDto(
                "b", "banana", 50);
        TransactionDto transactionDtoTwo = new TransactionDto(
                "s", "banana", 50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(transactionDto.getFruit(), 100);
        StockService stockService = new StockServiceImpl(strategyOperations);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto);
        transactionDtoList.add(transactionDtoTwo);
        stockService.applyOperationsOnFruitsDto(transactionDtoList);
        Map<String, Integer> actual = Stock.stockStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void applyOperationsOnFruitsDto_OperationPurchase_Ok() {
        TransactionDto transactionDto = new TransactionDto(
                "b", "banana", 50);
        TransactionDto transactionDtoTwo = new TransactionDto(
                "p", "banana", 20);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(transactionDto.getFruit(), 30);
        StockService stockService = new StockServiceImpl(strategyOperations);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto);
        transactionDtoList.add(transactionDtoTwo);
        stockService.applyOperationsOnFruitsDto(transactionDtoList);
        Map<String, Integer> actual = Stock.stockStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void applyOperationsOnFruitsDto_OperationReturn_Ok() {
        TransactionDto transactionDto = new TransactionDto(
                "b", "banana", 50);
        TransactionDto transactionDtoTwo = new TransactionDto(
                "r", "banana", 20);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(transactionDto.getFruit(), 70);
        StockService stockService = new StockServiceImpl(strategyOperations);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto);
        transactionDtoList.add(transactionDtoTwo);
        stockService.applyOperationsOnFruitsDto(transactionDtoList);
        Map<String, Integer> actual = Stock.stockStorage;
        assertEquals(expected, actual);
    }

    @Test(expected = OperationException.class)
    public void applyOperationsOnFruitsDto_OperationNotAvailable_NotOk() {
        TransactionDto transactionDtoFirst = new TransactionDto(
                "b", "banana", 50);
        TransactionDto transactionDtoSecond = new TransactionDto(
                "p", "banana", 100);
        StockService stockService = new StockServiceImpl(strategyOperations);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDtoFirst);
        transactionDtoList.add(transactionDtoSecond);
        stockService.applyOperationsOnFruitsDto(transactionDtoList);
        fail("Must will be an Exception!");
    }
}
