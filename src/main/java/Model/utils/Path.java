package Model.utils;

/**
 * A interface Path fornece caminhos para arquivos no sistema, especificamente relacionados ao armazenamento de dados.
 * Este pacote oferece acesso aos caminhos dos arquivos de banco de dados para diferentes funcionalidades do sistema.
 */
public interface Path {

    /**
     * Caminho para o arquivo de banco de dados do estoque.
     */
    String pathEstoque = "src/main/java/Model/BD/bdEstoque.txt";

    /**
     * Caminho para o arquivo de banco de dados de transações.
     */
    String pathTransacao = "src/main/java/Model/BD/bdTransações.txt";

    /**
     * Caminho para o arquivo de banco de dados de usuários.
     */
    String pathUsers = "src/main/java/Model/BD/bdUsers.txt";

    /**
     * Caminho para o arquivo de banco de dados de movimentações.
     */
    String pathMovimentacao = "src/main/java/Model/BD/bdMovimentações.txt";
}
