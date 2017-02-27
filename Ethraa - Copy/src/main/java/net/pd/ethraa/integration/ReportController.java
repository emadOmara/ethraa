package net.pd.ethraa.integration;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.pd.ethraa.business.ReportService;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/reports")
public class ReportController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @GetMapping(path = "/summary/{userId}")
    public BaseResponse getSummaryReport(@PathVariable("userId") Long userId) throws EthraaException {

	handleNullID(userId);
	BaseResponse response = new BaseResponse();

	Map<Long, Long> result = reportService.summarizeUserReport(userId);
	handleSuccessResponse(response, result);

	return response;

    }

    @GetMapping(path = "/training/{userId}/{type}")
    public BaseResponse addTrainingExraPoints(@PathVariable("userId") Long userId, @PathVariable("type") Long type)
	    throws EthraaException {

	handleNullID(userId);
	BaseResponse response = new BaseResponse();

	Map<String, Long> result = reportService.getTrainingReport(userId, type);
	handleSuccessResponse(response, result);

	return response;

    }

    @GetMapping(path = "/books/{userId}")
    public BaseResponse addTrainingExraPoints(@PathVariable("userId") Long userId) throws EthraaException {

	handleNullID(userId);
	BaseResponse response = new BaseResponse();

	Map<String, Long> result = reportService.getBookReadingReport(userId);
	handleSuccessResponse(response, result);

	return response;

    }

}
