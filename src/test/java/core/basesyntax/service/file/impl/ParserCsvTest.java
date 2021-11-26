package core.basesyntax.service.file.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.CsvLineDto;
import core.basesyntax.service.file.Parser;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserCsvTest {
    private static Parser parser;
    private static String[] validData;
    private static String[] emptyLine;
    private static String[] emptyColumn;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserCsv();
        validData = new String[]{
                "b,banana,20",
                "s,banana,100",
                "p,banana,13",
                "r,banana,10",
        };
        emptyLine = new String[] {
                ""
        };
        emptyColumn = new String[] {
                "s,banana,"
        };
    }

    @Test
    public void parse_ValidData_Ok() {
        List<CsvLineDto> expected = List.of(
                new CsvLineDto("b","banana", "20"),
                new CsvLineDto("s","banana", "100"),
                new CsvLineDto("p","banana", "13"),
                new CsvLineDto("r","banana", "10")
        );
        List<CsvLineDto> actual = parser.parse(validData);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_EmptyLine_NotOk() {
        parser.parse(emptyLine);
    }

    @Test(expected = RuntimeException.class)
    public void parse_EmptyColumn_NotOk() {
        parser.parse(emptyColumn);
    }
}
