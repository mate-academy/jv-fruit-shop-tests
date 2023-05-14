package core.basesyntax.strategy.filestrategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.InvalidFileTypeException;
import core.basesyntax.strategy.filestrategy.impl.CsvDataParserImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParsingStrategyTest {
    private static Map<String, DataParser> dataParsersMap;
    private static DataParsingStrategy dataParsingStrategy;

    @BeforeClass
    public static void setUp() {
        Map<String, DataParser> dataParsersMap = new HashMap<>();
        dataParsersMap.put(FileType.CSV.getName(), new CsvDataParserImpl());
        dataParsingStrategy = new DataParsingStrategy(dataParsersMap);
    }

    @Test
    public void getDataParser_validCsvInput_ok() {
        Class<CsvDataParserImpl> expected = CsvDataParserImpl.class;
        Class<? extends DataParser> actual = dataParsingStrategy.getDataParser("csv").getClass();
        assertEquals("Should return instance of CsvDataParserImpl "
                + "when input is \"csv\", but was instance of " + actual, expected, actual);
    }

    @Test(expected = InvalidFileTypeException.class)
    public void getDataParser_invalidInput_notOk() {
        dataParsingStrategy.getDataParser("invalid type");

    }

    @Test(expected = InvalidFileTypeException.class)
    public void getDataParser_nullInput_notOk() {
        dataParsingStrategy.getDataParser(null);
    }
}
