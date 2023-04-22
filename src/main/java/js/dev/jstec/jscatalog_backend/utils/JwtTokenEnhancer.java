package js.dev.jstec.jscatalog_backend.utils;

import js.dev.jstec.jscatalog_backend.domain.entities.User;
import js.dev.jstec.jscatalog_backend.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Autowired
    private UserRepository repository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        User user = repository.findByEmail(oAuth2Authentication.getName());
        Map<String, Object> tokenAdditionalInformation = new HashMap<>();
        tokenAdditionalInformation.put("userFirstName", user.getFirstName());
        tokenAdditionalInformation.put("userLastName", user.getLastName());
        tokenAdditionalInformation.put("userId", user.getId());

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;
        token.setAdditionalInformation(tokenAdditionalInformation);
        return oAuth2AccessToken;
    }
}
