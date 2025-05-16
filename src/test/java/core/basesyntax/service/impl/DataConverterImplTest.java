package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_numberOfDataLinesInInputFileAndOutputListCoincide_Ok() {
        List<String> inputReport = new ArrayList<>();
        inputReport.add("type,fruit,quantity");
        inputReport.add("b,banana,7");
        inputReport.add("p,banana,3");
        int expected = inputReport.size() - 1;
        int actual = dataConverter.convertToTransaction(inputReport).size();
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_inputStringIsEmpty_NotOk() {
        List<String> inputReport = new ArrayList<>();
        inputReport.add("type,fruit,quantity");
        inputReport.add("");
        assertThrows(RuntimeException.class, () -> dataConverter
                .convertToTransaction(inputReport));
    }

    @Test
    void convertToTransactton_inputAmountIsNotANumber_NotOk() {
        List<String> inputReport = new ArrayList<>();
        inputReport.add("type,fruit,quantity");
        inputReport.add("r,banana,seven");
        assertThrows(NumberFormatException.class, () -> dataConverter
                .convertToTransaction(inputReport));
    }
}
