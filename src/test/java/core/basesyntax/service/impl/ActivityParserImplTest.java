package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.ActivityDto;
import core.basesyntax.enums.ActivityType;
import core.basesyntax.exception.CsvParseException;
import core.basesyntax.service.ActivityParser;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class ActivityParserImplTest {
    private final ActivityParser parser = new ActivityParserImpl();

    @Test
    void parse_Ok() {
        List<String> activityRows = List.of(
                "",
                "b,banana,20",
                "s,apple,100",
                "p,banana,13",
                "r,apple,10"
        );
        List<ActivityDto> expectedDto = List.of(
                new ActivityDto(ActivityType.BALANCE, "banana", 20),
                new ActivityDto(ActivityType.SUPPLY, "apple", 100),
                new ActivityDto(ActivityType.PURCHASE, "banana", 13),
                new ActivityDto(ActivityType.RETURN, "apple", 10)
        );
        List<ActivityDto> actualParsedDto = parser.parse(activityRows);
        assertEquals(expectedDto, actualParsedDto);
    }

    @Test
    void parse_null_Ok() {
        List<ActivityDto> expectedDto = Collections.emptyList();
        List<ActivityDto> actualParsedDto = parser.parse(null);
        assertEquals(expectedDto, actualParsedDto);
    }

    @Test
    void parse_empty_Ok() {
        List<ActivityDto> expectedDto = Collections.emptyList();
        List<ActivityDto> actualParsedDto = parser.parse(Collections.emptyList());
        assertEquals(expectedDto, actualParsedDto);
    }

    @Test
    void parse_onlyHeader_Ok() {
        List<String> activityRows = List.of(
                ""
        );
        List<ActivityDto> expectedDto = Collections.emptyList();
        List<ActivityDto> actualParsedDto = parser.parse(activityRows);
        assertEquals(expectedDto, actualParsedDto);
    }

    @Test
    void parse_twoColumns_notOk() {
        List<String> activityRows = List.of(
                "",
                "b,banana"
        );
        CsvParseException existsException = assertThrows(
                CsvParseException.class,
                () -> parser.parse(activityRows)
        );
        assertEquals("Incorrect number of columns in CSV file",
                existsException.getMessage());
    }

    @Test
    void parse_notValidFirstColumn_notOk() {
        List<String> activityRows = List.of(
                "",
                "aaaaa,banana,5"
        );
        CsvParseException existsException = assertThrows(
                CsvParseException.class,
                () -> parser.parse(activityRows)
        );
    }

    @Test
    void parse_notValidThirdColumn_notOk() {
        List<String> activityRows = List.of(
                "",
                "b,banana,k"
        );
        CsvParseException existsException = assertThrows(
                CsvParseException.class,
                () -> parser.parse(activityRows)
        );
    }
}
