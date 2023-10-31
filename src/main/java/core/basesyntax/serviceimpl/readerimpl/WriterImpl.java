package core.basesyntax.serviceimpl.readerimpl;

import core.basesyntax.strategy.serviceintrface.reader.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriterImpl implements Writer {
    @Override
    public void writeReportToFile(String inputData, String filePath) {
        if (inputData == null || inputData.length() == 0) {
            throw new RuntimeException("Error the file is empty");
        }
        try {
            Files.write(Paths.get(filePath), inputData.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error while writing a file: " + filePath, e);
        }
    }
}
