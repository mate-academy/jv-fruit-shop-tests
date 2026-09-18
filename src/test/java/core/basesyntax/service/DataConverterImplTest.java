package core.basesyntax.service;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private DataConverterImpl dataConverterImpl;

    @BeforeEach
    void setUp() {
        dataConverterImpl = new DataConverterImpl();
        Storage.clear();
    }

    @Test
    void convert_invalidNumberFormat_throwsException() {
        List<String> inputLines = new ArrayList<>();
        inputLines.add("type,fruit,quantity");
        inputLines.add("b,banana,NOT_A_NUMBER");
        assertThrows(NumberFormatException.class,
                () -> dataConverterImpl.convertToTransaction(inputLines));
    }
}
