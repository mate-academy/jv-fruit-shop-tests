package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.CsvDataParser;
import core.basesyntax.service.DataParserService;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataParserServiceImplTest {
    private final DataParserService dataParser = new DataParserServiceImpl(
            new CsvDataParser()
    );

    @Test
    public void parse_nullData_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.dataParser.parse(null)
        );
    }

    @Test
    public void parse_invalidOperation_notOk() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                " ,apple,100"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.dataParser.parse(data)
        );
    }

    @Test
    public void parse_invalidFruitName_notOk() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,  ,20",
                "b,apple,100"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.dataParser.parse(data)
        );
    }

    @Test
    public void parse_inputDataHas3Values_ok() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100"
        );

        assertDoesNotThrow(
                () -> this.dataParser.parse(data)
        );
    }

    @Test
    public void parse_inputFruitEntryHasLessThan3Values_notOk() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.dataParser.parse(data)
        );
    }

    @Test
    public void parse_inputFruitEntryHasMoreThan3Values_notOk() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100,10"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.dataParser.parse(data)
        );
    }

    @Test
    public void parse_emptyQuantity_notOk() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,  "
        );

        assertThrows(
                RuntimeException.class,
                () -> this.dataParser.parse(data)
        );
    }

    @Test
    public void parse_invalidQuantityFormat_notOk() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,qty"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.dataParser.parse(data)
        );
    }

    @Test
    public void parse_negativeQuantityNumber_notOk() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,-100"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.dataParser.parse(data)
        );
    }
}
