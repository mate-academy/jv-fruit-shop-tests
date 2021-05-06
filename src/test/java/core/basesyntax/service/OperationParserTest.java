package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Fruit;
import core.basesyntax.dto.Operation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.basesyntax.exceptions.IllegalDataException;
import org.junit.Before;
import org.junit.Test;

public class OperationParserTest {
    private static final Fruit FRUIT_BANANA = new Fruit("banana");
    private static final Fruit FRUIT_APPLE = new Fruit("apple");
    private static final int TEN_QUANTITY = 10;
    private static final int TWENTY_QUANTITY = 20;
    private static final Operation BANANA_BALANCE =
            new Operation(Operation.OperationType.BALANCE, FRUIT_BANANA, TEN_QUANTITY);
    private static final Operation APPLE_BALANCE =
            new Operation(Operation.OperationType.BALANCE, FRUIT_APPLE, TWENTY_QUANTITY);
    private static final DataReader dataReader = new DataReader();
    private static final OperationParser operationParser = new OperationParser();

    @Test
    public void parseOperation_Ok() throws IOException {
        List<Operation> listOfOperations = new ArrayList<>();
        listOfOperations.add(BANANA_BALANCE);
        listOfOperations.add(APPLE_BALANCE);
        List<String> stringList = dataReader.readData("src/test/test_data.csv");
        List<Operation> actual = operationParser.parseOperationsToList(stringList);
        assertEquals(listOfOperations, actual);
    }

    @Test(expected = IllegalDataException.class)
    public void handleIllegalData_Ok() throws IOException {
        List<String> stringList = dataReader.readData("src/test/illegal_data_test.csv");
        operationParser.parseOperationsToList(stringList);
    }
}
