package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.WriteParser;
import core.basesyntax.service.impl.WriteParserImpl;
import org.junit.Before;
import org.junit.Test;

public class WriteParserTest {
    private WriteParser writeParser;

    @Before
    public void setUp() {
        writeParser = new WriteParserImpl();
    }

    @Test
    public void writeParse_ValidData_Ok() {
        Storage.storageMap.put("apple", 5);
        Storage.storageMap.put("banana", 10);
        String report = writeParser.parse();
        String expectedReport = "fruit,quantity\napple,5\nbanana,10\n";
        assertEquals(expectedReport, report);
    }
}
