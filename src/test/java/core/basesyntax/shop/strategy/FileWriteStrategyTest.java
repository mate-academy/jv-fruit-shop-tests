package core.basesyntax.shop.strategy;

import core.basesyntax.shop.service.Writer;
import core.basesyntax.shop.service.impl.WriterToCsvFile;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriteStrategyTest {
    private static final String SUPPORTED_FILETYPE = "src/test/resources/correct.csv";
    private static final String UNSUPPORTED_FILETYPE = "src/test/resources/correct.txt";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void chooseWriteFileFormat_writeToSupportedFileType_ok() {
        Writer actual = FileWriteStrategy.chooseWriteFileFormat(SUPPORTED_FILETYPE);
        Assert.assertEquals(actual.getClass(), WriterToCsvFile.class);
    }

    @Test
    public void chooseWriteFileFormat_writeToUnsupportedFileType_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Unsupported file type: txt");
        FileWriteStrategy.chooseWriteFileFormat(UNSUPPORTED_FILETYPE);
    }
}
