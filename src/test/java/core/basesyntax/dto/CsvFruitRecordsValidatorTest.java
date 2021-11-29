package core.basesyntax.dto;

import org.junit.Test;

public class CsvFruitRecordsValidatorTest {
    private static final String emptyString = "";
    private static final String validData = new StringBuilder()
            .append("type,fruit,quantity")
            .append(System.lineSeparator())
            .append("b,banana,20")
            .append(System.lineSeparator())
            .append("b,apple,100")
            .append(System.lineSeparator())
            .append("s,banana,100")
            .append(System.lineSeparator())
            .toString();
    private static final Operator operator = new Operator();
    private static final FruitRecordsValidator validator
            = new CsvFruitRecordsValidator();

    @Test(expected = RuntimeException.class)
    public void validation_emptyString_Exception() {
        validator.validation(emptyString, operator);
    }

    @Test(expected = RuntimeException.class)
    public void validation_nullOperator_Exception() {
        validator.validation(validData, null);
    }

    @Test(expected = RuntimeException.class)
    public void validation_incorrectRecordSeparator_Exception() {
        String data = new StringBuilder()
                .append("type,fruit,quantity")
                .append(System.lineSeparator())
                .append("b,banana,20")
                .append("\r")
                .toString();
        validator.validation(data, operator);
    }

    @Test(expected = RuntimeException.class)
    public void validation_incorrectDataSeparator_Exception() {
        String data = new StringBuilder()
                .append("type;fruit;quantity")
                .append(System.lineSeparator())
                .append("b;banana;20")
                .append(System.lineSeparator())
                .toString();
        validator.validation(data, operator);
    }

    @Test(expected = RuntimeException.class)
    public void validation_incorrectOperation_Exception() {
        String data = new StringBuilder()
                .append("type,fruit,quantity")
                .append(System.lineSeparator())
                .append("z,banana,20")
                .append(System.lineSeparator())
                .toString();
        validator.validation(data, operator);
    }
}
