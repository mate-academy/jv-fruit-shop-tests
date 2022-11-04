package myfirstproject.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import myfirstproject.dao.FruitDao;
import myfirstproject.dao.impl.FruitDaoImpl;
import myfirstproject.model.Operation;
import myfirstproject.service.ReadFile;
import myfirstproject.service.WriteFile;
import myfirstproject.strategy.BalanceOperation;
import myfirstproject.strategy.OperationHandler;
import myfirstproject.strategy.PurchaseOperation;
import myfirstproject.strategy.ReturnOperation;
import myfirstproject.strategy.SeparationOfOperations;
import myfirstproject.strategy.SupplyOperation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileImplTest {

    private static final String PATH_TO_NEW_FILE = "src/test/resources/sourceFile.csv";
    private static final String PATH_TO_RESULT = "src/test/resources/reportFile.csv";
    private static final String FILE_NOT_EXIST = "wrong/file.csv";
    private static ReadFile fileReaderTest;
    private static final List<String[]> temporalList = new ArrayList<>();

    @BeforeClass
    public static void before() {
        final FruitDao fruitDao = new FruitDaoImpl();
        final ReadFile reader = new ReadFileImpl();
        final WriteFile writer = new WriteFileImpl();
        final SeparationOfOperations separation = new SeparationOfOperations();
        final List<String[]> temporalList = new ArrayList<>();

        Map<String, OperationHandler> operation = new HashMap<>();
        operation.put(Operation.BALANCE.getOperation(), new BalanceOperation());
        operation.put(Operation.SUPPLY.getOperation(), new SupplyOperation());
        operation.put(Operation.PURCHASE.getOperation(), new PurchaseOperation());
        operation.put(Operation.RETURN.getOperation(), new ReturnOperation());
        reader.readFile(temporalList, PATH_TO_NEW_FILE);
        separation.toDoEachOperation(temporalList, operation);
        writer.writeToFile(PATH_TO_RESULT, fruitDao.getAll());

        fileReaderTest = new ReadFileImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readWrongFile_not_Ok() {
        fileReaderTest.readFile(temporalList, FILE_NOT_EXIST);
    }

    @Test
    public void readFile_Ok() {
        List<String[]> expectedList = new ArrayList<>();
        StringBuilder expectedBuilder = new StringBuilder();
        StringBuilder actualBuilder = new StringBuilder();

        expectedList.add("type,fruit,quantity".split(","));
        expectedList.add("b,banana,20".split(","));
        expectedList.add("b,apple,100".split(","));
        expectedList.add("s,banana,100".split(","));
        expectedList.add("p,banana,13".split(","));
        expectedList.add("r,apple,10".split(","));
        expectedList.add("p,apple,20".split(","));
        expectedList.add("p,banana,5".split(","));
        expectedList.add("s,banana,50".split(","));

        fileReaderTest.readFile(temporalList,PATH_TO_NEW_FILE);
        for (String[] s : expectedList) {
            expectedBuilder.append(Arrays.toString(s));
            expectedBuilder.append(System.lineSeparator());
        }
        for (String[] s : temporalList) {
            actualBuilder.append(Arrays.toString(s));
            actualBuilder.append(System.lineSeparator());
        }

        String expected = expectedBuilder.toString();
        String actual = actualBuilder.toString();
        Assert.assertEquals(actual, expected);
    }

}
