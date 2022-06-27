package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataHandlerImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataHandlerImplTest {
    private static DataHandler dataHandler;
    private static List<String> listForActual;
    private static List<String> emptyList;
    private static List<FruitTransaction> listForExpected;

    @Before
    public void setUp() {
        dataHandler = new DataHandlerImpl();
    }

    @BeforeClass
    public static void beforeClass() {
        listForActual = new ArrayList<>();
        listForExpected = new ArrayList<>();
    }

    @Test
    public void handleData_validInput_isOk() {
        listForActual.add("type,fruit,quantity");
        listForActual.add("b,banana,20");
        List<FruitTransaction> actual = dataHandler.handleData(listForActual);
        listForExpected.add(new FruitTransaction("b", "banana", 20));
        Assert.assertEquals(listForExpected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleData_nullDataInput_notOK() {
        dataHandler.handleData(null);
    }

    @Test(expected = RuntimeException.class)
    public void handleData_wrongDataInput_notOk() {
        dataHandler.handleData(emptyList);
    }

    @Test(expected = RuntimeException.class)
    public void handleData_wrongInput_notOk() {
        listForActual.add("b,banana,20");
        dataHandler.handleData(listForActual);
    }

    @After
    public void tearDown() {
        listForActual.clear();
    }
}
