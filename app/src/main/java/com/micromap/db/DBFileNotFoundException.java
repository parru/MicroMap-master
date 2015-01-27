package com.micromap.db;

import java.io.FileNotFoundException;

public class DBFileNotFoundException extends FileNotFoundException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8507880882488102869L;

	public DBFileNotFoundException(){
		super();
	}

	public DBFileNotFoundException(String msg){
		super(msg);
	}
}
