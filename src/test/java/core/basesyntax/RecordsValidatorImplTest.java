package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Record;
import core.basesyntax.model.RecordsValidator;
import core.basesyntax.model.RecordsValidatorImpl;
import org.junit.Test;

public class RecordsValidatorImplTest {
    private static final RecordsValidator recordsValidator = new RecordsValidatorImpl();
    private static final String[] goodOperations = new String[] {"b", "s", "p", "r"};
    private static final String[] goodFruitNames = new String[] {
            "apple", "melon", "orange", "papaya45%", "peach_4_Caliber", "yellow-red tomato"};
    private static final String[] goodQuantity = new String[] {"45", "1", "17", "2599", "0"};
    private static final Record.OperationType[] operationTypes = new Record.OperationType[] {
            Record.OperationType.BALANCE, Record.OperationType.SUPPLY,
            Record.OperationType.PURCHASE, Record.OperationType.RETURN};
    private static final String NEGATIVE_QUANTITY = "-5";
    private static final String DECIMAL_QUANTITY = "-5.5";
    private static final String NON_NUMBER_QUANTITY = "five";
    private static final String EMPTY_VALUE = "";
    private static final String NON_EXISTING_OPERATION = "f";

    @Test
    public void validate_goodInput_ok() {
        for (String quantity : goodQuantity) {
            for (String name : goodFruitNames) {
                int counter = 0;
                for (String operation : goodOperations) {
                    Record provided = recordsValidator.validate(operation, name, quantity);
                    Record expected =
                            new Record(operationTypes[counter], name, Integer.parseInt(quantity));
                    counter++;
                    assertEquals(expected, provided);
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nullNameInput_notOk() {
        recordsValidator.validate(goodOperations[0], null, goodQuantity[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_emptyNameInput_notOk() {
        recordsValidator.validate(goodOperations[0], EMPTY_VALUE, goodQuantity[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nullOperationInput_notOk() {
        recordsValidator.validate(null, goodFruitNames[0], goodQuantity[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_emptyOperationInput_notOk() {
        recordsValidator.validate(EMPTY_VALUE, goodFruitNames[0], goodQuantity[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nonExistingOperationInput_notOk() {
        recordsValidator.validate(NON_EXISTING_OPERATION, goodFruitNames[0], goodQuantity[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nullQuantityInput_notOk() {
        recordsValidator.validate(goodOperations[0], goodFruitNames[0], null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_emptyQuantityInput_notOk() {
        recordsValidator.validate(goodOperations[0], goodFruitNames[0], EMPTY_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_negativeQuantityInput_notOk() {
        recordsValidator.validate(goodOperations[0], goodFruitNames[0], NEGATIVE_QUANTITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_decimalQuantityInput_notOk() {
        recordsValidator.validate(goodOperations[0], goodFruitNames[0], DECIMAL_QUANTITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_nonNumberQuantityInput_notOk() {
        recordsValidator.validate(goodOperations[0], goodFruitNames[0], NON_NUMBER_QUANTITY);
    }
}
