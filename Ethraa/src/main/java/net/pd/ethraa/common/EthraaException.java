package net.pd.ethraa.common;

public class EthraaException extends Exception {

    public EthraaException(String msg) {
	super(msg);
    }

    public EthraaException(String msg, Throwable cause) {
	super(msg, cause);
    }

    public EthraaException(Exception e) {
	super(e);
    }

    /**
     *
     */
    private static final long serialVersionUID = -6913547944608757439L;

}
