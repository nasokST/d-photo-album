package com.dphotoalbum;

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

		PhotoAlbumService.initAlbum();

		SpringApplication.run(DPhotoAlbumApplication.class, args);
	}
}