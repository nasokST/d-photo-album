package com.dphotoalbum.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.dphotoalbum.ipfs.IPFSUtils;
import com.dphotoalbum.objects.IPFSHashUnpacked;

import io.ipfs.multihash.Multihash;

public class IPFSUtilsTest {

	@Test
	public void IPFSHashConverting() {

		byte[] bts = Multihash.fromBase58("QmPZ9gcCEpqKTo6aq61g2nXGUhM4iCL3ewB6LDXZCtioEB").toBytes(); 
				
		Multihash multihash = new Multihash(bts);

		IPFSHashUnpacked hashinterface = IPFSUtils.ipfsHashToHashInterface(multihash);

		Multihash rezMultihash = IPFSUtils.hashInterfaceToIPFSHash(hashinterface);
		
		assertTrue("incorrect multihashs", multihash.toString().equals(rezMultihash.toString()));
	}
}
