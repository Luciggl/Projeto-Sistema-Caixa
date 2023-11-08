package Model.exceptions;

public class ProdutoJaExisteException extends RuntimeException{
    public ProdutoJaExisteException(String msg){
        super(msg);
    }
}
