package core.basesyntax.services.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TypeOfOperation;
import core.basesyntax.services.operations.OperationBalance;
import core.basesyntax.services.operations.OperationHandler;
import core.basesyntax.services.operations.OperationSupply;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String TITLE = "fruit,quantity" + System.lineSeparator();
    private FruitServiceImpl fruitService;

    @Before
    public void setUp() {
        Map<TypeOfOperation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(TypeOfOperation.BALANCE, new OperationBalance());
        handlerMap.put(TypeOfOperation.SUPPLY, new OperationSupply());
        handlerMap.put(TypeOfOperation.PURCHASE, new OperationSupply());
        handlerMap.put(TypeOfOperation.RETURN, new OperationSupply());
        Storage.fruitStorage.put(new Fruit("banana"), 20);
        Storage.fruitStorage.put(new Fruit("apple"), 50);
        fruitService = new FruitServiceImpl(handlerMap);
    }

    @Test
    public void makeReport_Ok() {
        String actual = fruitService.makeReport();
        StringBuilder builder = new StringBuilder(TITLE);
        Storage.fruitStorage.forEach((key, value) ->
                builder.append(key.getName())
                        .append(",")
                        .append(value)
                        .append(System.lineSeparator()));
        String expected = builder.toString();
        assertEquals(actual, expected);
    }

    @Test
    public void applyOperationOnFruitDto_Ok() {
        Fruit banana = new Fruit("banana");
        FruitDto fruitDto = new FruitDto(TypeOfOperation.SUPPLY, banana, 20);
        List<FruitDto> fruitDtos = new ArrayList<>();
        fruitDtos.add(fruitDto);
        Storage.fruitStorage.put(banana, 10);
        fruitService.applyOperationsOnFruitsDto(fruitDtos);
        assertEquals(30, (int) Storage.fruitStorage.get(banana));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}
