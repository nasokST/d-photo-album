package com.dphotoalbum.ipfs;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import com.dphotoalbum.config.DPhotoAlbumConfig;
import com.dphotoalbum.objects.IPFSHashUnpacked;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

public class IPFSUtils {
	
	public static IPFSHashUnpacked uploadDoc(String name, byte[] docFile) {
		IPFSHashUnpacked hashInterface = null;

		try {
			NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(name, docFile);

			IPFS ipfs = new IPFS(DPhotoAlbumConfig.getIPFSProvider());			
			
			MerkleNode addResult = ipfs.add(file).get(0);

			hashInterface = ipfsHashToHashInterface(addResult.hash);

		} catch (IOException e) {
			e.printStackTrace();
		}		

		return hashInterface;
	}
	
	public static byte[] downloadFile(IPFSHashUnpacked hashInterface) {
		
		byte[] docFile = null;
		try {

			Multihash multihash = hashInterfaceToIPFSHash(hashInterface);
			
			IPFS ipfs = new IPFS(DPhotoAlbumConfig.getIPFSProvider());
			docFile = ipfs.cat(multihash);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return docFile;
	}
	
	public static IPFSHashUnpacked ipfsHashToHashInterface(Multihash multihash) {
		IPFSHashUnpacked hashInterface = new IPFSHashUnpacked();

		hashInterface.digest = Arrays.copyOfRange(multihash.toBytes(), 2, multihash.toBytes().length);
		hashInterface.hashFunction = new BigInteger(String.valueOf(multihash.toBytes()[0]));
		hashInterface.size = new BigInteger(String.valueOf(multihash.toBytes()[1]));

		return hashInterface;
	}

	public static Multihash hashInterfaceToIPFSHash(IPFSHashUnpacked hashInterface) {
		
		byte[] multihashBts = new byte[hashInterface.digest.length + 2];
		
		multihashBts[0] = hashInterface.hashFunction.byteValue();
		multihashBts[1] = hashInterface.size.byteValue();
		System.arraycopy(hashInterface.digest, 0, multihashBts, 2, hashInterface.digest.length);

		Multihash multihash = new Multihash(multihashBts);
		return multihash;
	}	
}
