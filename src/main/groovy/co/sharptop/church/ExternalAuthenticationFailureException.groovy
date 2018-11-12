package co.sharptop.church

class ExternalAuthenticationFailureException extends Exception {

    String service

    ExternalAuthenticationFailureException(String service) {
        this.service = service
    }

}
