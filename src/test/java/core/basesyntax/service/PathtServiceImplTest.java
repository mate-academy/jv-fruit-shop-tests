package core.basesyntax.service;

import static org.junit.Assert.fail;

import org.junit.Test;

public class PathtServiceImplTest {

    private final PathService pathtService = new PathServiceImpl();

    @Test
    public void getReportFullPath_null_notOk() {
        String dateOfReport = null;
        try {
            pathtService.getReportFullPath(dateOfReport);
        } catch (RuntimeException e) {
            return;
        }
        fail("ValidationException should be thrown if dateOfReport == null");
    }

    @Test
    public void getReportFullPath_emptyString_notOk() {
        String dateOfReport = "";
        try {
            pathtService.getReportFullPath(dateOfReport);
        } catch (RuntimeException e) {
            return;
        }
        fail("ValidationException should be thrown if dateOfReport == emptyString");
    }

    @Test
    public void getInputFullPath_null_notOk() {
        String dateOfInput = null;
        try {
            pathtService.getInputFullPath(dateOfInput);
        } catch (RuntimeException e) {
            return;
        }
        fail("ValidationException should be thrown if dateOfInput == null");
    }

    @Test
    public void getInputFullPath_emptyString_notOk() {
        String dateOfInput = "";
        try {
            pathtService.getInputFullPath(dateOfInput);
        } catch (RuntimeException e) {
            return;
        }
        fail("ValidationException should be thrown if dateOfReport == emptyString");
    }
}
