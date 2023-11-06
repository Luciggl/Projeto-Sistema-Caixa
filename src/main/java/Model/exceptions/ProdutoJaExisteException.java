package Model.exceptions;

public class ProdutoJaExisteException extends Exception{
    public ProdutoJaExisteException(String msg){
        super(msg);
    }
}
