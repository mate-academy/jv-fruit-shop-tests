package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.Storage;
import model.FruitTransaction;
import service.handler.BalanceHandler;
import service.handler.OperationHandler;
import service.handler.PurchaseHandler;
import service.handler.ReturnHandler;
import service.handler.SupplyHandler;
import service.impl.DataReaderServiceImpl;
import service.impl.DataWriterServiceImpl;
import service.impl.ProcessorServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductsShopTest {

    private static DataReaderServiceImpl dataReaderService;
    private static DataWriterServiceImpl dataWriterService;
    private static ProcessorServiceImpl processorService;

    @BeforeEach
      public void setObjects() {
        dataReaderService = new DataReaderServiceImpl();
        dataWriterService = new DataWriterServiceImpl();
        processorService = new ProcessorServiceImpl();
    }

    @Test
      public void checkReadFileWithIncorectPath() {

        String path = "file.txt";
        String path2 = "src/main/java/resources/file.txt";
        String path3 = null;

        assertThrows(RuntimeException.class,
                () -> dataReaderService.readDataInFile(path),
                "Was correct file path!");
        assertThrows(RuntimeException.class,
                () -> dataReaderService.readDataInFile(path2),
                "File was in resources of file");
        assertThrows(RuntimeException.class,
                () -> dataReaderService.readDataInFile(path3),
                "File was in resources of file");
    }

    @Test
      public void checkCorrectReadingData() {

        String filepath = "src/main/java/resources/input.txt";
        List<FruitTransaction> fruitTransactionList
                = dataReaderService.readDataInFile(filepath);
        String fruitFirstPosition = "apple";
        int quantityFirstPosition = 100;
        FruitTransaction.Operation thirdPosition
                = FruitTransaction.Operation.PURCHASE;

        assertEquals(fruitTransactionList
                .get(1)
                .getFruit(), fruitFirstPosition);
        assertEquals(fruitTransactionList
                .get(1)
                .getQuantity(), quantityFirstPosition);
        assertEquals(fruitTransactionList.get(3).getOperation(),
                thirdPosition);
    }

    @Test
    public void checkEmptyFileForReading() {

        String filePath = "src/main/java/resources/empty.txt";
        List<FruitTransaction> fruitTransactionList =
                dataReaderService.readDataInFile(filePath);

        assertTrue(fruitTransactionList.isEmpty());
    }

    @Test
    public void checkPurchaseOperation() {

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler()
        );

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 123));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 23));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 100;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void checkSupplyOperation() {

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler()
        );

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 80));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 100;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void checkReturnOperation() {

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler()
        );

        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 130));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 70));
        processorService.processOnData(fruitTransactionList, handlerMap);
        int correctResult = 200;

        assertEquals(correctResult,
                Storage.getQuantity(fruitTransactionList.get(0).getFruit()));
    }

    @Test
    public void checkNullInPathToWriteFile() {
        String path = null;

        assertThrows(RuntimeException.class,
                () -> dataWriterService.writeProcessedDataToFile(path));
    }
}
