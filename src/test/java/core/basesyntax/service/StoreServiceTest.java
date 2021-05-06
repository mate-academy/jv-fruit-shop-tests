package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.file.FileReader;
import core.basesyntax.service.file.FileReaderImpl;
import core.basesyntax.service.impl.FruitRecordParserServiceImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.StoreServiceImpl;
import core.basesyntax.service.operation.AddOperation;
import core.basesyntax.service.operation.AddOrCreateOperation;
import core.basesyntax.service.operation.CreateOperation;
import core.basesyntax.service.operation.Operation;
import core.basesyntax.service.operation.RemoveOperation;
import core.basesyntax.service.validator.DataValidator;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StoreServiceTest {
    private static StoreService storeService;
    private static Map<String, Operation> map;
    private static FruitsDao fruitsDao;
    private static OperationStrategy operationStrategy;
    private static DataValidator dataValidator;
    private static FruitRecordParserService parser;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        dataValidator = new DataValidator();
        parser = new FruitRecordParserServiceImpl(dataValidator);

        fruitsDao = new FruitsDaoImpl();
        map = new HashMap<>();
        map.put("b", new CreateOperation(fruitsDao));
        map.put("p", new RemoveOperation(fruitsDao));
        map.put("s", new AddOperation(fruitsDao));
        map.put("r", new AddOrCreateOperation(fruitsDao));
        operationStrategy = new OperationStrategyImpl(map);
        storeService = new StoreServiceImpl(operationStrategy);

        fileReader = new FileReaderImpl();
    }

    @Test
    public void doInstruction_correctInput_Ok() {
        storeService.doInstruction(parser.getRecord(fileReader
                .readFile("src/test/resources/correct.csv")));
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 152);
        expected.put(new Fruit("apple"), 90);
        Map<Fruit, Integer> actual = Storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void doInstructions_incorrectInput_NotOk() {
        storeService.doInstruction(parser.getRecord(fileReader
                .readFile("src/test/resources/incorrect.csv")));
    }
}
