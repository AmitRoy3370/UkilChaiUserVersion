package com.example.demo700.Services.SecurityService;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class BucketService {

	private Bucket globalBucket = Bucket.builder()
			.addLimit(Bandwidth.classic(1000, Refill.intervally(1000, Duration.ofMinutes(1)))).build();

	private Map<String, Bucket> userCatche = new ConcurrentHashMap<>();

	public Bucket resolvePersonalUses(String key) {

		return userCatche.computeIfAbsent(key, this::createNewBucket);

	}

	private Bucket createNewBucket(String key) {

		return Bucket.builder().addLimit(Bandwidth.classic(40, Refill.intervally(40, Duration.ofMinutes(1)))).build();

	}

	public Bucket getGlobalBucket() {
		return globalBucket;
	}

	public void setGlobalBucket(Bucket globalBucket) {
		this.globalBucket = globalBucket;
	}

	public Map<String, Bucket> getUserCatche() {
		return userCatche;
	}

	public void setUserCatche(Map<String, Bucket> userCatche) {
		this.userCatche = userCatche;
	}

}
