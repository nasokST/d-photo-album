package com.dphotoalbum;

import java.io.IOException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import com.dphotoalbum.services.PhotoAlbumService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.dphotoalbum.*" })
@ComponentScan(basePackages = { "com.shumencoin.*" })
@EnableAsync
public class DPhotoAlbumApplication {

	public static void main(String[] args) {

		Security.addProvider(new BouncyCastleProvider());

//		try {
//			IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
//
//			// add bytes to IPFS
//			NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper("hello.txt",
//					"G'day world! IPFS rocks!".getBytes());
//			
//			MerkleNode addResult = ipfs.add(file).get(0);
//			
//			String toStr = addResult.hash.toString();
//			String toHx = addResult.hash.toHex();
//			String toBase58 = addResult.hash.toBase58();
//			
//			byte[] toBts = addResult.hash.toBytes();			
//
//			// To get a file use:
//			Multihash filePointer = Multihash.fromBase58(toBase58);
//			byte[] fileContents = ipfs.cat(filePointer);
//				
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		//PhotoAlbumService.deployAlbum();
		//PhotoAlbumService.deployAlbumCategories();

		SpringApplication.run(DPhotoAlbumApplication.class, args);
	}
}