package core.basesyntax.shop.strategy;

import core.basesyntax.shop.service.Reader;
import core.basesyntax.shop.service.impl.ReaderFromCsvFile;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReadStrategyTest {
    private static final String SUPPORTED_FILETYPE = "src/test/resources/correct.csv";
    private static final String UNSUPPORTED_FILETYPE = "src/test/resources/correct.txt";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void chooseReadFileFormat_readFromSupportedFileType_ok() {
        Reader actual = FileReadStrategy.chooseReadFileFormat(SUPPORTED_FILETYPE);
        Assert.assertEquals(actual.getClass(), ReaderFromCsvFile.class);
    }

    @Test
    public void chooseReadFileFormat_readFromUnsupportedFileType_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Unsupported file type: txt");
        FileReadStrategy.chooseReadFileFormat(UNSUPPORTED_FILETYPE);
    }
}
