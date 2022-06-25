package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataHandlerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    public void dataHandlerCorrectInput() {
        List<String> listForOkTest = new ArrayList<>();
        listForOkTest.add("type,fruit,quantity");
        listForOkTest.add("b,banana,20");
        List<String> actual = dataHandler.handleData(listForOkTest).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        listForExpected.add(new FruitTransaction("b", "banana", 20));
        List<String> expected = listForExpected.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        Assert.assertEquals(expected, actual);
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
        listForActual.add("b,banana,20");
        dataHandler.handleData(listForActual);
    }
}
