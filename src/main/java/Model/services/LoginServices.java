package Model.services;

import Model.entities.User;
import Model.enums.FunctionUser;
import Model.exceptions.UserExceptions;
import Model.repositories.Path;

import java.io.*;
import java.util.ArrayList;

public class LoginServices {

    private ArrayList<User> users = new ArrayList<>();

    public boolean ListaDeUserEstaVazia() {
        return users.isEmpty();
    }


    public void adicionarUsuario(User user) throws UserExceptions {
        if (!usuarioExiste(user)) {
            users.add(user);
            SalvarUsuarios(Path.pathUsers);
        } else throw new UserExceptions("Usuario ja cadastrado");

    }

    public void removerUsuario(User user) {
        if (usuarioExiste(user)) {
            users.remove(user);
            SalvarUsuarios(Path.pathUsers);
        }
    }

    public User findByLogin(String login) throws  UserExceptions{
        for (User u : users){
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        throw new UserExceptions("Usuario Não Existe No Sistema");
    }

    public boolean loginNaoExiste(String login){
        for(User u : users){
            if(u.getLogin().equals(login)){
                return false;
            }
        }
        return true;
    }

    public boolean usuarioExiste(User user) {
        return users.contains(user);
    }

    public String retornarNomeFuncionario(String login) throws UserExceptions {
        return findByLogin(login).getNome();

    }
    public int logar(String login, String senha) throws UserExceptions {
        User user = findByLogin(login);
        if (user != null) {
            if (user.getLogin().equals(login) && user.getSenha().equals(senha)) {
                if (user.getFunction().equals(FunctionUser.GERENTE)) return 1;
                else if (user.getFunction().equals(FunctionUser.CAIXA)) return 2;
                else if (user.getFunction().equals(FunctionUser.ESTOQUISTA)) return 3;
            }
        }
        throw new UserExceptions("Login ou senha inválidos");
    }

    public void SalvarUsuarios(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (User u : users) {
                bw.write(u.getNome() + "," + u.getLogin() + "," + u.getSenha() + "," + u.getFunction());
                bw.newLine();
                System.out.print("Salvo com sucesso");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CarregarUsuarios(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String nome = parts[0];
                    String login = parts[1];
                    String senha = parts[2];
                    FunctionUser functionUser = FunctionUser.valueOf(parts[3]);

                    try {
                        User user = new User(nome, login, senha, functionUser);
                        users.add(user);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            } System.out.println("Usuarios carregados com sucesso");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
