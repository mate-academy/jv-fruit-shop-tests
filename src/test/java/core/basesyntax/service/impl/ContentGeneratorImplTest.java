package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.db.StorageDaoImpl;
import core.basesyntax.models.Fruit;
import core.basesyntax.service.ContentGenerator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentGeneratorImplTest {
    private static StorageDao storageDao;
    private static ContentGenerator contentGenerator;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        contentGenerator = new ContentGeneratorImpl(storageDao);
    }

    @Test
    public void generateContent_properHeader_Ok() {
        String expectedHeader = "fruit,quantity";
        String[] actualArray = contentGenerator.generateContent().split(System.lineSeparator());
        assertEquals(expectedHeader, actualArray[0]);
    }

    @Test
    public void generateContent_oneItem_Ok() {
        Fruit testFruit = new Fruit("Coconut");
        Storage.storage.put(testFruit, 10);
        String expected = "Coconut,10";
        String[] actualArray = contentGenerator.generateContent().split(System.lineSeparator());
        assertEquals(expected, actualArray[1]);
    }

    @Test
    public void generateContent_multipleItemsSize_Ok() {
        Storage.storage.put(new Fruit("Apple"), 10);
        Storage.storage.put(new Fruit("Apricot"), 11);
        Storage.storage.put(new Fruit("Avocado"), 12);
        Storage.storage.put(new Fruit("Arbuz"), 13);

        String actualContent = contentGenerator.generateContent();
        assertTrue(actualContent.contains("fruit,quantity"));
        assertTrue(actualContent.contains("Apple,10"));
        assertTrue(actualContent.contains("Apricot,11"));
        assertTrue(actualContent.contains("Avocado,12"));
        assertTrue(actualContent.contains("Arbuz,13"));

        String[] expectedLength = new String[5];
        String[] actualLength = actualContent.split(System.lineSeparator());
        assertEquals(expectedLength.length, actualLength.length);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
