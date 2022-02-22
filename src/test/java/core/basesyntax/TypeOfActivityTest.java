package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceActivity;
import core.basesyntax.strategy.PurchaseActivity;
import core.basesyntax.strategy.SupplyActivity;
import core.basesyntax.strategy.TypeOfActivity;
import org.junit.After;
import org.junit.Test;

public class TypeOfActivityTest {
    private TypeOfActivity typeOfActivity;

    @Test
    public void realizeTypeBalance_inputDataIsNull_notOk() {
        typeOfActivity = new BalanceActivity();
        assertThrows(NullPointerException.class,
                () -> typeOfActivity.realizeType(null));
    }

    @Test
    public void realizeTypePurchase_inputDataIsNull_notOk() {
        typeOfActivity = new PurchaseActivity();
        assertThrows(NullPointerException.class,
                () -> typeOfActivity.realizeType(null));
    }

    @Test
    public void realizeTypeSupply_inputDataIsNull_notOk() {
        typeOfActivity = new SupplyActivity();
        assertThrows(NullPointerException.class,
                () -> typeOfActivity.realizeType(null));
    }

    @Test
    public void realizeTypeBalance_validDataOfVariables_notOk() {
        typeOfActivity = new BalanceActivity();
        assertThrows(RuntimeException.class,
                () -> typeOfActivity.checkValidData(new Fruit(null),-5));
    }

    @Test
    public void realizeTypeSupply_validDataOfVariables_notOk() {
        typeOfActivity = new SupplyActivity();
        assertThrows(RuntimeException.class,
                () -> typeOfActivity.checkValidData(new Fruit(null),-5));
    }

    @Test
    public void realizeTypePurchase_validDataOfVariables_notOk() {
        typeOfActivity = new PurchaseActivity();
        assertThrows(RuntimeException.class,
                () -> typeOfActivity.checkValidData(new Fruit(null),-5));
    }

    @Test
    public void realizeTypeBalance_validInputData_Ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of("b","banana",45);
        typeOfActivity = new BalanceActivity();
        typeOfActivity.realizeType(fruitTransaction);
        int actual = 45;
        int expected = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected,actual);
    }

    @Test
    public void realizeTypePurchase_validInputData_Ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of("b","banana",25);
        typeOfActivity = new PurchaseActivity();
        Storage.storage.put(new Fruit("banana"),45);
        typeOfActivity.realizeType(fruitTransaction);
        int actual = 20;
        int expected = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected,actual);
    }

    @Test
    public void realizeTypeSupply_validInputDataFirstPutInStorage_Ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of("b","banana",45);
        typeOfActivity = new SupplyActivity();
        typeOfActivity.realizeType(fruitTransaction);
        int actualFirstPutInStorage = 45;
        int expectedFirstPutInStorage = Storage.storage.get(new Fruit("banana"));
        assertEquals(expectedFirstPutInStorage,actualFirstPutInStorage);
    }

    @Test
    public void realizeTypeSupply_validInputDataStorageAlreadyContains_Ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of("b","banana",45);
        typeOfActivity = new SupplyActivity();
        Storage.storage.put(new Fruit("banana"),45);
        typeOfActivity.realizeType(fruitTransaction);
        int actual = 90;
        int expected = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected,actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
