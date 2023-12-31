package Model.services;

import Model.repositories.UserLogin;

import java.util.Objects;

public class LoginServices {

    public int logar(String login, String senha){
        if (Objects.equals(login, UserLogin.gerenteLogin)){
            if (Objects.equals(senha, UserLogin.gerenteSenha)) return 1;
        } else if (Objects.equals(login, UserLogin.caixaLogin)) {
            if (Objects.equals(senha, UserLogin.caixaSenha)) return 2;
        }
        return 3;
    }
}
