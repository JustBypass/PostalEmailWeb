package com.mail.logio.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.global.dto.LoginForm;
import com.mail.global.dto.RegistrationFormDTO;
import com.mail.global.dto.TokenPair;
import com.mail.global.clients.online.ClientDTO;
import com.mail.logio.utils.Property;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class Requests {
    @Autowired
    private Property property;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    public void tryLoginToMail(ClientDTO clientDTO) throws Exception {
        HttpEntity<ClientDTO> clientDTOHttpEntity =
                new HttpEntity<>(clientDTO);
       var response =  restTemplate.postForEntity(property.getMAIL_LOGIN_URL(),
               clientDTOHttpEntity,String.class);
          if(response.getStatusCode() != HttpStatus.OK){
            throw new Exception("BadLoginMail");
        }
    }
    public void signUpInDatabase(RegistrationFormDTO registrationFormDTO,
                                        ClientDTO clientDTO){

    }
    public TokenPair getTokens(ClientDTO clientDTO) throws URISyntaxException, IOException, InterruptedException {
        try {
            var now = Date.from(Instant.now());

            String accessToken = Jwts.builder()
                    .claim("client", clientDTO)
                    .setIssuedAt(now)
                    .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
                    .signWith(SignatureAlgorithm.RS256, TextCodec.BASE64.decode(property.getSECRET()))
                    .compact();
            String refreshToken = Jwts.builder()
                    .claim("client", clientDTO)
                    .setIssuedAt(now)
                    .setExpiration(Date.from(Instant.now().plus(72, ChronoUnit.HOURS)))
                    .signWith(SignatureAlgorithm.RS256, TextCodec.BASE64.decode(property.getSECRET()))
                    .compact();

            return new TokenPair(accessToken,refreshToken);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ClientDTO getClientFromJwt(String accessToken ){
     /*   Cookie[] coolies = request.getCookies();
        var validCookie = Arrays.stream(coolies)
                .filter(cookie->cookie.getName()
                        .equals("Postal-Cookie")
                ).findFirst().get();
        String accessToken = validCookie.getValue();*/
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(accessToken),
                SignatureAlgorithm.HS256.getJcaName());
        Jws<Claims> jwt = Jwts.parser().setSigningKey(hmacKey)
                .parseClaimsJws(accessToken);

       // response.addCookie(validCookie);
        return (ClientDTO) jwt.getBody().get("clientDTO");
    }

    public void refreshToken(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        Cookie[] coolies = request.getCookies();
        var validCookie = Arrays.stream(coolies)
                .filter(cookie->cookie.getName()
                        .equals("Postal-Cookie")
                ).findFirst().get();
        String refreshToken = validCookie.getValue();
        TokenPair tokens = null;

        Cookie cookie = new Cookie("Postal-Cookie",tokens.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        objectMapper.writeValue(response.getOutputStream(), Map.of("accessToken",tokens.getAccessToken()));
    }
    public ClientDTO login(LoginForm loginForm) throws URISyntaxException {
        return restTemplate.postForObject(new URI(property.getDatabaseLogin()),loginForm,ClientDTO.class);
    }
}
