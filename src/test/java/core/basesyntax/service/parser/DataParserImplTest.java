package core.basesyntax.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataParserImplTest {
    private static final int FIRST_LINE_OF_DATA = 0;
    private static final int SECOND_LINE_OF_DATA = 1;
    private final DataParserImpl dataParser = new DataParserImpl();

    @Test
    void parse_validData_isOk() {
        List<String> rawData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "r,apple,10"
        );
        List<FruitTransactionDto> result = dataParser.parse(rawData);
        FruitTransactionDto dto1 = result.get(FIRST_LINE_OF_DATA);
        assertEquals("b", dto1.getOperationType());
        assertEquals("banana", dto1.getNameFruit());
        assertEquals(20, dto1.getQuantity());

        FruitTransactionDto dto2 = result.get(SECOND_LINE_OF_DATA);
        assertEquals("r", dto2.getOperationType());
        assertEquals("apple", dto2.getNameFruit());
        assertEquals(10, dto2.getQuantity());
    }

    @Test
    void parse_emptyData_notOk() {
        List<String> rawData = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> dataParser.parse(rawData));
        String actual = assertThrows(RuntimeException.class, () ->
                dataParser.parse(rawData)).getMessage();
        assertEquals("File is empty", actual);
    }
}
