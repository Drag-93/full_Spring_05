package org.spring.springprojectT.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

//스프링부트 프로젝트에서 예외가 발생 했을 때 가로채서 클라이언트에게 응답
@RestControllerAdvice
public class GlobalExceptionHandler {
         //클라리언트에서 응답해주는타입               //오류메세지         상태코트
    private ResponseEntity<String> buildResponse(String message, HttpStatus status){
        String html = "<script>"+ "alert('"+message+"');"+"history.go(-1);"+"</script>";
        return ResponseEntity.status(status).body(html);
    }


    // 잘못된 요청(파라미터 오류)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(IllegalArgumentException e){
        return buildResponse(e.getMessage(),HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleConflict(IllegalStateException e){
        return buildResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<?> handleNotFound(NoSuchElementException e){
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNull(NullPointerException e){
        return buildResponse("서버 처리 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleAll(Exception e){
//        return buildResponse("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }



















}
