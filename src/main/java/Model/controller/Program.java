package Model.controller;

import Model.entities.Products;
import Model.enums.Category;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;
import Model.services.Estoque;

import javax.swing.*;
import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {
        Estoque estoque = new Estoque();


        estoque.carregarEstoque("src/main/java/Model/BDEstoque/bdEstoque.txt");

        try {
            int option;
            do {
                String input = JOptionPane.showInputDialog("Escolha uma opção:\n1 - Adicionar Produto\n2 - Remover Produto\n3 - Verificar Estoque\n4 - Adicionar Quantidade\n5 - Remover Quantidade\n6 - Pesquisar Produto Por Id\n7 - Salvar \n8 - Sair");

                if (input == null || input.isEmpty()) {
                    option = 7;
                } else {
                    option = Integer.parseInt(input);
                }

                switch (option) {
                    case 1:
                        adicionarProduto(estoque);
                        break;

                    case 2:
                        removerProduto(estoque);
                        break;

                    case 3:
                        verificarEstoque(estoque);
                        break;

                    case 4:
                        adicionarQuantidade(estoque);
                        break;

                    case 5:
                        removerQuantidade(estoque);
                        break;

                    case 6:
                        pesquisarPorIdOuCategoria(estoque);
                        break;

                    case 7:
                        estoque.salvarEstoque("src/main/java/Model/BDEstoque/bdEstoque.txt");
                        break;

                    case 8:
                        JOptionPane.showMessageDialog(null, "Saindo do programa.");
                        estoque.salvarEstoque("src/main/java/Model/BDEstoque/bdEstoque.txt");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                }
            } while (option != 8);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
        }
    }

    private static void adicionarProduto(Estoque estoque) {
        try {
            double id = Double.parseDouble(JOptionPane.showInputDialog("ID do produto:"));
            String name = JOptionPane.showInputDialog("Nome do produto:");
            String manufacturer = JOptionPane.showInputDialog("Fabricante");
            Category category = (Category) JOptionPane.showInputDialog(null, "Selecione a Categoria do Produto:", "Categoria", JOptionPane.QUESTION_MESSAGE, null, Category.values(), Category.Drinks);
            double value = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto:"));
            int quant = Integer.parseInt((JOptionPane.showInputDialog("Digite a Quantidade em Estoque")));

            Products newProduct = new Products(id, name, manufacturer, category, value, quant);
            estoque.addEstoque(newProduct);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar produto: Insira valores válidos.");
        } catch (ProdutoJaExisteException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar produto: " + e.getMessage());
        }
    }

    private static void removerProduto(Estoque estoque) {
        try {
            int idToRemove = Integer.parseInt(JOptionPane.showInputDialog("ID do produto a ser removido:"));
            Products productToRemove = estoque.getProductById(idToRemove);

            if (productToRemove != null) {
                estoque.removeEstoque(productToRemove, idToRemove);
                JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado com o ID fornecido.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover produto: Insira um ID válido.");
        } catch (ProdutoNaoExisteException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover produto: " + e.getMessage());
        }
    }

    private static void verificarEstoque(Estoque estoque) {
        JOptionPane.showMessageDialog(null, "Estoque Atual:\n" + estoque);
    }

    private static void adicionarQuantidade(Estoque estoque) {
        try {
            int idToAddQuant = Integer.parseInt(JOptionPane.showInputDialog("ID do produto para adicionar quantidade:"));
            int addQuantity = Integer.parseInt(JOptionPane.showInputDialog("Quantidade a ser adicionada:"));

            Products productToAddQuant = estoque.getProductById(idToAddQuant);
            estoque.AddQuant(productToAddQuant, addQuantity);
            JOptionPane.showMessageDialog(null, "Quantidade adicionada ao estoque.");
        } catch (NumberFormatException | ProdutoNaoExisteException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar quantidade: " + e.getMessage());
        }
    }

    private static void removerQuantidade(Estoque estoque) {
        try {
            int idToRemoveQuant = Integer.parseInt(JOptionPane.showInputDialog("ID do produto para remover quantidade:"));
            int removeQuantity = Integer.parseInt(JOptionPane.showInputDialog("Quantidade a ser removida:"));

            Products productToRemoveQuant = estoque.getProductById(idToRemoveQuant);
            estoque.removeQuant(productToRemoveQuant, removeQuantity);
            JOptionPane.showMessageDialog(null, "Quantidade removida do estoque.");
        } catch (NumberFormatException | ProdutoNaoExisteException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover quantidade: " + e.getMessage());
        }
    }

    private static Products escolherProduto(Estoque estoque) {
        try {
            int productId = Integer.parseInt(JOptionPane.showInputDialog("ID do produto a ser escolhido:"));
            return estoque.getProductById(productId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao escolher produto: Insira um ID válido.");
            return null;
        }
    }
    private static void pesquisarPorIdOuCategoria(Estoque estoque) {
        try {
            String input = JOptionPane.showInputDialog("Escolha uma opção:\n1 - Pesquisar por ID\n2 - Pesquisar por Categoria");
            int option = Integer.parseInt(input);

            switch (option) {
                case 1:
                    pesquisarPorId(estoque);
                    break;

                case 2:
                    pesquisarPorCategoria(estoque);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao escolher opção: Insira um número válido.");
        }
    }

    private static void pesquisarPorId(Estoque estoque) {
        try {
            Products produtoPesquisado = escolherProduto(estoque);
            if (produtoPesquisado != null) {
                JOptionPane.showMessageDialog(null, "Produto encontrado:\n" + produtoPesquisado);
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar por ID: Insira um ID válido.");
        }
    }

    private static void pesquisarPorCategoria(Estoque estoque) {
        try {
            Category category = (Category) JOptionPane.showInputDialog(null, "Selecione a Categoria:", "Categoria", JOptionPane.QUESTION_MESSAGE, null, Category.values(), Category.Drinks);
            ArrayList<Products> produtosPorCategoria = estoque.getProductsByCategory(category);

            if (!produtosPorCategoria.isEmpty()) {
                StringBuilder mensagem = new StringBuilder("Produtos na categoria " + category + ":\n");
                for (Products produto : produtosPorCategoria) {
                    mensagem.append(produto).append("\n");
                }
                JOptionPane.showMessageDialog(null, mensagem.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado na categoria " + category + ".");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar por categoria: " + e.getMessage());
        }
    }
}
