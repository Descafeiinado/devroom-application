package net.heavenus.chest.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemSerializer {

    public static String serialize(ItemStack item) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            BukkitObjectOutputStream output = new BukkitObjectOutputStream(stream);

            output.writeObject(item);
            output.close();

            return Base64Coder.encodeLines(stream.toByteArray());
        } catch (Exception exception) {
            throw new IllegalStateException("Item couldn't be serialized.", exception);
        }
    }

    public static ItemStack deserialize(String data) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream input = new BukkitObjectInputStream(stream);
            ItemStack item = (ItemStack) input.readObject();

            input.close();

            return item;
        } catch (ClassNotFoundException | IOException exception) {
            throw new IllegalStateException("Item couldn't be deserialized.", exception);
        }
    }

}
