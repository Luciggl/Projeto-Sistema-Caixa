package Model.services;

import Model.entities.User;
import Model.enums.FunctionUser;
import Model.repositories.Path;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class LoginServices {

    private ArrayList<User> users = new ArrayList<>();


    public boolean ListaDeUserEstaVazia(){
        return users.isEmpty();
    }


    public void adicionarUsuario(User user) {
        if (!usuarioExiste(user)) {
            users.add(user);
            SalvarUsuarios(Path.pathUsers);
        } else System.out.println("Usuario ja cadastrado");

    }

    public void removerUsuario(User user) {
        if (usuarioExiste(user)){
            users.remove(user);
            SalvarUsuarios(Path.pathUsers);
        }
    }

    public User findByLogin(String login) {
        for (User u : users)
            if (u.getLogin().equals(login)) {
                return u;
            } else JOptionPane.showMessageDialog(null, "Usuario não encontrado");
        return null;
    }

    public boolean usuarioExiste(User user) {
        return users.contains(user);
    }

    public boolean ExisteGerente() {
        for (User u : users) {
            if (u.getFunction().equals(FunctionUser.GERENTE)) {
                return true;
            }
        }
        return false;
    }


    public int logar(String login, String senha) {
            User user = findByLogin(login);
            if (user != null) {
                if (user.getLogin().equals(login) && user.getSenha().equals(senha)) {
                    if (user.getFunction().equals(FunctionUser.GERENTE)) return 1;
                    else if (user.getFunction().equals(FunctionUser.CAIXA)) return 2;
                    else if (user.getFunction().equals(FunctionUser.ESTOQUISTA)) return 3;
                }
            } else System.out.println("login não efetuado\n Verifique se Login ou senha");

        return 0;
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
                        System.out.println("Usuarios carregados com sucesso");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
