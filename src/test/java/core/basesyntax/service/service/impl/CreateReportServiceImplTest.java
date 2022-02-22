package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.CreatReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportServiceImplTest {
    private static final Fruit APPLE = new Fruit("apple", 90);
    private static final Fruit BANANA = new Fruit("banana", 152);
    private static CreatReportService creatReportService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        creatReportService = new CreateReportServiceImpl();
        FruitsStorage.getFruits().add(BANANA);
        FruitsStorage.getFruits().add(APPLE);
    }

    @Test
    public void createReport_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        assertEquals(expected, creatReportService.createReport());
    }

    @Test
    public void createReport_emptyStorage_notOk() {
        FruitsStorage.getFruits().clear();
        assertThrows(RuntimeException.class, () -> creatReportService.createReport());
    }

    @After
    public void tearDown() throws Exception {
        FruitsStorage.getFruits().clear();
    }
}
