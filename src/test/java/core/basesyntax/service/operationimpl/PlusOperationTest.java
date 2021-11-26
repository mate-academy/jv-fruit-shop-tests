package core.basesyntax.service.operationimpl;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.ParsedLine;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.OperationService;
import service.operationimpl.PlusOperation;

public class PlusOperationTest {
    private static OperationService operationService;
    private ParsedLine parsedLine;
    private Fruit actual;
    private Fruit expected;

    @BeforeClass
    public static void init() {
        operationService = new PlusOperation();
    }

    @Test
    public void plus_option_ok() {
        Storage.storage.add(new Fruit("banana", 20));
        parsedLine = new ParsedLine("b", "banana", 25);
        operationService.operation(parsedLine);
        actual = new Fruit("banana", 45);
        expected = Storage.storage.get(0);
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void storage_clear() {
        Storage.storage.clear();
    }
}
