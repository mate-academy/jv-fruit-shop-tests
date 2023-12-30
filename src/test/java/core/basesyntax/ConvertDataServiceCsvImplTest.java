package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ConvertDataServiceCsvImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConvertDataServiceCsvImplTest {
    private static final String OFFSET_LINE = "type,product,amount";
    private static final String LINE = "b,apple,100";
    private static final String EMPTY_LINE = "";
    private static final String DATA = OFFSET_LINE + System.lineSeparator() + LINE;
    private ConvertDataServiceCsvImpl convertDataServiceCsv;

    @BeforeEach
    public void setUp() {
        convertDataServiceCsv = new ConvertDataServiceCsvImpl();
    }

    @Test
    public void convert_onlyOffsetLine_ok() {
        assertEquals(List.of(), convertDataServiceCsv.convert(OFFSET_LINE));
    }

    @Test
    public void convert_nullData_notOk() {
        assertThrows(NullPointerException.class, () -> convertDataServiceCsv.convert(null));
    }

    @Test
    public void convert_emptySecondLine_ok() {
        String data = OFFSET_LINE + System.lineSeparator() + EMPTY_LINE;
        assertEquals(List.of(), convertDataServiceCsv.convert(data));
    }

    @Test
    public void convert_normalData_ok() {
        assertEquals(List.of(LINE), convertDataServiceCsv.convert(DATA));
    }
}
