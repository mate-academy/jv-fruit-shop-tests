package core.basesyntax.dataservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeDataToFile(String reportToFile, List<String> convertReport) {
        validInputData(reportToFile,convertReport);
        try {
            Files.write(Path.of(reportToFile), convertReport);
        } catch (InvalidPathException | IOException ex) {
            throw new RuntimeException("Can't write to file -> " + reportToFile, ex);
        }
    }
}
