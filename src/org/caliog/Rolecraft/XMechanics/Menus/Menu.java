package org.caliog.Rolecraft.XMechanics.Menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.caliog.Rolecraft.Manager;

public abstract class Menu {

	private List<MenuItem> items = new ArrayList<MenuItem>();
	protected int height;
	protected String name;
	private boolean giveToolToPlayers;

	public Menu() {

	}

	public Menu(int height, String name) {
		this.height = height;
		this.name = name;
		this.giveToolToPlayers = false;
		init();
	}

	public void setItem(int i, MenuItem item) {
		items.set(i, item);
	}

	protected void init() {
		items.clear();
		for (int i = 0; i < height * 9; i++) {
			items.add(new MenuItem(Material.AIR));
		}
	}

	public MenuItem findMenuItem(String name) {
		if (name == null)
			return null;
		for (MenuItem item : items) {
			if (item.getName() != null)
				if (item.getName().equals(name))
					return item;
		}
		return null;
	}

	public void display(Player player) {
		Menu menu = this;
		if (player != null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Manager.plugin, new Runnable() {
				@Override
				public void run() {
					player.openInventory(new MenuInventoryView(menu, player));
				}
			});

		}
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public ItemStack getItem(int i) {
		return items.get(i).createItemStack();
	}

	public boolean clicked(InventoryClickEvent event) {
		if (items.size() > event.getSlot()) {
			items.get(event.getSlot()).onClick(event);
			return items.get(event.getSlot()).isEditable();
		}
		return false;
	}

	public boolean giveToolToPlayers() {
		return giveToolToPlayers;
	}

}
