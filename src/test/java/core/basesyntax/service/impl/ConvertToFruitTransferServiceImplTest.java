package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransfer;
import core.basesyntax.service.ConvertToFruitTransferService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConvertToFruitTransferServiceImplTest {
    private static ConvertToFruitTransferService convert;

    @BeforeClass
    public static void setUp() {
        convert = new ConvertToFruitTransferServiceImpl();
    }

    @Test
    public void toFruitTransfer_ok() {
        FruitTransfer expected = new FruitTransfer(FruitTransfer.Operation.BALANCE,
                new Fruit("banana"), 100);
        FruitTransfer actual = convert.toFruitTransfer("b,banana,100");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void toFruitTransfer_incorrectFormatNotOk() {
        convert.toFruitTransfer("v,banana,100");
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
