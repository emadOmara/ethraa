package net.pd.ethraa.integration;

import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.integration.response.BaseResponse;

public abstract class BaseController {

    protected void handleSuccessResponse(BaseResponse response, Object result) {
	response.setStatus(EthraaConstants.OK);
	response.setComment(EthraaConstants.GENERAL_SUCCESS);
	response.setResult(result);
    }

    protected void handleNullID(Long... ids) throws EthraaException {

	for (Long id : ids) {
	    if (id == null || id < 1) {
		throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	    }

	}

    }
}
