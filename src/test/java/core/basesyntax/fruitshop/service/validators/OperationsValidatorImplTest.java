package core.basesyntax.fruitshop.service.validators;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class OperationsValidatorImplTest {

    private List<String> wrongAmount;
    private List<String> emptyList;
    private List<String> lackOfInformation;
    private OperationsValidatorImpl operationsValidator = new OperationsValidatorImpl();

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
