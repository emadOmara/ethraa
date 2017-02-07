package net.pd.ethraa.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.BookDao;
import net.pd.ethraa.dao.TrainingDao;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

	@Autowired
	private TrainingDao trainingDao;

	@Autowired
	private BookDao bookDao;
	@Autowired
	private AccountDao accountDao;

	private static final Long REPORT_RESPONSE_TYPE_ALL = 0l;
	private static final Long REPORT_RESPONSE_TYPE_READ_BOOK = 1l;
	// private static final Long REPORT_RESPONSE_TYPE_COURSE = 2l;
	// private static final Long REPORT_RESPONSE_TYPE_MEETING = 3l;
	private static final Long REPORT_RESPONSE_TYPE_COURSE_BONUS = 4l;
	private static final Long REPORT_RESPONSE_TYPE_MEETING_BONUS = 5l;

	@Override
	public Map<Long, Long> summarizeUserReport(Long userId) {
		Map<Long, Long> result = new HashMap<>();

		addTrainingAttendence(result, userId);
		addTrainngBonus(result, userId);
		addBookReading(result, userId);

		Long totalSum = result.values().stream().mapToLong(Number::longValue).sum();
		result.put(REPORT_RESPONSE_TYPE_ALL, totalSum);

		return result;

	}

	private void addBookReading(Map<Long, Long> result, Long userId) {
		List<Long> trainingTypes = new ArrayList<>();
		trainingTypes.add(EthraaConstants.ACTIVITY_TYPE_READ_BOOK);
		Object[] sumUserPoints = accountDao.sumUserPoints(userId, trainingTypes);

		if (sumUserPoints.length == 0 || ((Object[]) sumUserPoints[0]).length < 2) {
			result.put(REPORT_RESPONSE_TYPE_READ_BOOK, 0l);
			return;
		}
		Long grade = (Long) ((Object[]) sumUserPoints[0])[1];
		result.put(REPORT_RESPONSE_TYPE_READ_BOOK, grade);

	}

	private void addTrainngBonus(Map<Long, Long> result, Long userId) {
		List<Long> trainingTypes = new ArrayList<>();
		trainingTypes.add(EthraaConstants.ACTIVITY_TYPE_COURSE);
		trainingTypes.add(EthraaConstants.ACTIVITY_TYPE_MEETING);
		Object[] trainingBonus = accountDao.sumUserPoints(userId, trainingTypes);

		result.put(REPORT_RESPONSE_TYPE_COURSE_BONUS, 0l);
		result.put(REPORT_RESPONSE_TYPE_MEETING_BONUS, 0l);

		for (Object item : trainingBonus) {
			Long type = (Long) ((Object[]) item)[0];
			Long grade = (Long) ((Object[]) item)[1];
			if (EthraaConstants.ACTIVITY_TYPE_COURSE.equals(type)) {
				result.put(REPORT_RESPONSE_TYPE_COURSE_BONUS, grade);
			} else if (EthraaConstants.ACTIVITY_TYPE_MEETING.equals(type)) {
				result.put(REPORT_RESPONSE_TYPE_MEETING_BONUS, grade);
			}

		}
	}

	private void addTrainingAttendence(Map<Long, Long> result, Long userId) {
		Object[] trainingPoints = trainingDao.countTrainingPoints(userId);
		if (trainingPoints.length == 0) {
			result.put(EthraaConstants.ACTIVITY_TYPE_MEETING, 0l);
			result.put(EthraaConstants.ACTIVITY_TYPE_COURSE, 0l);
			return;
		}
		for (Object item : trainingPoints) {
			Long type = (Long) ((Object[]) item)[0];
			Long count = (Long) ((Object[]) item)[1];
			Long attendencePoints = (Long) ((Object[]) item)[2];
			result.put(type, count * attendencePoints);

		}

	}

	@Override
	public Map<String, Long> getTrainingReport(Long userId, Long type) {
		Map<String, Long> result = new HashMap<>();

		Object[] trainingPoints = trainingDao.getAllTrainingsWithAttendence(userId, type);
		for (Object item : trainingPoints) {
			String title = (String) ((Object[]) item)[0];
			Long count = (Long) ((Object[]) item)[1];
			Long attendencePoints = (Long) ((Object[]) item)[2];
			result.put(title, count * attendencePoints);

		}
		return result;
	}

	@Override
	public Map<String, Long> getBookReadingReport(Long userId) {
		Map<String, Long> result = new HashMap<>();

		Object[] sumUserPoints = bookDao.sumUserPoints(userId, EthraaConstants.ACTIVITY_TYPE_READ_BOOK);

		String bookName = (String) ((Object[]) sumUserPoints[0])[0];
		Long grade = (Long) ((Object[]) sumUserPoints[0])[1];
		if (!StringUtils.isEmpty(bookName)) {
			result.put(bookName, grade);
		}
		return result;
	}

}
