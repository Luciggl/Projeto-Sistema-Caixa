package Model.repositories;

import Model.entities.User;
import Model.exceptions.UserExceptions;

public interface LoginRepository {
    void adicionarUsuario(User user) throws UserExceptions;

    void removerUsuario(User user) throws UserExceptions;

    User findByLogin(String login) throws UserExceptions;

    boolean loginNaoExiste(String login);

    boolean usuarioExiste(User user);

    String retornarNomeFuncionario(String login) throws UserExceptions;

    int logar(String login, String senha) throws UserExceptions;

    void SalvarUsuarios(String path);

    void CarregarUsuarios(String path);
}
