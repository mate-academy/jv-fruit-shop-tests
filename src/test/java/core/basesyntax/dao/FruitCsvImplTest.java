package core.basesyntax.dao;

import junit.framework.TestCase;

import java.io.File;

public class FruitCsvImplTest extends TestCase {
    FruitDao fruitDao;
    private static final String WRONG_PATH_FILE = "Wrong.csv";
    @Override
    public void setUp() throws Exception {
         fruitDao = new FruitCsvImpl();
    }

    public void wrongInputFile() {
        fruitDao.getAll(new File(WRONG_PATH_FILE));
        assert
    }
}