package org.caliog.Rolecraft.XMechanics.Menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.caliog.Rolecraft.Manager;

public class MenuInventoryView extends InventoryView {

	private final Menu menu;
	private final Player player;
	private Inventory top;
	private final Listener listener;

	private final MenuInventoryView myself;

	public MenuInventoryView(Menu menu, Player player) {
		myself = this;
		Manager.plugin.getServer().getPluginManager().registerEvents(listener = new Listener() {
			@EventHandler(priority = EventPriority.HIGH)
			public void onInventoryClick(InventoryClickEvent event) {
				if (event.getView().equals(myself) && event.getWhoClicked() instanceof Player
						&& event.getView().getTopInventory().equals(event.getClickedInventory())) {
					if (!myself.clicked(event)) {
						event.setCancelled(true);
					}
				}
			}

		}, Manager.plugin);
		this.menu = menu;
		this.player = player;
		createInventory();
	}

	private void createInventory() {
		top = Bukkit.createInventory(null, menu.getHeight() * 9, menu.getName());
		for (int i = 0; i < top.getSize(); i++) {
			top.setItem(i, menu.getItem(i));
		}
	}

	@Override
	public Inventory getBottomInventory() {
		return player.getInventory();
	}

	@Override
	public HumanEntity getPlayer() {
		return player;
	}

	@Override
	public Inventory getTopInventory() {
		return top;
	}

	@Override
	public InventoryType getType() {
		return InventoryType.CHEST;
	}

	public void closeListener() {
		HandlerList.unregisterAll(listener);
	}

	public boolean clicked(InventoryClickEvent event) {
		if (event.getClickedInventory() != null && event.getClickedInventory().getTitle() != null)
			if (event.getClickedInventory().getTitle().equals(top.getTitle()))
				return menu.clicked(event);
		return false;
	}

	public void reload() {
		for (int i = 0; i < top.getSize(); i++) {
			top.setItem(i, menu.getItem(i));
		}
	}

}
