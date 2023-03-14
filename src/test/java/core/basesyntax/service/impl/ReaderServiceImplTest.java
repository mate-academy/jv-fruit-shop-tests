package core.basesyntax.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final ReaderServiceImpl readerService = new ReaderServiceImpl();

    @Test
    public void getDataIncorrectPath_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(("Can't read from file by path: "));
        String inputSourcePath = "test";
        readerService.getDataFromCsv(inputSourcePath);
    }
}
