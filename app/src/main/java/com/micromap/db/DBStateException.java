package com.micromap.db;

public class DBStateException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1327656992365460629L;

	public DBStateException()
	{
		super();
	}

	public DBStateException(String msg)
	{
		super(msg);
	}

}