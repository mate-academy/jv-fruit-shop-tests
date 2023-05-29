package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import core.basesyntax.service.Writer;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static final List<String> TEST_LIST = List.of("banana,20", "apple,50");
    private static final String PATH = "src/main/resources/test/reportForTests.csv";
    private final Writer writer = new WriterImpl();
    private final Reader reader = new ReaderImpl();

    @Test
    void writeReport_Ok() {
        writer.writeReport(PATH, TEST_LIST);
        List<String> actual = reader.readFile(PATH);
        Assert.assertEquals(TEST_LIST, actual);
    }
}
