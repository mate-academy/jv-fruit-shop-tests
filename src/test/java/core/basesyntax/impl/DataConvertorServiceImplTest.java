package core.basesyntax.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.ConvertationException;
import core.basesyntax.service.DataConvertorService;
import core.basesyntax.service.impl.DataConvertorServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConvertorServiceImplTest {
    private DataConvertorService dataConvertorService = new DataConvertorServiceImpl();

    @Test
    void convertData_nullList_NotOk() {
        assertThrows(NullPointerException.class, () -> dataConvertorService.convertData(null));
    }

    @Test
    void convertData_emptyList_NotOk() {
        List<String> list = List.of();
        assertThrows(ConvertationException.class, () -> dataConvertorService.convertData(list));
    }

    @Test
    void convertData_incorrectStrategy_NotOk() {
        List<String> list = List.of("first,line,one", "wrongStrategy,line,2");
        assertThrows(ConvertationException.class, () -> dataConvertorService.convertData(list));
    }

    @Test
    void convertData_incorrectQuantity_NotOk() {
        List<String> list = List.of("first,line,one", "b,line,wrongQuantity");
        assertThrows(NumberFormatException.class, () -> dataConvertorService.convertData(list));
    }

    @Test
    void convertData_incorrectQuantityColumn_NotOk() {
        List<String> list = List.of("first,line,one", "b,2");
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> dataConvertorService.convertData(list));
    }
}
