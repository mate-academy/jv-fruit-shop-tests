package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.Test;

public class FruitServiceImplTest {
    private final FruitServiceImpl fruitService;

    public FruitServiceImplTest() {
        fruitService = new FruitServiceImpl(new FruitDaoImpl(new Storage()),
                "src/main/resources/report.csv");
    }

    @Test()
    public void fileExists_Ok() {
        assertTrue("if report was writing in file, is it true", fruitService.createReport());
    }

    @Test(expected = RuntimeException.class)
    public void fileExists_NotOk() {
        FruitServiceImpl fruitService = new FruitServiceImpl(new FruitDaoImpl(new Storage()),
                "src/main/resources");
        fruitService.createReport();
    }
}
