package Model.services;

import Model.entities.Products;
import Model.enums.Categoria;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;

import java.io.*;
import java.util.ArrayList;

public class Estoque implements Model.repositories.Estoque{
    private ArrayList<Products> productsEstoque;

    public Products getProductById(int id) {
        for (Products product : productsEstoque) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
    public Estoque(){}

    public boolean produtoExiste(Products product) {
        return productsEstoque.contains(product);
    }


    public void addEstoque(Products product) throws ProdutoJaExisteException {
        try {
            if (productsEstoque == null) {
                productsEstoque = new ArrayList<>();
            }

            if (produtoExiste(product)) {
                throw new ProdutoJaExisteException("Error: Produto já existe no estoque");
            } else {
                productsEstoque.add(product);
            }
        } catch (ProdutoJaExisteException e) {
            throw e;
        }
    }

    @Override
    public void removeEstoque(Products product, int id) throws ProdutoNaoExisteException {
        boolean productFound = false;

        for (Products p : productsEstoque) {
            if (p.getId() == id) {
                productFound = true;
                productsEstoque.remove(p);
                break;
            }
        }
        if (!productFound) {
            throw new ProdutoNaoExisteException("Error: Produto não existe no estoque");
        }
    }


    @Override
    public void AddQuant(Products product, int quant) throws ProdutoNaoExisteException {
        if (produtoExiste(product)) {
            int newQuant = product.getQuanti() + quant;
            product.setQuanti(newQuant);
        } else {
            throw new ProdutoNaoExisteException("Error: Produto não existe no estoque");
        }
    }

    
    @Override
    public void removeQuant(Products product, int quant) throws ProdutoNaoExisteException {
        if (produtoExiste(product)) {
            int newQuant = product.getQuanti() - quant;
            product.setQuanti(newQuant);
        } else {
            throw new ProdutoNaoExisteException("Error: Produto não existe no estoque");
        }
    }
    public ArrayList<Products> getProductsByCategory(Categoria category) {
        ArrayList<Products> productsByCategory = new ArrayList<>();
        for (Products product : productsEstoque) {
            if (product.getCategoria() == category) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory;
    }

    @Override
    public String toString() {
        return productsEstoque + "\n";
    }

    public void salvarEstoque(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            ArrayList<Products> tempProducts = new ArrayList<>();

            for (Products product : productsEstoque) {
                boolean productFound = false;

                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 4) {
                        double id = Double.parseDouble(parts[1]);
                        if (id == product.getId()) {
                            productFound = true;
                            writer.write(product.getName() + "," + product.getId() + "," + product.getCategoria() + "," + product.getValue() + "," + product.getQuanti());
                            writer.newLine();
                        }
                    }
                }
                reader.close();

                if (!productFound) {
                    tempProducts.add(product);
                }
            }

            for (Products tempProduct : tempProducts) {
                writer.write(tempProduct.getName() + "," + tempProduct.getId() + "," + tempProduct.getCategoria() + "," + tempProduct.getValue() + "," + tempProduct.getQuanti());
                writer.newLine();
            }

            System.out.println("Estoque salvo com sucesso no arquivo: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarEstoque(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            productsEstoque = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length > 4) {
                    String nome = parts[0];
                    double id = Double.parseDouble(parts[1]);
                    Categoria categoria = Categoria.valueOf(parts[2]);
                    double valor = Double.parseDouble(parts[3]);
                    int quantidade = Integer.parseInt(parts[4]);

                    Products product = new Products(nome, id, categoria, valor, quantidade);
                    productsEstoque.add(product);
                }
            }
            System.out.println("Estoque carregado com sucesso do arquivo: " + filePath);
        } catch (IOException e) {
            System.out.println("Estoque Vazio");
        }
    }

    public double calcularValorTotalEstoque() {
        double valorTotal = 0;

        for (Products product : productsEstoque) {
            valorTotal += product.getValue() * product.getQuanti();
        }

        return valorTotal;
    }

}