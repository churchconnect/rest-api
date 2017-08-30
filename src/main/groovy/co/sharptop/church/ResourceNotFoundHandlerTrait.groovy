package co.sharptop.church

import org.springframework.http.HttpStatus

trait ResourceNotFoundHandlerTrait {

    void notFound() {
        render status: HttpStatus.NOT_FOUND
    }

    def resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        notFound()
    }

}