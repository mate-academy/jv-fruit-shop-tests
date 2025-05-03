package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Fruit;
import core.basesyntax.dto.Operation;
import core.basesyntax.exceptions.InvalidOperationException;
import core.basesyntax.service.DataReader;
import core.basesyntax.service.OperationParser;
import core.basesyntax.service.Parser;
import core.basesyntax.service.StorageService;
import java.util.List;
import org.junit.Test;

public class OperationServiceTest {
    private static final DataReader dataReader = new DataReader();
    private static final Parser dataParser = new OperationParser();
    private static final Fruit banana = new Fruit("banana");
    private static final Fruit apple = new Fruit("apple");

    @Test
    public void applyOperations_Ok() {
        List<String> operationsList = dataReader.readData("src/test/operations_test.csv");
        List<Operation> allOperations = dataParser.parseOperationsToList(operationsList);

        StorageService expected = new StorageService();
        expected.create(banana, new Operation(Operation.OperationType.BALANCE, banana, 40));
        expected.create(apple, new Operation(Operation.OperationType.BALANCE, apple, 16));

        StorageService actualStorage = new StorageService();
        OperationService operationService = new OperationService(actualStorage);
        operationService.applyOperations(allOperations);

        assertEquals(expected.read(banana), actualStorage.read(banana));
        assertEquals(expected.read(apple), actualStorage.read(apple));
    }

    @Test(expected = InvalidOperationException.class)
    public void handleUnknownOperation_Ok() {
        List<String> operationsList = dataReader.readData("src/test/unknown_operation.csv");
        dataParser.parseOperationsToList(operationsList);
    }
}
