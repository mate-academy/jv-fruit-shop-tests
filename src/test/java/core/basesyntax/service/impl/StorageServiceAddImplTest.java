package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageService;
import org.junit.After;
import org.junit.Test;

public class StorageServiceAddImplTest {
    private StorageService storageServiceAdd = new StorageServiceAddImpl(new FruitDaoImpl());

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void addFruitInStorage_Ok() {
        storageServiceAdd.operateSupply("kiwi",25);
        assertEquals("kiwi",String.valueOf(Storage.fruits.get(0).getNameFruit()));
    }

    @Test
    public void addFruitInStorage() {
        storageServiceAdd.operateSupply("kiwi",25);
        String string = Storage.fruits.get(0).getNameFruit();
        assertEquals("kiwi",string);
    }
}
