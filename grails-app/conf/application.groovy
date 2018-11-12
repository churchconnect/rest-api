// Added by the Spring Security Core plugin:
grails {
    plugin {
        springsecurity {

            userLookup {
                userDomainClassName = 'co.sharptop.church.Person'
                authorityJoinClassName = 'co.sharptop.church.PersonRole'
            }
            authority.className = 'co.sharptop.church.Role'

            controllerAnnotations.staticRules = [
                [pattern: '/', access: ['permitAll']],
                [pattern: '/error', access: ['permitAll']],
                [pattern: '/index', access: ['permitAll']],
                [pattern: '/index.gsp', access: ['permitAll']],
                [pattern: '/shutdown', access: ['permitAll']],
                [pattern: '/assets/**', access: ['permitAll']],
                [pattern: '/**/js/**', access: ['permitAll']],
                [pattern: '/**/css/**', access: ['permitAll']],
                [pattern: '/**/images/**', access: ['permitAll']],
                [pattern: '/**/favicon.ico', access: ['permitAll']]
            ]

            filterChain.chainMap = [
                [pattern: '/assets/**', filters: 'none'],
                [pattern: '/**/js/**', filters: 'none'],
                [pattern: '/**/css/**', filters: 'none'],
                [pattern: '/**/images/**', filters: 'none'],
                [pattern: '/**/favicon.ico', filters: 'none'],
                [pattern: '/**', filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
            ]

            rest {
                token {
                    storage {
                        useGorm = true
                        gorm.tokenDomainClassName = 'co.sharptop.church.AuthenticationToken'
                    }
                    validation {
                        useBearerToken = false
                        headerName = 'X-Auth-Token'
                        enableAnonymousAccess = true
                    }
                }
            }
        }
    }
}
