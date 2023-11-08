package Model.controller;

import Model.entities.Products;
import Model.enums.Categoria;
import Model.exceptions.ProdutoNaoExisteException;
import Model.services.Estoque;

import javax.swing.*;

public class Program {

    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        try {
            int option;
            do {
                String input = JOptionPane.showInputDialog("Escolha uma opção:\n1 - Adicionar Produto\n2 - Remover Produto\n3 - Verificar Estoque\n4 - Adicionar Quantidade\n5 - Remover Quantidade\n6 - Gerenciar Mesas\n7 - Sair");

                if (input == null || input.isEmpty()) {
                    option = 7;
                } else {
                    option = Integer.parseInt(input);
                }

                switch (option) {
                    case 1:
                        // Adicionar produto ao estoque
                        String name = JOptionPane.showInputDialog("Nome do produto:");
                        double id = Double.parseDouble(JOptionPane.showInputDialog("ID do produto:"));
                        Categoria categoria = (Categoria) JOptionPane.showInputDialog(null, "Selecione a Categoria do Produto:", "Categoria", JOptionPane.QUESTION_MESSAGE, null, Categoria.values(), Categoria.Drinks);
                        double value = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto:"));

                        Products newProduct = new Products(name, id, categoria, value);
                        estoque.addEstoque(newProduct, 1);
                        break;

                    case 2:
                        // Remover produto do estoque
                        int idToRemove = Integer.parseInt(JOptionPane.showInputDialog("ID do produto a ser removido:"));
                        Products productToRemove = estoque.getProductById(idToRemove);

                        if (productToRemove != null) {
                            estoque.removeEstoque(productToRemove, idToRemove);
                            JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Produto não encontrado com o ID fornecido.");
                        }
                        break;

                    case 3:

                        JOptionPane.showMessageDialog(null, "Estoque Atual:\n" + estoque);
                        break;

                    case 4:

                        int idToAddQuant = Integer.parseInt(JOptionPane.showInputDialog("ID do produto para adicionar quantidade:"));
                        int addQuantity = Integer.parseInt(JOptionPane.showInputDialog("Quantidade a ser adicionada:"));

                        try {
                            Products productToAddQuant = estoque.getProductById(idToAddQuant);
                            estoque.AddQuant(productToAddQuant, addQuantity);
                            JOptionPane.showMessageDialog(null, "Quantidade adicionada ao estoque.");
                        } catch (ProdutoNaoExisteException e) {
                            JOptionPane.showMessageDialog(null, "Erro ao adicionar quantidade: " + e.getMessage());
                        }
                        break;

                    case 5:
                        // Remover quantidade do estoque
                        int idToRemoveQuant = Integer.parseInt(JOptionPane.showInputDialog("ID do produto para remover quantidade:"));
                        int removeQuantity = Integer.parseInt(JOptionPane.showInputDialog("Quantidade a ser removida:"));

                        try {
                            Products productToRemoveQuant = estoque.getProductById(idToRemoveQuant);
                            estoque.removeQuant(productToRemoveQuant, removeQuantity);
                            JOptionPane.showMessageDialog(null, "Quantidade removida do estoque.");
                        } catch (ProdutoNaoExisteException e) {
                            JOptionPane.showMessageDialog(null, "Erro ao remover quantidade: " + e.getMessage());
                        }
                        break;

                    case 6:
                        System.out.println("Em manutenção...");
                        break;

                    case 7:
                        JOptionPane.showMessageDialog(null, "Saindo do programa.");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                }
            } while (option != 7);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
        }
    }
}
