package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
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
    private static final String PATH_FILE_TO_READ
            = "src/test/java/resources/testinput.txt";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String EMPTY_FILE = "src/test/java/resources/empty.txt";
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
        Storage.getFruitStorage().clear();
        dataReaderService = new DataReaderServiceImpl();
        processorService = new ProcessorServiceImpl();
    }

    @Test
    public void workProcessorService_Ok() {
        List<FruitTransaction> fruitTransactionList =
                dataReaderService.readDataInFile(PATH_FILE_TO_READ);

        Map<String, Integer> correct = Map.of(
                BANANA,152,
                APPLE,90
        );

        processorService.processOnData(fruitTransactionList, handlerMap);

        assertEquals(correct, Storage.getFruitStorage());
    }

    @Test
    public void emptyDataToProcessorService_Ok() {
        Map<String, Integer> correct = new HashMap<>();
        processorService.processOnData(dataReaderService
                .readDataInFile(EMPTY_FILE), handlerMap);

        assertEquals(correct, Storage.getFruitStorage());
    }

    @Test
    public void purchaseOperation_Ok() {

        List<FruitTransaction> fruitTransactionList =
                new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                BANANA, 123));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE,
                BANANA, 23));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 100;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void supplyOperation_Ok() {

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                BANANA, 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.SUPPLY,
                BANANA, 80));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 100;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void returnOperation_Ok() {

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                BANANA, 130));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction
                .Operation.RETURN,
                BANANA, 70));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 200;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }
}
