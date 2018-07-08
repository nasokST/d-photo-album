package com.shumencoin.node;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shumencoin.convertion.Converter;
import com.shumencoin.crypto.Crypto;

@SpringBootApplication
@ComponentScan(basePackages = { "com.dphotoalbum.*" })
@ComponentScan(basePackages = { "com.shumencoin.*" })
@EnableAsync
public class NodeApplication {

	static int port = 8080;

	public static void main(String[] args) {

		Security.addProvider(new BouncyCastleProvider());

		for (String s : args) {
			switch (s.substring(0, 2)) {
			case "-p":
				port = Integer.valueOf(s.substring(3, s.length()));
				System.out.println(port);
				break;
			}
		}

		SpringApplication.run(NodeApplication.class, args);
	}
}