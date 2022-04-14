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
    public static final String CORRECT_INPUT_FILE = "src/test/resources/correct.csv";
    public static final String INCORRECT_INPUT_FILE = "src/test/resources/incorrect.csv";
    private static StoreService storeService;
    private static FruitRecordParserService parser;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DataValidator dataValidator = new DataValidator();
        parser = new FruitRecordParserServiceImpl(dataValidator);
        fileReader = new FileReaderImpl();
        initializeStoreService();
    }

    private static void initializeStoreService() {
        FruitsDao fruitsDao = new FruitsDaoImpl();
        Map<String, Operation> map = new HashMap<>();
        map.put("b", new CreateOperation(fruitsDao));
        map.put("p", new RemoveOperation(fruitsDao));
        map.put("s", new AddOperation(fruitsDao));
        map.put("r", new AddOrCreateOperation(fruitsDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(map);
        storeService = new StoreServiceImpl(operationStrategy);
    }

    @Test
    public void doInstruction_correctInput_Ok() {
        storeService.doInstruction(parser.getRecord(fileReader
                .readFile(CORRECT_INPUT_FILE)));
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 152);
        expected.put(new Fruit("apple"), 90);
        Map<Fruit, Integer> actual = Storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidInputException.class)
    public void doInstructions_incorrectInput_NotOk() {
        storeService.doInstruction(parser.getRecord(fileReader
                .readFile(INCORRECT_INPUT_FILE)));
    }
}
