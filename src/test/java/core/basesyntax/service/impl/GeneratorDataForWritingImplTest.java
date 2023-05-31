package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.GeneratorDataForWriting;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneratorDataForWritingImplTest {
    private static final String EXPECTED_DATA_FOR_WRITING_FORM_EMPTY_DB = "fruit,quantity";
    private static final String EXPECTED_STRING_FOR_WRITING
            = "fruit,quantity\nbanana,152\napple,90";
    private static final String NAME_BANANA = "banana";
    private static final String NAME_APPLE = "apple";
    private static final int QUANTITY_OF_BANANA = 152;
    private static final int QUANTITY_OF_APPLE = 90;
    private static FruitsDao fruitsDao;
    private static GeneratorDataForWriting generatorDataForWriting;

    @BeforeClass
    public static void setUp() {
        fruitsDao = new FruitDaoImpl();
        generatorDataForWriting = new GeneratorDataForWritingImpl(fruitsDao);
    }

    @Test
    public void generateData_fromEmptyDb_ok() {
        String actual = generatorDataForWriting.generateData();
        assertEquals(EXPECTED_DATA_FOR_WRITING_FORM_EMPTY_DB, actual);
    }

    @Test
    public void generateData_fromNotEmptyDb_ok() {
        Storage.fruitsStorage.put(NAME_BANANA, QUANTITY_OF_BANANA);
        Storage.fruitsStorage.put(NAME_APPLE, QUANTITY_OF_APPLE);
        String actual = generatorDataForWriting.generateData();
        assertEquals(EXPECTED_STRING_FOR_WRITING, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruitsStorage.clear();
    }
}
