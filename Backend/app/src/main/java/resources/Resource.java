<<<<<<< Updated upstream
package resources;

public abstract class Resource {
    private int quantity;

    public Resource(int quantity) {
        this.quantity = quantity;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void consume(int amount) {
        if (this.getQuantity() == 0 && !(this.getQuantity() < amount)) {
            this.setQuantity(0);
        }
        else {
            this.setQuantity(this.getQuantity() - amount);
        }
    }




    @Override
    public String toString() {
        return "Resource{" +
                "quantity=" + quantity +
                '}';
    }
}
=======
package resources;

public abstract class Resource {

}
>>>>>>> Stashed changes
