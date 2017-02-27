package net.pd.ethraa.business;

import java.util.Map;

public interface ReportService {

    Map<Long, Long> summarizeUserReport(Long userId);

    Map<String, Long> getTrainingReport(Long userId, Long type);

    Map<String, Long> getBookReadingReport(Long userId);

}
