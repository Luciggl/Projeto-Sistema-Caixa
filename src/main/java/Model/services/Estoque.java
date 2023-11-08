package Model.services;

import Model.entities.Products;
import Model.enums.Categoria;
import Model.exceptions.ProdutoJaExisteException;
import Model.exceptions.ProdutoNaoExisteException;

import java.util.ArrayList;

public class Estoque implements Model.repositories.Estoque{
    private ArrayList<Products> productsEstoque;
    private int QuantInstoque;

    public int getQuantInstoque() {
        return QuantInstoque;
    }

    public void setQuantInstoque(int quantInstoque) {
        QuantInstoque = quantInstoque;
    }

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


    public void addEstoque(Products product, int qntEstoque) throws ProdutoJaExisteException {
        try {
            if (productsEstoque == null) {
                productsEstoque = new ArrayList<>();
                this.QuantInstoque = qntEstoque;
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
            int newQuant = getQuantInstoque() + quant;
            setQuantInstoque(newQuant);
        } else {
            throw new ProdutoNaoExisteException("Error: Produto não existe no estoque");
        }
    }



    @Override
    public void removeQuant(Products product, int quant) throws ProdutoNaoExisteException {
        if (produtoExiste(product)) {
            int newQuant = getQuantInstoque() - quant;
            setQuantInstoque(newQuant);
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
        return productsEstoque + "\n" + "QuantInstoque=" + getQuantInstoque();
    }
}