package core.basesyntax.model;

import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

public class OperationTest {
    @Test(expected = NoSuchElementException.class)
    public void getByCode_nullValue_notOk() {
        Operation.getByCode(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void getByCode_emptyValue_notOk() {
        Operation.getByCode("");
    }

    @Test(expected = NoSuchElementException.class)
    public void getByCode_invalidValue_notOk() {
        Operation.getByCode("bad");
    }

    @Test
    public void getByCode_validValue_ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = Operation.getByCode("b");
        Assert.assertEquals(expected, actual);
    }
}
