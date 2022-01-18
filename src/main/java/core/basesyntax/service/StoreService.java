package core.basesyntax.service;

public interface StoreService {

    void processTodaysInputFile();

    void generateTodaysReportFile();

    void createInputFileForNextDay();

}
