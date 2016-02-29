package com.vteba.exception;

import com.vteba.common.exception.ServiceException;

public class BusinessService extends ServiceException {

	private static final long serialVersionUID = -2033421811850461700L;

	public BusinessService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessService(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BusinessService(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BusinessService(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BusinessService(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
