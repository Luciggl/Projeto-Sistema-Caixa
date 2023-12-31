package Model.controller;

import Model.entities.Products;
import Model.enums.Category;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;
import Model.repositories.Path;
import Model.services.Caixa;
import Model.services.Estoque;
import Model.services.LoginServices;
import Model.services.PaymentsServices;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;

import static Model.services.PaymentsServices.ValidadorCartaoCredito.validarNumeroCartao;

public class Program {

    public static void main(String[] args) {
        Estoque estoque = new Estoque();
        Caixa caixa = new Caixa(estoque);
        LoginServices loginServices = new LoginServices();


        estoque.carregarEstoque(Path.pathEstoque);
        estoque.carregarTransacao(Path.pathTransacao);

        try {
            String login = JOptionPane.showInputDialog("Bem vindo!! \nDigite Seu Login: ");
            String senha = JOptionPane.showInputDialog("Digite Sua Senha: ");


            if (login == null || login.isEmpty() || senha == null || senha.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Saindo do programa.");
                System.exit(0);
            }

            int papel = loginServices.logar(login, senha);

            switch (papel) {
                case 1:
                    menuGerente(estoque, caixa);
                    break;

                case 2:
                    menuCaixa(caixa);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Saindo do programa.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
        }
    }

    private static void menuGerente(Estoque estoque, Caixa caixa) {
        try {
            int option;
            do {
                String input = JOptionPane.showInputDialog("Escolha uma opção:\n1 - Adicionar Produto\n2 - Remover Produto\n3 - Verificar Estoque\n4 - Adicionar Quantidade\n5 - Remover Quantidade\n6 - Pesquisar Produto\n7 - Salvar \n8 - transações\n9 - Buscar Transações\n10 - Sair");

                if (input == null || input.isEmpty()) {
                    option = 10;
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
                        pesquisarPorIdOuCategoriaOuFabricante(estoque);
                        break;

                    case 7:
                        estoque.salvarEstoque(Path.pathEstoque);
                        break;

                    case 8:
                        estoque.transacoes();
                        break;

                    case 9:
                        int opcao = Integer.parseInt(JOptionPane.showInputDialog("pesquisar por:\n1 - Entrada \n2 - saida"));
                        switch (opcao) {
                            case 1:
                                estoque.PesquisarTransacaoTipo("Entrada");
                                break;
                            case 2:
                                estoque.PesquisarTransacaoTipo("Saida");
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                        }
                        break;

                    case 10:
                        JOptionPane.showMessageDialog(null, "Saindo do programa.");
                        estoque.salvarEstoque(Path.pathEstoque);
                        estoque.salvarTransacao(Path.pathTransacao);
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                }
            } while (option != 10);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
        }
    }

    private static void menuCaixa(Caixa caixa) {
        try {
            int option;
            do {
                String input = JOptionPane.showInputDialog("Escolha uma opção:\n1 - Comprar\n2 - Sair");

                if (input == null || input.isEmpty()) {
                    option = 2;
                } else {
                    option = Integer.parseInt(input);
                }

                switch (option) {
                    case 1:
                        realizarCompra(caixa);
                        break;

                    case 2:
                        JOptionPane.showMessageDialog(null, "Saindo do caixa.");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                }
            } while (option != 2);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
        }
    }

    private static void adicionarProduto(Estoque estoque) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do produto:"));
            if (!estoque.Idexiste(id)) {
                String name = JOptionPane.showInputDialog("Nome do produto:");
                String manufacturer = JOptionPane.showInputDialog("Fabricante");
                Category category = (Category) JOptionPane.showInputDialog(null, "Selecione a Categoria do Produto:", "Categoria", JOptionPane.QUESTION_MESSAGE, null, Category.values(), Category.ALIMENTO);
                BigDecimal value = BigDecimal.valueOf(Double.parseDouble(JOptionPane.showInputDialog("Valor do produto:")));
                int quant = Integer.parseInt((JOptionPane.showInputDialog("Digite a Quantidade em Estoque")));

                Products newProduct = new Products(id, name, manufacturer, category, value, quant);
                estoque.addEstoque(newProduct);
            } else {
                JOptionPane.showMessageDialog(null, "Id ja cadastrado");
            }
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

    private static void pesquisarPorIdOuCategoriaOuFabricante(Estoque estoque) {
        try {
            String input = JOptionPane.showInputDialog("Escolha uma opção:\n1 - Pesquisar por ID\n2 - Pesquisar por Categoria\n3 - Pesquisar por Fabricante");
            int option = Integer.parseInt(input);

            switch (option) {
                case 1:
                    pesquisarPorId(estoque);
                    break;

                case 2:
                    pesquisarPorCategoria(estoque);
                    break;

                case 3:
                    pesquisarPorFabricante(estoque);
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
            Category category = (Category) JOptionPane.showInputDialog(null, "Selecione a Categoria:", "Categoria", JOptionPane.QUESTION_MESSAGE, null, Category.values(), Category.ALIMENTO);
            ArrayList<Products> produtosPorCategoria = estoque.getProductsByCategory(category);

            if (!produtosPorCategoria.isEmpty()) {
                StringBuilder mensagem = new StringBuilder("Produtos na categoria \n" + category + ":\n");
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

    private static void pesquisarPorFabricante(Estoque estoque) {
        try {
            String manufacturer = JOptionPane.showInputDialog("Digite o nome do fabricante:");
            ArrayList<Products> produtosPorFabricante = estoque.getProductByManufacturer(manufacturer);

            if (!produtosPorFabricante.isEmpty()) {
                StringBuilder mensagem = new StringBuilder("Produtos do fabricante \n" + manufacturer + ":\n");
                for (Products produto : produtosPorFabricante) {
                    mensagem.append(produto).append("\n");
                }
                JOptionPane.showMessageDialog(null, mensagem.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado do fabricante " + manufacturer + ".");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar por fabricante: " + e.getMessage());
        }
    }

    private static void realizarCompra(Caixa caixa) {
        ArrayList<String> listaCompra = new ArrayList<>();

        int option;
        BigDecimal valorTotalCompra = BigDecimal.ZERO;

        do {
            Products produtoComprado = escolherProduto(caixa.getEstoque());

            if (produtoComprado != null) {
                int quantidadeComprada = Integer.parseInt(JOptionPane.showInputDialog("Quantidade a ser comprada:"));
                if (quantidadeComprada > 0){
                    try {
                        caixa.adicionarProduto(produtoComprado, quantidadeComprada);

                        BigDecimal valorTotalProduto = produtoComprado.getValue().multiply(BigDecimal.valueOf(quantidadeComprada));

                        listaCompra.add(produtoComprado.getName() + " \n" + quantidadeComprada + " - R$: " +
                                produtoComprado.getValue() + " - Total R$:" + valorTotalProduto);
                        valorTotalCompra = valorTotalCompra.add(valorTotalProduto);
                    } catch (ProdutoNaoExisteException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao adicionar produto à compra: " + e.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "A quantidade de produto não pode ser menor ou igual a 0");
                }
            } else {
                break;
            }

            String[] options = {"Continuar Comprando", "Finalizar"};
            option = JOptionPane.showOptionDialog(
                    null,
                    "Valor total da compra: R$ " + valorTotalCompra,
                    "Continuar Comprando ou Finalizar",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (option == 1) {
                break;
            }

            String[] opcoesCompra = {"Adicionar Produto", "Remover Produto"};
            int opcaoCompra = JOptionPane.showOptionDialog(
                    null,
                    "Escolha uma opção:",
                    "Adicionar ou Remover Produto",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoesCompra,
                    opcoesCompra[0]
            );

            if (opcaoCompra == 1) {
                Products produtoRemover = escolherProduto(caixa.getEstoque());
                int quantidadeRemover = Integer.parseInt(JOptionPane.showInputDialog("Quantidade a ser removida:"));
                caixa.removerProduto(produtoRemover, quantidadeRemover);
                break;
            }
        } while (true);

        PaymentsServices paymentsServices = new PaymentsServices();

        int payments = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Digite a forma de pagamento: \n1 - PIX \n2 - Credito\n3 - Debito\n4 - Dinheiro\nTotal R$: " + valorTotalCompra));

        switch (payments) {
            case 1:
                String ValorPix = paymentsServices.pagamentoPix(valorTotalCompra);
                listaCompra.add(ValorPix);
                caixa.finalizarCompra();
                JOptionPane.showMessageDialog(null, "Produtos comprados:\n" + String.join("\n", listaCompra) + "\n");
                break;
            case 2:
                String Card = JOptionPane.showInputDialog(null, "Digite o numero do cartão");
                if (validarNumeroCartao(Card)) {
                    listaCompra.add(paymentsServices.pagamentoCredito(valorTotalCompra));
                    caixa.finalizarCompra();
                    JOptionPane.showMessageDialog(null, "Produtos comprados:\n" + String.join("\n", listaCompra) + "\n");
                } else {
                    JOptionPane.showMessageDialog(null, "Cartão Invalido\nCompra Nâo concluida");
                }
                break;

            case 3:
                String CardDeb = JOptionPane.showInputDialog(null, "Digite o numero do cartão");
                if (validarNumeroCartao(CardDeb)) {
                    listaCompra.add(paymentsServices.pagamentoDebito(valorTotalCompra));
                    caixa.finalizarCompra();
                    JOptionPane.showMessageDialog(null, "Produtos comprados:\n" + String.join("\n", listaCompra) + "\n");
                } else {
                    JOptionPane.showMessageDialog(null, "Cartão Invalido\nCompra Nâo concluida");
                }
                break;
            case 4:
                BigDecimal ValorRecebido = new BigDecimal(JOptionPane.showInputDialog(null,
                        "Valor Total R$: " + valorTotalCompra + "\nValor recebido R$: "));
                if (ValorRecebido.compareTo(valorTotalCompra) >= 0) {
                    listaCompra.add(paymentsServices.pagamentoDinheiro(valorTotalCompra, ValorRecebido));
                    caixa.finalizarCompra();
                    JOptionPane.showMessageDialog(null, "Produtos comprados:\n" + String.join("\n", listaCompra) + "\n");
                } else {
                    JOptionPane.showMessageDialog(null, "Valor insuficiente para efetuar o Pagamento\nCompra Nâo concluida");
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Digite uma forma de pagamento valida");
        }
    }
}