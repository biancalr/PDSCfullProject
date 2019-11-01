package util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.validation.constraints.NotNull;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwTokenHelper {

	private static JwTokenHelper jwTokenHelper = null;
	public static final long EXPIRATION_LIMIT_MINIMUM = 30;
	public static final long EXPIRATION_LIMIT_MAXIMUM = 87600;
	private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private JwTokenHelper() {

	}

	public static JwTokenHelper getInstance() {
		if (jwTokenHelper == null)
			jwTokenHelper = new JwTokenHelper();
		return jwTokenHelper;
	}

	public String generateToken(String username, String password, long limit) {
		return Jwts
				.builder()
				.setSubject(username)
				.setSubject(password)
				.setExpiration(getExpirationDate(limit))
				.signWith(key)
				.compact();
	}

	public void claimKey(String token) throws ExpiredJwtException, MalformedJwtException {
		Jwts
			.parser()
			.setSigningKey(key)
			.parseClaimsJws(token);
	}

	@NotNull
	private Date getExpirationDate(long limit) {
		long currentTimeMillis = System.currentTimeMillis();
		long expMilliSeconds = TimeUnit.MINUTES.toMillis(limit);
		return new Date(currentTimeMillis + expMilliSeconds);
	}

}
