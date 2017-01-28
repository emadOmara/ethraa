package net.pd.ethraa.integration.response;

public class ReportResponse extends BaseResponse {

    /**
     *
     */
    private static final long serialVersionUID = 1395918555510719966L;

    public ReportResponse() {
	super();
    }

    public ReportResponse(int status, String comment) {
	super(status, comment);
    }

    private Long code;
    private Long count;

    public Long getCode() {
	return code;
    }

    public void setCode(Long code) {
	this.code = code;
    }

    public Long getCount() {
	return count;
    }

    public void setCount(Long count) {
	this.count = count;
    }

}
