package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
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
    public void increaseFruitQuantity() {
        Storage.fruits.clear();
        Fruit fruit = new Fruit();
        fruit.setNameFruit("pineapple");
        fruit.setQuantityFruit(1900);
        Storage.fruits.add(fruit);
        storageServiceReturn.operateSupply("pineapple",1786);
        storageServiceSupply.operateSupply("pineapple",1000);
        Integer quantity = Storage.fruits.get(0).getQuantityFruit();
        assertEquals((int) quantity,4686);
    }

    @Test
    public void createReportChecking() {
        Storage.fruits.clear();
        String checkingString = reportCreator.reportCreator();
        assertEquals("fruit,quantity\n",checkingString);

        Storage.fruits.clear();
        Fruit fruit = new Fruit();
        fruit.setNameFruit("pineapple");
        fruit.setQuantityFruit(1900);
        Storage.fruits.add(fruit);
        String checkingString2 = reportCreator.reportCreator();
        assertEquals("fruit,quantity\npineapple,1900\n",checkingString2);
    }
}
