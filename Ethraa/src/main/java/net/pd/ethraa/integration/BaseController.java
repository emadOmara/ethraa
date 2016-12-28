package net.pd.ethraa.integration;

import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.integration.response.BaseResponse;

public abstract class BaseController {

    protected void handleSuccessResponse(BaseResponse response, Object result) {
	response.setStatus(EthraaConstants.OK);
	response.setComment(EthraaConstants.GENERAL_SUCCESS);
	response.setResult(result);
    }

    protected void handleFailureResponse(BaseResponse response, Exception e) {
	response.setStatus(EthraaConstants.ERROR);
	response.setComment(e.getMessage());
    }
}
