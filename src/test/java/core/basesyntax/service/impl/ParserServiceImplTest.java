package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidDataFormatException;
import core.basesyntax.model.Operation;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.interfaces.ParserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserServiceImplTest {
    private static final String EMPTY_LINE = "";
    private static final String INVALID_CSV_VALUES = "b,banana,25,45";
    private static final String INVALID_OPERATION = "v,banana,25";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private final ParserService parserService = new ParserServiceImpl();

    @Test
    public void parse_validData_Ok() {
        FruitRecordDto expected = new FruitRecordDto(Operation.BALANCE, "orange", 25);
        FruitRecordDto actual = parserService.parse("b,orange,25");
        assertEquals(expected, actual);
    }

    @Test
    public void parse_emptyLine_notOk() {
        expectedException.expect(InvalidDataFormatException.class);
        expectedException.expectMessage("Empty line");
        parserService.parse(EMPTY_LINE);
    }

    @Test
    public void parse_invalidCsvFormat_notOk() {
        expectedException.expect(InvalidDataFormatException.class);
        expectedException.expectMessage("Invalid data format");
        parserService.parse(INVALID_CSV_VALUES);
    }

    @Test
    public void parse_unexpectedOperation_notOk() {
        expectedException.expect(InvalidDataFormatException.class);
        expectedException.expectMessage("Invalid data format");
        parserService.parse(INVALID_OPERATION);
    }

    @Test
    public void parse_nullLine_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Line can't be null");
        parserService.parse(null);
    }
}
