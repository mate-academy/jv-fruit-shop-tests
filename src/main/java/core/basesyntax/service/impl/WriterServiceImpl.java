package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterServiceImpl implements WriterService {

    @Override
    public void write(File outputFileName, String report) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName))) {
            if (report != null) {
                bufferedWriter.write(report);
            }
        } catch (IOException e) {
            throw new RuntimeException("File " + outputFileName + " can't be wrote!", e);
        }
    }
}
