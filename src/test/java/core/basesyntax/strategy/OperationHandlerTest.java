package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class OperationHandlerTest {
    @Test
    public void processFruitOperation_ContentIsSame_Ok() {
        List<FruitDto> fruitDtos = new ArrayList<>();
        fruitDtos.add(new FruitDto("banana",20,"b"));
        fruitDtos.add(new FruitDto("banana",20,"r"));
        fruitDtos.add(new FruitDto("banana",20,"p"));
        fruitDtos.add(new FruitDto("banana",20,"s"));
        OperationHandler operationHandler = new OperationHandler();
        operationHandler.processFruitOperation(fruitDtos);
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana",40));
        FruitDao dao = new FruitDaoImpl();
        List<Fruit> actual = dao.getAll();
        assertEquals(expected,actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}

