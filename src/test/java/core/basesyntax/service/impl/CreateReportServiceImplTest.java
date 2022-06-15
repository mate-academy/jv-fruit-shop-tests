package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImp;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateReportServiceImplTest {
    private FruitDao fruitDao;
    private FruitService fruitService;
    private CreateReportServiceImpl createReportService;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImp();
        fruitService = new FruitServiceImpl(fruitDao);
        createReportService = new CreateReportServiceImpl(fruitService);
    }

    @Test
    public void createReport_validData_Ok() {
        FruitTransaction banana = new FruitTransaction();
        banana.setFruit("banana");
        banana.setOperation(FruitTransaction.Operation.BALANCE);
        banana.setQuantity(152);
        FruitTransaction apple = new FruitTransaction();
        apple.setFruit("apple");
        apple.setOperation(FruitTransaction.Operation.BALANCE);
        apple.setQuantity(90);
        fruitDao.add(banana);
        fruitDao.add(apple);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String actual = createReportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = createReportService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.warehouse.clear();
    }
}
