package core.basesyntax.servicesimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.ResultMessage;
import core.basesyntax.strategy.ElementDoesNotExist;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResultMessageImplTest {

    private ResultMessage resultMessage;
    private Fruit lemon;

    @Before
    public void setUp() {
        lemon = new Fruit();
        lemon.setBalance(100);
        lemon.setSupply(50);
        lemon.setPurchase(70);
        resultMessage = new ResultMessageImpl();
    }

    @After
    public void tearDown() {
        DataBase.fruitsInShop.clear();
    }

    @Test(expected = ElementDoesNotExist.class)
    public void makeMessage_keyIsNull_notOk() {
        DataBase.fruitsInShop.put("hello", null);
        resultMessage.makeMessage(new FruitDaoImpl());
    }

    @Test
    public void makeMessage_EmptyDataBase() {
        String actual = resultMessage.makeMessage(new FruitDaoImpl());
        String expected = "fruit,quantity";
        assertEquals(expected, actual);
    }

    @Test
    public void makeMessage_Ok() {
        DataBase.fruitsInShop.put("lemon", lemon);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "lemon,80";
        String actual = resultMessage.makeMessage(new FruitDaoImpl());
        assertEquals(expected, actual);
    }
}
