package co.sharptop.church

import grails.plugin.springsecurity.rest.token.generation.SecureRandomTokenGenerator
import grails.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

@Transactional
class AuthenticationTokenService {

    UserDetailsService userDetailsService

    AuthenticationToken createToken(String username) {
        createToken(Person.findOrCreate(username))
    }

    AuthenticationToken createToken(Person person) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(person.username)

        AuthenticationToken token = new AuthenticationToken(
            username: person.username,
            tokenValue: generateTokenValue(userDetails)
        ).save(failOnError: true)

        return token
    }

    private String generateTokenValue(UserDetails userDetails) {
        new SecureRandomTokenGenerator().generateAccessToken(userDetails).accessToken
    }

}
