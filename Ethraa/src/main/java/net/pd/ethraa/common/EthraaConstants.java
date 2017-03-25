package net.pd.ethraa.common;

public interface EthraaConstants {

	int ERROR = 0;
	int OK = 1;

	int ACTIVE = 1;
	int INACTIVE = 0;
	String GENERAL_SUCCESS = "Operation done successfully";
	String GENERAL_Error = "Some thing went wrong";
	public String ERROR_MSG_ID_CAN_T_BE_NULL = "Id can't be null";
	public String ERROR_MSG_MOBILE_CAN_T_BE_NULL = "Mobile can't be null";
	public String XA_TOKEN = "xa-token";
	Long ACTIVITY_TYPE_READ_BOOK = 1l;

	Long ACTIVITY_TYPE_LECTURE = 10l;
	Long ACTIVITY_TYPE_COURSE = 2l;
	Long ACTIVITY_TYPE_MEETING = 3l;

	Long EXAM_STATUS_Not_ANSWERED = 0l;
	Long EXAM_STATUS_ANSWERED = 1l;
	Long EXAM_STATUS_EVALUATED = 2l;

	Long EXAM_QUESTION_TYPE_MC = 1l;
	// Integer EXAM_QUESTION_TYPE_SC = 2;
	Long EXAM_QUESTION_TYPE_TEXT = 2l;
	/**
	 * date format like 2017-01-23 18:14:41
	 */
	public String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	// public String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public int RANDOM_PASSWORD_LENGTH = 6;
	String EMAIL_SUBJECT_FORGET_PASSWORD = "نسيان كلمة المرور";
	String EMAIL_BODY_FORGET_PASSWORD = "تم اعادة تعيين لكلمة المرور الخاصة بكم لتصبح : ";

	static final String EMAIL_BODY_ACTIVATE_ACCOUNT = "رجاء العلم بانه قد تم تفعيل حسابك ويمكنك الان تسجيل الدخول";
	static final String EMAIL_SUBJECT_ACTIVATE_ACCOUNT = "تفعيل الحساب";

	Long EXAM_TYPE = 1l;
	Long POLL_TYPE = 2l;
}
