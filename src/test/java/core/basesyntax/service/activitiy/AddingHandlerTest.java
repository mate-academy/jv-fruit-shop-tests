package core.basesyntax.service.activitiy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoCsvImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitCrate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AddingHandlerTest {
    private static StorageDao storageDao;
    private static ActivityHandler activityHandler;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoCsvImpl();
        activityHandler = new AddingHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void updateFruitCrate_validDataEmptyStorage_Ok() {
        FruitCrate expected = new FruitCrate("apple", 1);
        FruitCrate actual = activityHandler.updateFruitCrate("apple", 1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateFruitCrate_validDataFilledStorage_Ok() {
        Storage.storage.add(new FruitCrate("apple", 1));
        FruitCrate expected = new FruitCrate("apple", 3);
        FruitCrate actual = activityHandler.updateFruitCrate("apple", 2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateFruitCrate_invalidData_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("You trying to add " + "-2" + " of " + "apple");
        activityHandler.updateFruitCrate("apple", -2);
    }
}
