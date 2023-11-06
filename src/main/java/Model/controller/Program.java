package Model.controller;

import Model.exceptions.ProdutoNãoExisteException;
import Model.services.Estoque;
import Model.entities.Products;
import Model.enums.Categoria;

import java.util.Locale;
import java.util.Scanner;


public class Program {
    public static void main(String[] args) throws ProdutoNãoExisteException {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Estoque estoque = new Estoque();
        Products products = new Products();

        boolean continuar = true;
        while (continuar != false){


            System.out.println("Bem vindo!! para sair Digite 4");
            int OpcaoConti = sc.nextInt();
            sc.nextLine();

            if (OpcaoConti == 1){
                System.out.println("Product Name:");
                String name = sc.nextLine();
                System.out.println("Product ID: ");
                Double id = sc.nextDouble();
                System.out.println("Digite a categoria (Drinks, Juice, Softs, Lanches, Petiscos, Beer):");
                sc.nextLine();
                String categoriaString = sc.nextLine();
                Categoria categoria = Categoria.valueOf(categoriaString);
                System.out.println("Value of Product: ");
                Double price = sc.nextDouble();
                System.out.println("Quantidade em estoque:");
                int qntEstoque = sc.nextInt();


                products = new Products(name, id,categoria,price);

                estoque.addEstoque(products,qntEstoque);

                System.out.println("Produto add ao estoque com sucesso: " + estoque );
            }
            else if (OpcaoConti == 2) {
                System.out.println("Add no estoque qnts itens:");
                int quantAdd = sc.nextInt();
                estoque.AddQuant(quantAdd);
                System.out.println("Quantidade atualizada");
                System.out.println(estoque);
            }
            else if (OpcaoConti == 3) {
                System.out.println("Remove no estoque qnts itens:");
                int quantRemove = sc.nextInt();
                estoque.removeQuant(quantRemove);
                System.out.println("Quantidade atualizada");
                System.out.println(estoque);
            }
            else if (OpcaoConti == 4){
                continuar = false;
            }
            else{
                System.out.println("Opção invalida");
            }
        }
        System.out.println(estoque);
        sc.close();
    }
}
