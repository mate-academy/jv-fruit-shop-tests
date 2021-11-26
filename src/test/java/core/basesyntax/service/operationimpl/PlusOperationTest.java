package core.basesyntax.service.operationimpl;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.ParseLine;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import service.OperationService;
import service.operationimpl.PlusOperation;

public class PlusOperationTest {
    private ParseLine parseLine;
    private OperationService operationService;
    private Fruit actual;
    private Fruit expected;

    @AfterClass
    public static void storage_clear() {
        Storage.storage.clear();
    }

    @Test
    public void plus_option_ok() {
        Storage.storage.add(new Fruit("banana", 20));
        parseLine = new ParseLine("b", "banana", 25);
        operationService = new PlusOperation();
        operationService.operation(parseLine);
        actual = new Fruit("banana", 45);
        expected = Storage.storage.get(0);
        Assert.assertEquals(expected, actual);
    }
}
