package core.basesyntax.service.operationimpl;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.ParseLine;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import service.OperationService;
import service.operationimpl.CreateOperation;

public class CreateOperationTest {
    private ParseLine parseLine;
    private OperationService operationService;
    private Fruit actual;
    private Fruit expected;

    @AfterClass
    public static void storage_clear() {
        Storage.storage.clear();
    }

    @Test
    public void create_option_ok() {
        parseLine = new ParseLine("b", "banana", 25);
        operationService = new CreateOperation();
        operationService.operation(parseLine);
        actual = new Fruit("banana", 25);
        expected = Storage.storage.get(0);
        Assert.assertEquals(expected, actual);
    }
}
