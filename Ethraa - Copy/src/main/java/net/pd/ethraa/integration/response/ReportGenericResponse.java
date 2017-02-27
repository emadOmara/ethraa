package net.pd.ethraa.integration.response;

import java.util.ArrayList;
import java.util.List;

public class ReportGenericResponse extends BaseResponse {

    /**
     *
     */
    private static final long serialVersionUID = 1395918555510719966L;

    public ReportGenericResponse() {
	super();
    }

    public ReportGenericResponse(int status, String comment) {
	super(status, comment);
    }

    private Long code;
    private Long count;
    List<ReportResponse> items = new ArrayList<>();

    public List<ReportResponse> getItems() {
	return items;
    }

    public void setItems(List<ReportResponse> items) {
	this.items = items;
    }

}
