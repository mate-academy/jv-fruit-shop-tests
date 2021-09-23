package core.basesyntax.fruitshop.service.validators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OperationsValidatorImplTest {

    List<String> wrongAmount;
    List<String> emptyList;
    List<String> lackOfInformation;
    OperationsValidatorImpl operationsValidator = new OperationsValidatorImpl();
    

    @Before
    public void setUp() throws Exception {

        wrongAmount = new ArrayList<>();
        wrongAmount.add("b,banana, -13");
        wrongAmount.add("s,apple,10");
        emptyList = new ArrayList<>();
        lackOfInformation = new ArrayList<>();
        lackOfInformation.add("b, apple,");
        lackOfInformation.add("p,10");
    }

    @Test(expected = InvalidDataException.class)
    public void validator_negativeAmount_NotOk() {
        operationsValidator.validator(wrongAmount);
    }

    @Test(expected = InvalidDataException.class)
    public void validator_emptyList_NotOk() {
        operationsValidator.validator(emptyList);
    }

    @Test(expected = InvalidDataException.class)
    public void validator_lackOfInformation_NotOk() {
        operationsValidator.validator(lackOfInformation);
    }
}