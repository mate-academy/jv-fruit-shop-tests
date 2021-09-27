package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.inter.FruitOperationListCreateService;
import core.basesyntax.service.inter.Mapper;
import core.basesyntax.service.inter.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitOperationListCreateServiceImplTest {
    private static FruitOperationListCreateService fruitOperationListCreateService;
    private static List<String> correctString;
    private static List<String> incorrectString;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitOperationListCreateService = new FruitOperationListCreateServiceImpl();
        correctString = new ArrayList<>();
        incorrectString = new ArrayList<>();
    }

    @Test
    public void getFruitOperationListWorksProperly() {
        correctString.add("b,banana,27");
        List<FruitOperation> actual =
                fruitOperationListCreateService.getFruitOperationsList(correctString);
        List<FruitOperation> fruitOperations = new ArrayList<>();
        Mapper<String, FruitOperation> mapper = new MapperImpl();
        Validator<String> validator = new FruitOperationValidator();
        for (String data: correctString) {
            validator.validate(data);
            fruitOperations.add(mapper.map(data));
        }
        assertEquals(fruitOperations, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getFruitOperationListInvalidValue_NotOk() {
        incorrectString.add("apple,27");
        List<FruitOperation> actual =
                fruitOperationListCreateService.getFruitOperationsList(incorrectString);
        List<FruitOperation> fruitOperations = new ArrayList<>();
        Mapper<String, FruitOperation> mapper = new MapperImpl();
        Validator<String> validator = new FruitOperationValidator();
        for (String data: incorrectString) {
            validator.validate(data);
            fruitOperations.add(mapper.map(data));
        }
        assertEquals(fruitOperations, actual);
    }

    @Test (expected = RuntimeException.class)
    public void nullCheck_NotOk() {
        fruitOperationListCreateService.getFruitOperationsList(null);
    }
}
