package ir.ac.ut.iemdb.tools.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ir.ac.ut.iemdb.tools.exceptions.ForbiddenException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWTUtils {
    private static String SECRET_KEY = "iemdb";
    private static long EXPIRE_PERIOD = 24*60*60*1000;

    private static Date expirationDate(int hours) {
        long curTime = System.currentTimeMillis();
        return new Date(curTime + (hours*60*60*1000));
    }

    public static String createJWT(String userMail, int hours) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder();
        builder.setIssuer(userMail);
        builder.setIssuedAt(new Date(System.currentTimeMillis()));
        builder.setExpiration(expirationDate(hours));
        builder.signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public static String verifyJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        if(claims.getIssuedAt() == null ||
                claims.getExpiration()== null ||
                claims.getIssuer() == null)
            throw new ForbiddenException("Authorization invalid");
        if (claims.getExpiration().getTime() < System.currentTimeMillis())
            return null;
        return claims.getIssuer();
    }
}
