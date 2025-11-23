package arboles;

public class Producto {

    public int ID;
    public String nombre;
    public int stock;
    public String ubicacion;

    public Producto(int ID, String nombre, int Stock, String ubicacion)
    {
        this.ID = ID;
        this.nombre = nombre;
        this.stock = Stock;
        this.ubicacion = ubicacion;
    }

    public int  getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }


    public int getStock() {
        return stock;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    @Override
    public String toString() {
        return ID + " | " + nombre + " | Stock: " + stock + " | Ubicación: " + ubicacion;
    }

}
