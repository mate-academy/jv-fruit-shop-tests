package myfirstproject.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import myfirstproject.dao.FruitDao;
import myfirstproject.dao.impl.FruitDaoImpl;
import myfirstproject.model.Fruit;
import myfirstproject.model.Operation;
import myfirstproject.service.ReadFile;
import myfirstproject.service.WriteFile;
import myfirstproject.strategy.BalanceOperation;
import myfirstproject.strategy.OperationHandler;
import myfirstproject.strategy.PurchaseOperation;
import myfirstproject.strategy.ReturnOperation;
import myfirstproject.strategy.SeparationOfOperations;
import myfirstproject.strategy.SupplyOperation;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteFileImplTest {
    private static final String PATH_TO_NEW_FILE = "src/test/resources/reportFile.csv";
    private static final String PATH_TO_RESULT = "src/test/resources/reportFile.csv";
    private static final String WRONG_PATH = "src/resources/reportFile.csv";
    private static WriteFile writer;

    @BeforeClass
    public static void before() {
        final FruitDao fruitDao = new FruitDaoImpl();
        final ReadFile reader = new ReadFileImpl();
        final SeparationOfOperations separation = new SeparationOfOperations();
        final List<String[]> temporalList = new ArrayList<>();
        Map<String, OperationHandler> operation = new HashMap<>();
        operation.put(Operation.BALANCE.getOperation(), new BalanceOperation());
        operation.put(Operation.SUPPLY.getOperation(), new SupplyOperation());
        operation.put(Operation.PURCHASE.getOperation(), new PurchaseOperation());
        operation.put(Operation.RETURN.getOperation(), new ReturnOperation());
        writer = new WriteFileImpl();
        writer.writeToFile(PATH_TO_RESULT, fruitDao.getAll());
        reader.readFile(temporalList, PATH_TO_NEW_FILE);
        separation.toDoEachOperation(fruitDao, temporalList, operation);
        WriteFile writeFile = new WriteFileImpl();
        Map<Fruit, Integer> mapToWrite = new HashMap<>();
        Fruit fruit = new Fruit("banana");
        Integer value = 10;
        mapToWrite.put(fruit, value);
        writeFile.writeToFile(PATH_TO_NEW_FILE, mapToWrite);
    }

    @Test(expected = RuntimeException.class)
    public void readWrongFile_not_Ok() {
        writer.writeToFile(WRONG_PATH, Collections.EMPTY_MAP);
    }
}
