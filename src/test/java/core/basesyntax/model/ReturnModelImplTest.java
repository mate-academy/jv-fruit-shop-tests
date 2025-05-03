package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnModelImplTest {
    private static ReturnModelImpl returnModel;

    @BeforeClass
    public static void beforeClass() throws Exception {
        returnModel = new ReturnModelImpl();
    }

    @Test(expected = RuntimeException.class)
    public void getModel_incorrectOperation_NotOk() {
        returnModel.getModel(new String[] {"", "apple", "10"});
    }

    @Test(expected = RuntimeException.class)
    public void getModel_incorrectFruit_NotOk() {
        returnModel.getModel(new String[] {"b", "", "10"});
    }

    @Test(expected = RuntimeException.class)
    public void getModel_amountIsEmpty_NotOk() {
        returnModel.getModel(new String[] {"b", "banana", ""});
    }

    @Test(expected = RuntimeException.class)
    public void getModel_incorrectAmount_NotOk() {
        returnModel.getModel(new String[] {"b", "banana", "a"});
    }

    @Test
    public void getModel_gotCorrectModel_Ok() {
        FruitModel expectedModel = new FruitModel("banana", 20);
        FruitModel returnedModel = returnModel.getModel(new String[] {"b", "banana", "20"});
        assertEquals(expectedModel.getName(), returnedModel.getName());
        assertEquals(expectedModel.getAmount(), returnedModel.getAmount());
    }

    @Test
    public void elementIsEmptyOrNull_incorrectElement_Ok() {
        assertTrue(returnModel.elementIsEmptyOrNull(null));
        assertTrue(returnModel.elementIsEmptyOrNull(""));
    }
}

