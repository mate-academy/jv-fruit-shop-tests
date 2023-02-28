package core.basesyntax.dao;

import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.FruitShopExeption;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final FruitDao FRUIT_DAO = new FruitDaoImpl();
    private static final String FRUIT_OK = "apple";
    private static final String FRUIT_2_OK = "banana";
    private static final Integer AMOUNT_OK = 10;
    private static final Integer AMOUNT2_OK = 50;
    private static final Integer AMOUNT_0_NOTOK = 0;
    private static final Integer AMOUNT_LES_THEN_0_NOTOK = -5;

    @Test(expected = FruitShopExeption.class)
    public void add_Nul_Fruit_NotOk() {
        FRUIT_DAO.add(null, AMOUNT_OK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void add_Nul_Amount_NotOk() {
        FRUIT_DAO.add(FRUIT_OK, null);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void add_Les_Then_0_Amount_NotOk() {
        FRUIT_DAO.add(FRUIT_OK, AMOUNT_LES_THEN_0_NOTOK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void add_0_Amount_NotOk() {
        FRUIT_DAO.add(FRUIT_OK, AMOUNT_0_NOTOK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void remove_Fruit_Null_NotOk() {
        FRUIT_DAO.remove(null, AMOUNT_OK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void remove_Amount_Null_NotOk() {
        FRUIT_DAO.remove(FRUIT_OK, null);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test
    public void add_Amount_Ok() {
        FRUIT_DAO.add(FRUIT_2_OK, AMOUNT2_OK);
        FRUIT_DAO.add(FRUIT_OK, AMOUNT_OK);
        FRUIT_DAO.add(FRUIT_2_OK, AMOUNT2_OK);
        Integer expected = AMOUNT_OK;
        Integer actual = Storage.fruits.get(FRUIT_OK);
        if (!expected.equals(actual)) {
            fail("Expected: " + expected + ", but was: " + actual);
        }
    }
}
