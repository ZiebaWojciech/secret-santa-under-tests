package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class WebExceptionHandler {

    @ExceptionHandler(GifterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleGifterNotFound() {}
}
