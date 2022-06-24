package core.basesyntax.service;

import core.basesyntax.service.impl.DataHandlerImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataHandlerImplTest {
    private static DataHandler dataHandler;
    private static List<String> listForActual;
    private static List<String> emptyList;
    private static List<String> listForExpected;

    @Before
    public void setUp() {
        dataHandler = new DataHandlerImpl();
    }

    @BeforeClass
    public static void beforeClass() {
        listForActual = new ArrayList<>();
        listForExpected = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void dataHandlerNullDataInput_notOK() {
        dataHandler.handleData(null);
    }

    @Test(expected = RuntimeException.class)
    public void dataHandlerWrongDataInput_notOk() {
        dataHandler.handleData(emptyList);
    }

    @Test(expected = RuntimeException.class)
    public void dataHandlerWrongInput_notOk() {
        listForActual.add("s,banana,20");
        dataHandler.handleData(listForActual);
    }

    @Test
    public void dataHandlerCorrectInput() {
        listForActual.add("type,fruit,quantity");
        listForActual.add("b,banana,20");
    }
}
