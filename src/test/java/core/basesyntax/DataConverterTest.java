package core.basesyntax;

import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.DataConverter;
import core.basesyntax.dao.impl.DataConverterImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convert_toWrongFormat_notOk() {
        List<String> report = Arrays.asList("BALANCE,apple", "SUPPLY,banana,50");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(report));
    }

    @Test
    void convert_illegalOperation_notOK() {
        List<String> report = Arrays.asList("NONEXISTENTOPERATION,apple,100", "SUPPLY,banana,50");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(report));
    }

    @Test
    void convert_illegalQuantity_notOK() {
        List<String> report = Arrays.asList("BALANCE,apple,-20", "SUPPLY,banana,50");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(report));
    }

    @Test
    void convert_illegalQuantityFormat_notOK() {
        List<String> report = Arrays.asList("BALANCE,apple,20.00", "SUPPLY,banana,50");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(report));
    }
}
