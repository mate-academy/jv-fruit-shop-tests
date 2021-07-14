package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ReportsDao;
import core.basesyntax.dao.ReportsDaoImpl;
import core.basesyntax.model.Record;
import core.basesyntax.model.RecordsMapper;
import core.basesyntax.model.RecordsMapperImpl;
import core.basesyntax.model.RecordsValidator;
import core.basesyntax.model.RecordsValidatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class RecordsMapperImplTest {
    private static final String TESTS_FILES_FOLDER = "src/test/java/core/basesyntax/resources/";
    private static final String GOOD_FILE = TESTS_FILES_FOLDER + "goodInput.csv";
    private static final String EMPTY_FILE = TESTS_FILES_FOLDER + "emptyFile.csv";
    private static final String NO_HEADER_INPUT_FILE = TESTS_FILES_FOLDER + "noHeaderInput.csv";
    private static final String INCORRECT_HEADER_INPUT_FILE =
            TESTS_FILES_FOLDER + "incorrectHeaderInput.csv";
    private static final String EXTRA_COLUMN_INPUT_FILE =
            TESTS_FILES_FOLDER + "extraColumnInput.csv";
    private static final String MISSING_COLUMN_FILE = TESTS_FILES_FOLDER + "missingColumnInput.csv";
    private static final ReportsDao reportsDao = new ReportsDaoImpl();
    private static final RecordsValidator recordsValidator = new RecordsValidatorImpl();
    private static final RecordsMapper recordsMapper =
            new RecordsMapperImpl(reportsDao, recordsValidator);
    private static final String FRUIT1 = "banana";
    private static final String FRUIT2 = "apple";
    private static final int[] quantity = {20, 150, 100, 13, 10, 5, 50};

    @Test
    public void map_goodInput_ok() {
        List<Record> expected = new ArrayList<>();
        expected.add(new Record(Record.OperationType.BALANCE, FRUIT1, quantity[0]));
        expected.add(new Record(Record.OperationType.BALANCE, FRUIT2, quantity[1]));
        expected.add(new Record(Record.OperationType.SUPPLY, FRUIT1, quantity[2]));
        expected.add(new Record(Record.OperationType.PURCHASE, FRUIT1, quantity[3]));
        expected.add(new Record(Record.OperationType.RETURN, FRUIT2, quantity[4]));
        expected.add(new Record(Record.OperationType.PURCHASE, FRUIT2, quantity[0]));
        expected.add(new Record(Record.OperationType.PURCHASE, FRUIT1, quantity[5]));
        expected.add(new Record(Record.OperationType.SUPPLY, FRUIT1, quantity[6]));
        assertEquals(expected, recordsMapper.map(GOOD_FILE));
    }

    @Test (expected = IllegalArgumentException.class)
    public void map_emptyFileInput_notOk() {
        recordsMapper.map(EMPTY_FILE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void map_noHeaderInput_notOk() {
        recordsMapper.map(NO_HEADER_INPUT_FILE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void map_incorrectHeaderInput_notOk() {
        recordsMapper.map(INCORRECT_HEADER_INPUT_FILE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void map_missingColumnInput_notOk() {
        recordsMapper.map(MISSING_COLUMN_FILE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void map_extraColumnInput_notOk() {
        recordsMapper.map(EXTRA_COLUMN_INPUT_FILE);
    }
}
