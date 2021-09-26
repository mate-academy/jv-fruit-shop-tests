package core.basesyntax.implementation;

import core.basesyntax.Record;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static List<Record> records = new ArrayList<>();
    private static Record correctRecord;
    private static Record inCorrectRecord;
    private static Record nullRecord;
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = new ValidatorImpl();
        correctRecord = new Record("b","banana",10);
        inCorrectRecord = new Record("v","apple",-200);
        nullRecord = new Record(null,null, -1);
    }

    @Test
    public void validate_CorrectData_OK() {
        records.add(correctRecord);
        validator.validate(records);
    }

    @After
    public void tearDown() {
        records.clear();
    }

    @Test(expected = ValidationException.class)
    public void validate_inCorrectData_notOk() {
        records.add(inCorrectRecord);
        validator.validate(records);
    }

    @Test(expected = ValidationException.class)
    public void validate_nullInputData_notOk() {
        records.add(inCorrectRecord);
        validator.validate(records);
    }
}
