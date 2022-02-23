package core.basesyntax.service;

import java.io.File;

public interface FileWriterService {

    void writeToFile(String fileName);

    void writeStringToFile(File testReportFile, String expected);
}
