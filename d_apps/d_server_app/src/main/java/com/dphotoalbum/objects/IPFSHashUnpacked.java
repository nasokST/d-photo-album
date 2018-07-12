package com.dphotoalbum.objects;

import java.io.Serializable;
import java.math.BigInteger;

public class IPFSHashUnpacked implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1774136045033165918L;

	public byte[] digest;
	public BigInteger hashFunction;
	public BigInteger size;

}
