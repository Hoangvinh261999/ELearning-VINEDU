package vinhit.authservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vinhit.authservice.entity.InvalidToken;
import vinhit.authservice.entity.User;
import vinhit.authservice.exception.AppException;
import vinhit.authservice.exception.ErrorCode;
import vinhit.authservice.repository.InvalidTokenRepository;
import vinhit.authservice.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class JwtService {
    private static final String key = "haychongiadungdinhecacbantretuoi";  // 32 ký tự (256 bit)
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS =3600;
    @Autowired
    InvalidTokenRepository invalidTokenRepository;
    @Autowired
    UserRepository userRepository;
    public String generateToken(User user){
        List<String> scopes = user.getAuthorities().stream()
                .map(Object::toString)
                .toList();

          return   Jwts.builder()
                    .setSubject(user.getUsername())
                    .setId(UUID.randomUUID().toString())
                    .claim("scope",scopes)
                     .claim("usId",user.getId())
                    .setIssuer("NguyenVinhDev")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                    .signWith(getKeys(key),SignatureAlgorithm.HS256)
                    .compact();
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = verifyToken(token);
            log.info("Token is valid: {}", signedJWT.getJWTClaimsSet().getJWTID());
            return true;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
    public String extractUsername(String token){
            return claims(token).getSubject();
    }
    public Collection<?> extractAuthorities(String token){
        return claims(token).get("scope", Collection.class);
    }

    public boolean istokenExperied(String token){
        Date expiration=claims(token).getExpiration();
        return expiration.before(new Date());
    }
    public Claims claims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKeys(key))
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public Key getKeys(String keys) throws SignatureException {
        byte[] keyBytes = keys.getBytes(StandardCharsets.UTF_8);  // Chuyển chuỗi thành mảng byte
        return Keys.hmacShaKeyFor(keyBytes);  // Trả về khóa dùng cho HMAC-SHA256
    }

    public String refeshJwtToken(String signedToken) throws ParseException, JOSEException {
        var signedJWT = verifyToken(signedToken);
        var jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidToken invalidatedToken = InvalidToken.builder().id(jwtId).expiryTime(expiryTime).build();
        invalidTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        return generateToken(user);
    }

    public boolean logout(String signedToken) throws AppException {
        try {
            var signedJWT = verifyToken(signedToken);

            var jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidToken invalidatedToken = InvalidToken.builder()
                    .id(jwtId)
                    .expiryTime(expiryTime)
                    .build();
            invalidTokenRepository.save(invalidatedToken);
            return true;
        } catch (AppException | JOSEException | ParseException e) {
//            log.info("loi",e);
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException,AppException {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

        JWSVerifier verifier = new MACVerifier(keyBytes);

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.INVALID_TOKEN);
        var token1=signedJWT.getJWTClaimsSet().getJWTID();
        log.info("token123: {} ", token1);

        if (invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.INVALID_TOKEN);
        return signedJWT;
    }


}
