package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class CsvDataParserTest {
    private final DataParser csvDataParser = new CsvDataParser();

    @Test
    public void parse_validInput_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        assertDoesNotThrow(
                () -> this.csvDataParser.parse(input)
        );
    }

    @Test
    public void parse_nullInput_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.csvDataParser.parse(null)
        );
    }

    @Test
    public void parse_invalidEntryOperation_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                ",apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.csvDataParser.parse(input)
        );
    }

    @Test
    public void parse_emptyEntryFruitName_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,   ,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.csvDataParser.parse(input)
        );
    }

    @Test
    public void parse_entryDataHasMoreValues_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,apple,100",
                "p,banana,13",
                "r,apple,10,4",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.csvDataParser.parse(input)
        );
    }

    @Test
    public void parse_entryDataHasLessValues_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,apple,100",
                "p,banana,13",
                "r,apple",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.csvDataParser.parse(input)
        );
    }

    @Test
    public void parse_entryQtyIsEmpty_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,        ",
                "s,banana,50"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.csvDataParser.parse(input)
        );
    }

    @Test
    public void parse_entryQtyHasWrongFormat_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,qw12",
                "s,banana,50"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.csvDataParser.parse(input)
        );
    }

    @Test
    public void parse_entryQtyIsLess0_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,-100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,qw12",
                "s,banana,50"
        );

        assertThrows(
                RuntimeException.class,
                () -> this.csvDataParser.parse(input)
        );
    }
}
