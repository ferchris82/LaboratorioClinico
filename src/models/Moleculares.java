package models;

public class Moleculares {
    private int id_codigo;
    private String name;
    private Double precio;

    public Moleculares() {
    }

    public Moleculares(int id_codigo, String name, Double precio) {
        this.id_codigo = id_codigo;
        this.name = name;
        this.precio = precio;
    }

    public int getId_codigo() {
        return id_codigo;
    }

    public void setId_codigo(int id_codigo) {
        this.id_codigo = id_codigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    
}
