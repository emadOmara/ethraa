package net.pd.ethraa.integration.request;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserPointsRequest extends BaseRequest {

    /**
     *
     */
    private static final long serialVersionUID = -296621420686320659L;

    @NotNull
    private List<EvaluationPoint> evaluations;

    @NotNull
    @Min(1)
    private Long trainingId;

    @NotNull
    @Min(1)
    private Long type;

    public List<EvaluationPoint> getEvaluations() {
	return evaluations;
    }

    public void setEvaluations(List<EvaluationPoint> evaluations) {
	this.evaluations = evaluations;
    }

    public Long getTrainingId() {
	return trainingId;
    }

    public void setTrainingId(Long trainingId) {
	this.trainingId = trainingId;
    }

    public Long getType() {
	return type;
    }

    public void setType(Long type) {
	this.type = type;
    }

}
