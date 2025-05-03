package core.basesyntax.service;

public interface PathService {

    String getReportFullPath(String dateOfReport);

    //чтобы создать nextDayInputFile нужно будет получить для него fullPath
    String getInputFullPath(String dateOfInput);
}
