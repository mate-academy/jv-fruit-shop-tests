package core.basesyntax.dto;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.dto.handlers.BalanceOperationHandler;
import core.basesyntax.dto.handlers.OperationsHandler;
import core.basesyntax.dto.handlers.PurchaseOperationHandler;
import core.basesyntax.dto.handlers.ReturnOperationHandler;
import core.basesyntax.dto.handlers.SupplyOperationHandler;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CsvFruitRecordsValidatorTest {
    private static final String emptyString = "";
    private static final String validData
            = "type,fruit,quantity\r\n"
            + "b,banana,20\r\n"
            + "b,apple,100\r\n"
            + "s,banana,100\r\n";
    private static final Operator operator = new Operator();
    private static final FruitRecordsValidator validator
            = new CsvFruitRecordsValidator();

    @Before
    public void setUp() {
        Map<Character, OperationsHandler> typesOfOperations
                = operator.getTypesOfOperations();
        typesOfOperations.put('b', new BalanceOperationHandler());
        typesOfOperations.put('p', new PurchaseOperationHandler());
        typesOfOperations.put('r', new ReturnOperationHandler());
        typesOfOperations.put('s', new SupplyOperationHandler());
    }

    @Test
    public void validation_Ok() {
        boolean expected = true;
        boolean actual = validator.validation(validData, operator);
        assertEquals(expected, actual);
    }

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
        String data = "type,fruit,quantity\r\n"
                + "b,banana,20\n";
        validator.validation(data, operator);
    }

    @Test(expected = RuntimeException.class)
    public void validation_incorrectDataSeparator_Exception() {
        String data = "type;fruit;quantity\r\n"
                + "b;banana;20\r\n";
        validator.validation(data, operator);
    }

    @Test(expected = RuntimeException.class)
    public void validation_incorrectOperation_Exception() {
        String data = "type,fruit,quantity\r\n"
                + "k,banana,20\r\n";
        validator.validation(data, operator);
    }
}
