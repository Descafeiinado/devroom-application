package net.heavenus.chest.models;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class User {

    public String name;
    public HashMap<String, String> inventory;

    public User(){}

    public User(String name, HashMap<String, String> inventory) {
        this.name = name;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, String> inventory) {
        this.inventory = inventory;
    }

    public void addItem(int slot, String item){
        inventory.put(String.valueOf(slot), item);
    }

    public void removeItem(int slot){
        inventory.remove(String.valueOf(slot));
    }

    public String getItem(int slot){
        return inventory.get(String.valueOf(slot));
    }

}
