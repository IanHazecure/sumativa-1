package com.ordenes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDENES")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @Column(name = "CLIENTE")
    private String cliente;
    
    @Column(name = "PRODUCTO")
    private String producto;
    
    @Column(name = "CANTIDAD")
    private int cantidad;
    
    @Column(name = "PRECIO")
    private double precio;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "FECHA")
    private String fecha;

    public Orden() {
    }
    
    public Orden(int id, String cliente, String producto, int cantidad, double precio, String estado, String fecha) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.estado = estado;
        this.fecha = fecha;
    }
//getter setters 
    public int getId() {
        return id;
}



    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
    

    public int getCantidad() {
        return cantidad;
    }


    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }


    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }



    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
