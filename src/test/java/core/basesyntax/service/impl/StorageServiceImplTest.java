package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.StorageService;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class StorageServiceImplTest {
    private StorageService storageServiceAdd = new StorageServiceAddImpl(new FruitDaoImpl());
    private StorageService storageServicePurchase
            = new StorageServicePurchaseImpl(new FruitDaoImpl());
    private StorageService storageServiceReturn = new StorageServiceReturnImpl(new FruitDaoImpl());
    private StorageService storageServiceSupply = new StorageServiceSupplyImpl(new FruitDaoImpl());
    private ReportCreator reportCreator = new CreateReportImpl();

    @Test
    public void addFruitInStorage_Ok() {
        Storage.fruits.clear();
        storageServiceAdd.operateSupply("kiwi",25);
        assertEquals("kiwi",String.valueOf(Storage.fruits.get(0).getNameFruit()));
    }

    @Test
    public void addFruitInStorage() {
        Storage.fruits.clear();
        storageServiceAdd.operateSupply("kiwi",25);
        String string = Storage.fruits.get(0).getNameFruit();
        assertEquals("kiwi",string);
    }

    @Test
    public void decreaseFruitQuantity() {
        Storage.fruits.clear();
        Fruit fruit = new Fruit();
        fruit.setNameFruit("pineapple");
        fruit.setQuantityFruit(1900);
        Storage.fruits.add(fruit);
        storageServicePurchase.operateSupply("pineapple",1786);
        Integer quantity = Storage.fruits.get(0).getQuantityFruit();
        assertEquals((int) quantity,114);
    }

    @Test
    public void supplyFruitQuantity() {
        Storage.fruits.clear();
        Fruit fruit = new Fruit();
        fruit.setNameFruit("pineapple");
        fruit.setQuantityFruit(1900);
        Storage.fruits.add(fruit);
        storageServiceSupply.operateSupply("pineapple",1000);
        Integer quantity = Storage.fruits.get(0).getQuantityFruit();
        assertEquals((int) quantity,2900);
    }

    @Test
    public void returnFruitQuantity() {
        Storage.fruits.clear();
        Fruit fruit = new Fruit();
        fruit.setNameFruit("pineapple");
        fruit.setQuantityFruit(1900);
        Storage.fruits.add(fruit);
        storageServiceReturn.operateSupply("pineapple",1786);
        Integer quantity = Storage.fruits.get(0).getQuantityFruit();
        assertEquals((int) quantity,3686);
    }

    @Test
    public void createFirstLineReportChecking() {
        Storage.fruits.clear();
        String checkingString = reportCreator.reportCreator();
        assertEquals("fruit,quantity\n",checkingString);
    }

    @Test
    public void createReportChecking() {
        Storage.fruits.clear();
        Fruit fruit = new Fruit();
        fruit.setNameFruit("pineapple");
        fruit.setQuantityFruit(1900);
        Storage.fruits.add(fruit);
        String checkingString2 = reportCreator.reportCreator();
        assertEquals("fruit,quantity\npineapple,1900\n",checkingString2);
    }

    @Test
    public void fileReadAbsolutePath() {
        CsvFileReaderService csvFileReaderService = new CsvFileReaderService();
        List<String> operations = new ArrayList<>();
        csvFileReaderService.readData("src/main/resources/FruitOperateDay.csv");
        assertEquals(new ArrayList<>(),operations);
    }

    @Test
    public void fileWriteAbsolutePath() {
        String absolutePath = new File("src/main/resources/FruitResult.csv").getAbsolutePath();
        CsvFileWriterService csvFileWriterService
                = new CsvFileWriterService("src/main/resources/FruitResult.csv",new String());
        assertTrue(new File(absolutePath).delete());
    }

    @Test
    public void checkOperations() {
        Storage.fruits.clear();
        Map<String, StorageService> operationStorageMap = new HashMap<>();
        operationStorageMap.put("b",storageServiceAdd);
        OperationsPerDay operations = new OperationsPerDay();
        List list = new ArrayList();
        list.add("b,checkingFruitEmpty,100");
        list.add("b,checkingFruitRealZero,100");
        operations.createOperations(list,operationStorageMap);
        assertEquals("checkingFruitRealZero",Storage.fruits.get(0).getNameFruit());

    }
}
