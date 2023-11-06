package Model.exceptions;

public class ProdutoNãoExisteException extends Exception{
    public ProdutoNãoExisteException(String msg){
        super(msg);
    }
}
