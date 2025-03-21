package core.basesyntax.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    @Test
    void dataConverterTestStringOk() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,wrong");
        DataConverter dataConverter = new DataConverterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));

        assertEquals("Invalid number format: 'wrong'. Expected an integer value.",
                exception.getMessage());
    }

    @Test
    void dataConverterTestLessZeroOk() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,-10");
        DataConverter dataConverter = new DataConverterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));

        assertEquals("Error! Number can't be less than zero", exception.getMessage());
    }

}
