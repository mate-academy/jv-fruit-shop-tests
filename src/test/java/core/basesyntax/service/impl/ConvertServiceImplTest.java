package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.service.ConvertService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvertServiceImplTest {
    private ConvertService convertService = new ConvertServiceImpl();
    private List<String> fileRows;

    @BeforeEach
    void setUp() {
        fileRows = new ArrayList<>();
        fileRows.add("type,fruit,quantity");
    }

    @Test
    void convertData_validFileRows_ok() {
        fileRows.add("b,banana,20");
        fileRows.add("s,banana,100");
        fileRows.add("");
        fileRows.add("p,banana,13");
        fileRows.add("r,banana,10");
        int actual = convertService.convertData(fileRows).size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void convertData_inputFruitQuantityLessThanZero_notOk() {
        fileRows.add("b,apple,-10");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_FileRowContainsOnlyOneElement_notOk() {
        fileRows.add("b");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_fileRowContainsOnlyTwoElements_notOk() {
        fileRows.add("b,banana");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_invalidTypeValue_notOk() {
        fileRows.add("a,banana,10");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_quantityInsteadOfType_notOk() {
        fileRows.add("54,banana,54");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_stringValueInsteadQuantityIntValue_notOk() {
        fileRows.add("b,54,typeOrFruit");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_incorrectColumnsNamesOrder_notOk() {
        fileRows.remove(0);
        fileRows.add("quantity,type,fruit");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_fileContainsTwoColumnsNames_notOk() {
        fileRows.remove(0);
        fileRows.add("type,fruit");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_fileContainsOneColumnName_notOk() {
        fileRows.remove(0);
        fileRows.add("type");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_fileDoesNotContainColumnsNames_notOk() {
        fileRows.remove(0);
        fileRows.add("b,apple,54");
        fileRows.add("b,banana,78");
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(fileRows);
        });
    }

    @Test
    void convertData_nullInputParameter_notOk() {
        assertThrows(RuntimeException.class, () -> {
            convertService.convertData(null);
        });
    }
}
