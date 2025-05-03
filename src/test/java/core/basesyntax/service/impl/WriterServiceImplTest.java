package core.basesyntax.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final CsvWriterServiceImpl writerService = new CsvWriterServiceImpl();

    @Test
    public void writeDataIncorrectPath_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(("Can't write to file with path:  "));
        String outputFilePath = "/a/a/a/";
        writerService.writeToFile(outputFilePath,outputFilePath);
    }
}
