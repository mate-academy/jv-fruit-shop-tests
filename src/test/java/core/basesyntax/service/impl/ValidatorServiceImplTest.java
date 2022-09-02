package core.basesyntax.service.impl;

import core.basesyntax.service.ValidatorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorServiceImplTest {
    private ValidatorService validator;

    @Before
    public void beforeClass() {
        validator = new ValidatorServiceImpl();
    }

    @Test
    public void validFile_ok() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("b,apple,10");
        Assert.assertTrue("Expected true data is valid file", validator.validateData(strings));
    }

    @Test
    public void wrongTitle_notOk() {
        List<String> strings = new ArrayList<>();
        strings.add("tttt,fruit,quantity");
        strings.add("b,apple,10");
        Assert.assertFalse("Wrong title", validator.validateData(strings));
    }

    @Test
    public void nullString_notOk() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add(null);
        Assert.assertFalse("String is null", validator.validateData(strings));
    }

    @Test
    public void emptyList_notOk() {
        List<String> strings = new ArrayList<>();
        Assert.assertFalse("Empty list", validator.validateData(strings));
    }

    @Test
    public void stringWithFourElements_notOk() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("b,apple,10,b");
        Assert.assertFalse("Four elements in line", validator.validateData(strings));
    }

    @Test
    public void stringWithTwoElements_notOk() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("b,apple");
        Assert.assertFalse("Two elements in line", validator.validateData(strings));
    }

    @Test
    public void wrongTransactionType_notOk() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("x,apple,10");
        Assert.assertFalse("Wrong type of operation", validator.validateData(strings));
    }

    @Test
    public void amountNotANumber_notOk() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("b,apple,b");
        Assert.assertFalse("Amount is not a number", validator.validateData(strings));
    }

    @Test
    public void negativeAmount_notOk() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("b,apple,-5");
        Assert.assertFalse("Negative amount", validator.validateData(strings));
    }
}
