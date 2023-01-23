package pe.parnertdigital.jpatest4.exceptions;

public class DineroInsuficienteException extends RuntimeException{
    public DineroInsuficienteException(String message) {
        super(message);
    }
}
