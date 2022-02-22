package core.basesyntax.dataservice;

import java.util.List;

public interface FileWriterService {
    void writeDataToFile(String reportToFile, List<String> convertReport);

    default void validInputData(String reportToFile, List<String> convertReport) {
        if (reportToFile == null || convertReport == null) {
            throw new RuntimeException("Input data can't be null");
        }
        if (reportToFile.isEmpty() || convertReport.isEmpty()) {
            throw new RuntimeException("Input data can't be empty");
        }
    }
}
