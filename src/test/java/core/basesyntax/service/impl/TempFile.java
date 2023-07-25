package core.basesyntax.service.impl;

import java.io.File;
import java.io.IOException;

public class TempFile {
    public File createTempInputFile() throws IOException {
        String resourcesPath = "src/test/java/resources";

        File tempFile = File.createTempFile("input", ".csv", new File(resourcesPath));

        if (!tempFile.exists()) {
            throw new IOException("Failed to create input file in src/test/resources");
        }

        return tempFile;
    }

    public File createTempReportFile() throws IOException {
        String resourcesPath = "src/test/java/resources";

        File tempFile = File.createTempFile("report", ".csv", new File(resourcesPath));

        if (!tempFile.exists()) {
            throw new IOException("Failed to create report file in src/test/resources");
        }

        return tempFile;
    }
}
