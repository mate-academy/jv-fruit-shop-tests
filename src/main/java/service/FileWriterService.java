package service;

import java.util.List;

public interface FileWriterService {
    boolean write(String outputFile, List<String> report);
}
