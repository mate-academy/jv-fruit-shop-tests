package core.basesyntax.file.operations;

import java.io.IOException;
import java.util.List;

public interface ReportFileReader {

    List<String> read(String fileName) throws IOException;
}
