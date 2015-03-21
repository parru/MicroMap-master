package com.micromap.db;

public class DBException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1327656992365460629L;

	public DBException(Throwable t)
	{
		super(t.getMessage());
	}

	public DBException(String msg)
	{
		super(msg);
	}
}
