package cc.ddrpa.dorian.infrastructure.exception;

import org.springframework.web.client.RestClientException;

public class OperationNotAllowedException extends RestClientException {
    public OperationNotAllowedException(String msg) {
        super(msg);
    }
}