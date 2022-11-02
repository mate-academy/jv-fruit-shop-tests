package com.fruitshop.servicesimpl;

import com.fruitshop.dao.FruitDaoImpl;
import com.fruitshop.db.DataBase;
import com.fruitshop.model.Fruit;
import com.fruitshop.services.ResultMessage;
import com.fruitshop.strategy.ElementDoesNotExist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResultMessageImplTest {

    private ResultMessage resultMessage;
    private Fruit lemon;

    @BeforeEach
    public void setUp() {
        lemon = new Fruit();
        lemon.setBalance(100);
        lemon.setSupply(50);
        lemon.setPurchase(70);
        resultMessage = new ResultMessageImpl();
    }

    @AfterEach
    public void tearDown() {
        DataBase.fruitsInShop.clear();
    }

    @Test
    public void makeMessage_keyIsNull_notOk() {
        DataBase.fruitsInShop.put("hello", null);
        Assertions.assertThrows(ElementDoesNotExist.class,
                () -> resultMessage.makeMessage(new FruitDaoImpl()));
    }

    @Test
    public void makeMessage_EmptyDataBase() {
        String actual = resultMessage.makeMessage(new FruitDaoImpl());
        String expected = "fruit,quantity";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void makeMessage_Ok() {
        DataBase.fruitsInShop.put("lemon", lemon);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "lemon,80";
        String actual = resultMessage.makeMessage(new FruitDaoImpl());
        Assertions.assertEquals(expected, actual);
    }
}
