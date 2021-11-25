package core.basesyntax.shop.strategy;

import core.basesyntax.shop.service.Reader;
import core.basesyntax.shop.service.impl.ReaderFromCsvFile;
import org.junit.Assert;
import org.junit.Test;

public class FileReadStrategyTest {
    private static final String SUPPORTED_FILETYPE = "src/test/resources//correct.csv";
    private static final String UNSUPPORTED_FILETYPE = "src/test/resources//correct.txt";

    @Test
    public void chooseReadFileFormat_readFromSupportedFileType_ok() {
        Reader actual = FileReadStrategy.chooseReadFileFormat(SUPPORTED_FILETYPE);
        Assert.assertEquals(actual.getClass(), ReaderFromCsvFile.class);
    }

    @Test
    public void chooseReadFileFormat_readFromUnsupportedFileType_notOk() {
        try {
            FileReadStrategy.chooseReadFileFormat(UNSUPPORTED_FILETYPE);
        } catch (Exception e) {
            return;
        }
        Assert.fail("Unsupported file type: " + UNSUPPORTED_FILETYPE
                .replaceAll("(^.*(\\w{3,4})$)", "$2")
                + ". Exception should be thrown");
    }
}
