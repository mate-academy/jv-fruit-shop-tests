package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.handler.BalanceHandler;
import service.handler.OperationHandler;
import service.handler.PurchaseHandler;
import service.handler.ReturnHandler;
import service.handler.SupplyHandler;

class ProcessorServiceImplTest {
    private static DataReaderServiceImpl dataReaderService;
    private static ProcessorServiceImpl processorService;
    private static final String EMPTY_FILE = "src/main/java/resources/empty.txt";
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeAll
    public static void setHandlerMap() {
        handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE,
                new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(),
                FruitTransaction.Operation.RETURN,
                new ReturnHandler()
        );
    }

    @BeforeEach
    public void setObjects() {
        dataReaderService = new DataReaderServiceImpl();
        processorService = new ProcessorServiceImpl();
    }

    @AfterEach
    public void clearStorage() {
        Storage.getFruitStorage().clear();
    }

    @Test
    public void checkProcessorServiceCorrectWork() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana", 13));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));

        Map<String, Integer> correct = Map.of(
                "banana",152,
                "apple",90
        );

        processorService.processOnData(fruitTransactionList, handlerMap);

        assertEquals(correct, Storage.getFruitStorage());
    }

    @Test
    public void checkEmptyProcessorServiceReport() {
        Map<String, Integer> correct = new HashMap<>();
        processorService.processOnData(dataReaderService
                .readDataInFile(EMPTY_FILE), handlerMap);

        assertEquals(correct, Storage.getFruitStorage());
    }
    @Test
    public void checkPurchaseOperation() {

        List<FruitTransaction> fruitTransactionList =
                new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                "banana", 123));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE,
                "banana", 23));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 100;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void checkSupplyOperation() {

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                "banana", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.SUPPLY,
                "banana", 80));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 100;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void checkReturnOperation() {

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                "banana", 130));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.RETURN,
                "banana", 70));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 200;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }
}