package de.soulworks.dam.webservice.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class ExceptionWrapper {

	@XmlElement
	private String throwableName;

	@XmlElement
	private String message;
	
	private StackTraceElement[] frames;

	public static ExceptionWrapper createFromThrowable(Throwable throwable) {
		ExceptionWrapper wrapper = new ExceptionWrapper();
		wrapper.setStacktrace(throwable.getStackTrace());
		wrapper.setMessage(throwable.getMessage());
		wrapper.setThrowableName(throwable.getClass().getName());
		return wrapper;
	}

	public String getThrowableName() {
		return throwableName;
	}

	public void setThrowableName(String throwableName) {
		this.throwableName = throwableName;
	}

	public StackTraceElement[] getStacktrace() {
		return frames;
	}

	public void setStacktrace(StackTraceElement[] frames) {
		this.frames = frames;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
