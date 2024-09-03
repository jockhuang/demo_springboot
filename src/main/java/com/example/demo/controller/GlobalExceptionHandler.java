package com.example.demo.controller;

import com.example.demo.model.MyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice

public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * handler all unknow exceptions
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    MyResponse handleException(Exception e) {
        logger.error(e.getMessage(), e);

        return MyResponse.failed("operation failed:" + e.getMessage());


    }


    /**
     * handler MethodArgumentNotValidException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    MyResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        return MyResponse.failed("ValidException:" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

    }

    /**
     * handler MethodArgumentTypeMismatchException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    MyResponse handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        logger.error(e.getMessage(), e);
        return MyResponse.failed("MethodArgumentTypeMismatchException:" + e.getMessage());

    }
}
