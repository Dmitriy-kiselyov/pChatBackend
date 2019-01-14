package ru.pussy_penetrator.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;

import com.sun.istack.internal.Nullable;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ru.pussy_penetrator.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthUtils {
    static private final String KEY = "abc123";

    static public void encrypt(User user) {
        user.setPassword(BCrypt.with(LongPasswordStrategies.truncate()).hashToString(12, user.getPassword().toCharArray()));
    }

    static public String generateToken(User user) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("login", user.getLogin());
        tokenData.put("password", user.getPassword());
        tokenData.put("token_create_time", new Date().getTime());

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(tokenData);
        String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, KEY).compact();

        return token;
    }

    @Nullable
    static public String getTokenFromBD(User user) {
        return null; // TODO: достать токен из БД
    }
}
