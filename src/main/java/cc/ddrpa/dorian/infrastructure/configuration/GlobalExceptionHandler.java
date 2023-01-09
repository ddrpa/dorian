package cc.ddrpa.dorian.infrastructure.configuration;

import cc.ddrpa.dorian.infrastructure.exception.OperationNotAllowedException;
import cc.ddrpa.dorian.infrastructure.exception.ResourceNotFoundException;
import io.micrometer.tracing.Tracer;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final Tracer tracer;

    public GlobalExceptionHandler(Tracer tracer) {
        this.tracer = tracer;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OperationNotAllowedException.class)
    public ProblemDetail handleOperationNotAllowedException(OperationNotAllowedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("Operation not allowed");
        problemDetail.setProperty("tracer", tracer.currentTraceContext().context().traceId());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setProperty("tracer", tracer.currentTraceContext().context().traceId());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Method Argument Not Valid");
        problemDetail.setTitle("Validation Error");
        var explanations = ex.getBindingResult().getAllErrors().stream()
                .map((error) -> {
                    var explanation = new HashMap<String, String>();
                    explanation.put("field", ((FieldError) error).getField());
                    explanation.put("message", error.getDefaultMessage());
                    return explanation;
                })
                .toList();
        problemDetail.setProperty("explanations", explanations);
        problemDetail.setProperty("tracer", tracer.currentTraceContext().context().traceId());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Method Argument Not Valid");
        problemDetail.setTitle("Validation Error");
        var explanations = ex.getConstraintViolations().stream()
                .map((violation) -> {
                    var explanation = new HashMap<String, String>();
                    explanation.put("field", violation.getPropertyPath().toString());
                    explanation.put("message", violation.getMessage());
                    return explanation;
                })
                .toList();
        problemDetail.setProperty("explanations", explanations);
        problemDetail.setProperty("tracer", tracer.currentTraceContext().context().traceId());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException ex) {
        logger.error("Unexpected exception: {}", ex.getCause().getLocalizedMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("tracer", tracer.currentTraceContext().context().traceId());
        return problemDetail;
    }
}