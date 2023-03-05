package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.CreateReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CreateReportServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 90;
    private static final int BANANA_QUANTITY = 152;
    private static CreateReportService createReportService;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        createReportService = new CreateReportServiceImpl();
        Storage.getFruits().add(new Fruit(BANANA, BANANA_QUANTITY));
        Storage.getFruits().add(new Fruit(APPLE, APPLE_QUANTITY));
    }

    @Test
    public void createReport_ok() {
        String expected = String.join("", "fruit,quantity\n",
                "banana,152\n",
                "apple,90");
        assertEquals(expected, createReportService.createReport());
    }

    @Test
    public void createReport_emptyStorage_notOk() {
        Storage.getFruits().clear();
        thrown.expect(RuntimeException.class);
        createReportService.createReport();
    }

    @After
    public void tearDown() {
        Storage.getFruits().clear();
    }
}
