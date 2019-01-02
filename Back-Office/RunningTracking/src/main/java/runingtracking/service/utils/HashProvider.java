package runingtracking.service.utils;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

@Component
public class HashProvider {
	
	private Logger log;
	private HashFunction hashFunction;
	
	public HashProvider() {
		log = LoggerFactory.getLogger(this.getClass());
		log.info("Constructor call");
		hashFunction = Hashing.sha256();
	}
	
	public String hash(String original) {
		return hashFunction.hashString(original, StandardCharsets.UTF_8).toString();
	}
	
	public boolean check(String original, String hash) {
		String s = this.hash(original);
		return s.equals(hash);
	}
}
