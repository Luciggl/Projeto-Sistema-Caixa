package Model.repositories;

import Model.entities.Movimentacao;

public interface MovimentacaoRepository {
    void addMovimentacao(Movimentacao movimentacao);
    void SalvarMovimentacao(String filePath);
    void carregarMovimentacao(String filepath);
    String toString();
}
