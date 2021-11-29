package core.basesyntax.service.operationimpl;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.ParsedLine;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.OperationService;
import service.operationimpl.CreateOperation;

public class CreateOperationTest {
    private static OperationService operationService;
    private ParsedLine parsedLine;
    private Fruit actual;
    private Fruit expected;

    @BeforeClass
    public static void init() {
        operationService = new CreateOperation();
    }

    @Test
    public void create_option_ok() {
        parsedLine = new ParsedLine("b", "banana", 25);
        operationService.operation(parsedLine);
        actual = new Fruit("banana", 25);
        expected = Storage.storage.get(0);
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void storage_clear() {
        Storage.storage.clear();
    }
}
