package Model.controller;

import Model.entities.Products;
import Model.enums.Categoria;
import Model.enums.StatusMesa;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;
import Model.services.Estoque;
import Model.services.Mesas;

import javax.swing.*;

public class Program {

    public static void main(String[] args) {
        Estoque estoque = new Estoque();
        Mesas mesas = new Mesas(1, StatusMesa.Livre, estoque);

        estoque.carregarEstoque("src/main/java/Model/BDEstoque/bdEstoque.txt");

        try {
            int option;
            do {
                String input = JOptionPane.showInputDialog("Escolha uma opção:\n1 - Adicionar Produto\n2 - Remover Produto\n3 - Verificar Estoque\n4 - Adicionar Quantidade\n5 - Remover Quantidade\n6 - Gerenciar Mesas\n7 - Salvar \n8 - Sair");

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
                        gerenciarMesas(mesas, estoque);
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
            String name = JOptionPane.showInputDialog("Nome do produto:");
            double id = Double.parseDouble(JOptionPane.showInputDialog("ID do produto:"));
            Categoria categoria = (Categoria) JOptionPane.showInputDialog(null, "Selecione a Categoria do Produto:", "Categoria", JOptionPane.QUESTION_MESSAGE, null, Categoria.values(), Categoria.Drinks);
            double value = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto:"));
            int quant = Integer.parseInt((JOptionPane.showInputDialog("Digite a Quantidade em Estoque")));

            Products newProduct = new Products(name, id, categoria, value, quant);
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
        JOptionPane.showMessageDialog(null, "Valor Total do estoque: " + estoque.calcularValorTotalEstoque() + "\n  Estoque Atual:\n" + estoque);
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

    private static void gerenciarMesas(Mesas mesas, Estoque estoque) {
        try {
            int option;
            do {
                String input = JOptionPane.showInputDialog("Escolha uma opção:\n1 - Adicionar Produto à Mesa\n2 - Remover Produto da Mesa\n3 - Calcular Total da Mesa\n4 - Voltar");

                if (input == null || input.isEmpty()) {
                    option = 4;
                } else {
                    option = Integer.parseInt(input);
                }

                switch (option) {
                    case 1:
                        adicionarProdutoAMesa(mesas, estoque);
                        break;

                    case 2:
                        removerProdutoDaMesa(mesas, estoque);
                        break;

                    case 3:
                        calcularTotalMesa(mesas);
                        break;

                    case 4:
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                }
            } while (option != 4);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
        }
    }

    private static void adicionarProdutoAMesa(Mesas mesas, Estoque estoque) {
        try {
            int mesaId = Integer.parseInt(JOptionPane.showInputDialog("Número da mesa para adicionar produto:"));
            int quant = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Produtos a Adicionar:"));
            Products productToAdd = escolherProduto(estoque);

            if (productToAdd != null) {
                mesas.addProdutoMesa(productToAdd, quant, mesaId);
                estoque.removeQuant(productToAdd, quant);
                JOptionPane.showMessageDialog(null, "Produto adicionado à mesa com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar produto à mesa: Insira um número de mesa válido.");
        } catch (ProdutoNaoExisteException e) {
            JOptionPane.showMessageDialog(null, "Quantidade Insuficiente:");
        }
    }

    private static void removerProdutoDaMesa(Mesas mesas, Estoque estoque) {
        try {
            int mesaId = Integer.parseInt(JOptionPane.showInputDialog("Número da mesa para remover produto:"));
            int quant = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Produtos a Remover:"));
            Products productToRemove = escolherProduto(estoque);

            if (productToRemove != null) {
                mesas.removerProdutoMesa(productToRemove,quant, mesaId);
                JOptionPane.showMessageDialog(null, "Produto removido da mesa com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover produto da mesa: Insira um número de mesa válido.");
        }
    }

    private static void calcularTotalMesa(Mesas mesas) {
        try {
            int mesaId = Integer.parseInt(JOptionPane.showInputDialog("Número da mesa para calcular total:"));
            double total = mesas.calcularTotalProdutosConsumidos(mesaId);
            JOptionPane.showMessageDialog(null, "Total da mesa " + mesaId + " R$: " + total);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular total da mesa: Insira um número de mesa válido.");
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
}
