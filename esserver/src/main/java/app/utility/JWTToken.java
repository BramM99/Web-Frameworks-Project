package app.utility;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWTToken {
    
    private static final String JWT_USERNAME_CLAIM = "sub";
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ADMIN_CLAIM = "admin";

    private String userName = "null";
    private long userId = 0;
    private boolean admin = false;

    public JWTToken(String username, long userId, boolean admin) {
        this.userName = username;
        this.userId = userId;
        this.admin = admin;
    }

    public String encode(String issuer, String passPhrase, long expiration) {
        Key key = getKey(passPhrase);

        return Jwts.builder()
                .claim(JWT_USERNAME_CLAIM, this.userName)
                .claim(JWT_USERID_CLAIM, this.userId)
                .claim(JWT_ADMIN_CLAIM, this.admin)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private static Key getKey(String passPhrase) {
        byte[] hmacKey = passPhrase.getBytes(StandardCharsets.UTF_8);
        Key key = new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
        return key;
    }

    public static JWTToken decode(String token, String passPhrase) {
        try {
            //validate token
            Key key = getKey(passPhrase);
            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            Claims claims = jws.getBody();

            JWTToken jwtToken = new JWTToken(
                    claims.get(JWT_USERNAME_CLAIM).toString(),
                    Long.parseLong(claims.get(JWT_USERID_CLAIM).toString()),
                    (boolean) claims.get(JWT_ADMIN_CLAIM)
            );

            return jwtToken;

        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "JWTToken{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                ", admin=" + admin +
                '}';
    }
}
