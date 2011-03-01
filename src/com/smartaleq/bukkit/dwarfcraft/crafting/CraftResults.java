package com.smartaleq.bukkit.dwarfcraft.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.Block;
import net.minecraft.server.CraftingRecipe;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ShapedRecipes;

public class CraftResults {
	
	private final static CraftResults instance = new CraftResults();
	private List<CraftingRecipe> recipeList = new ArrayList<CraftingRecipe>();

	public static CraftResults getInstance() {
		return instance;
	}

	// start of minecraft code
	
	// smartaleq note: some recipes are missing, notably armor
	public CraftResults() {
		this.addShaped(new ItemStack(Item.PAPER, 3), new Object[] { "###",
				Character.valueOf('#'), Item.SUGAR_CANE });
		this.addShaped(new ItemStack(Item.BOOK, 1), new Object[] { "#", "#",
				"#", Character.valueOf('#'), Item.PAPER });
		this.addShaped(new ItemStack(Block.FENCE, 2), new Object[] { "###",
				"###", Character.valueOf('#'), Item.STICK });
		this.addShaped(new ItemStack(Block.JUKEBOX, 1),
				new Object[] { "###", "#X#", "###", Character.valueOf('#'),
						Block.WOOD, Character.valueOf('X'), Item.DIAMOND });
		this.addShaped(new ItemStack(Block.NOTE_BLOCK, 1), new Object[] {
				"###", "#X#", "###", Character.valueOf('#'), Block.WOOD,
				Character.valueOf('X'), Item.REDSTONE });
		this.addShaped(new ItemStack(Block.BOOKSHELF, 1),
				new Object[] { "###", "XXX", "###", Character.valueOf('#'),
						Block.WOOD, Character.valueOf('X'), Item.BOOK });
		this.addShaped(new ItemStack(Block.SNOW_BLOCK, 1), new Object[] { "##",
				"##", Character.valueOf('#'), Item.SNOW_BALL });
		this.addShaped(new ItemStack(Block.CLAY, 1), new Object[] { "##", "##",
				Character.valueOf('#'), Item.CLAY_BALL });
		this.addShaped(new ItemStack(Block.BRICK, 1), new Object[] { "##",
				"##", Character.valueOf('#'), Item.CLAY_BRICK });
		this.addShaped(new ItemStack(Block.GLOWSTONE, 1), new Object[] { "###",
				"###", "###", Character.valueOf('#'), Item.GLOWSTONE_DUST });
		this.addShaped(new ItemStack(Block.WOOL, 1), new Object[] { "###",
				"###", "###", Character.valueOf('#'), Item.STRING });
		this.addShaped(new ItemStack(Block.TNT, 1),
				new Object[] { "X#X", "#X#", "X#X", Character.valueOf('X'),
						Item.SULPHUR, Character.valueOf('#'), Block.SAND });
		this.addShaped(new ItemStack(Block.STEP, 3, 3), new Object[] { "###",
				Character.valueOf('#'), Block.COBBLESTONE });
		this.addShaped(new ItemStack(Block.STEP, 3, 0), new Object[] { "###",
				Character.valueOf('#'), Block.STONE });
		this.addShaped(new ItemStack(Block.STEP, 3, 1), new Object[] { "###",
				Character.valueOf('#'), Block.SANDSTONE });
		this.addShaped(new ItemStack(Block.STEP, 3, 2), new Object[] { "###",
				Character.valueOf('#'), Block.WOOD });
		this.addShaped(new ItemStack(Block.LADDER, 1), new Object[] { "# #",
				"###", "# #", Character.valueOf('#'), Item.STICK });
		this.addShaped(new ItemStack(Item.WOOD_DOOR, 1), new Object[] { "##",
				"##", "##", Character.valueOf('#'), Block.WOOD });
		this.addShaped(new ItemStack(Item.IRON_DOOR, 1), new Object[] { "##",
				"##", "##", Character.valueOf('#'), Item.IRON_INGOT });
		this.addShaped(new ItemStack(Item.SIGN, 1),
				new Object[] { "###", "###", " X ", Character.valueOf('#'),
						Block.WOOD, Character.valueOf('X'), Item.STICK });
		this.addShaped(new ItemStack(Item.CAKE, 1), new Object[] { "AAA",
				"BEB", "CCC", Character.valueOf('A'), Item.MILK_BUCKET,
				Character.valueOf('B'), Item.SUGAR, Character.valueOf('C'),
				Item.WHEAT, Character.valueOf('E'), Item.EGG });
		this.addShaped(new ItemStack(Item.SUGAR, 1), new Object[] { "#",
				Character.valueOf('#'), Item.SUGAR_CANE });
		this.addShaped(new ItemStack(Block.WOOD, 4), new Object[] { "#",
				Character.valueOf('#'), Block.LOG });
		this.addShaped(new ItemStack(Item.STICK, 4), new Object[] { "#", "#",
				Character.valueOf('#'), Block.WOOD });
		this.addShaped(new ItemStack(Block.TORCH, 4), new Object[] { "X", "#",
				Character.valueOf('X'), Item.COAL, Character.valueOf('#'),
				Item.STICK });
		this.addShaped(new ItemStack(Block.TORCH, 4), new Object[] { "X", "#",
				Character.valueOf('X'), new ItemStack(Item.COAL, 1, 1),
				Character.valueOf('#'), Item.STICK });
		this.addShaped(new ItemStack(Item.BOWL, 4), new Object[] { "# #",
				" # ", Character.valueOf('#'), Block.WOOD });
		this.addShaped(new ItemStack(Block.RAILS, 16), new Object[] { "X X",
				"X#X", "X X", Character.valueOf('X'), Item.IRON_INGOT,
				Character.valueOf('#'), Item.STICK });
		this.addShaped(new ItemStack(Item.MINECART, 1), new Object[] { "# #",
				"###", Character.valueOf('#'), Item.IRON_INGOT });
		this.addShaped(new ItemStack(Block.JACK_O_LANTERN, 1),
				new Object[] { "A", "B", Character.valueOf('A'), Block.PUMPKIN,
						Character.valueOf('B'), Block.TORCH });
		this.addShaped(new ItemStack(Item.STORAGE_MINECART, 1),
				new Object[] { "A", "B", Character.valueOf('A'), Block.CHEST,
						Character.valueOf('B'), Item.MINECART });
		this.addShaped(new ItemStack(Item.POWERED_MINECART, 1),
				new Object[] { "A", "B", Character.valueOf('A'), Block.FURNACE,
						Character.valueOf('B'), Item.MINECART });
		this.addShaped(new ItemStack(Item.BOAT, 1), new Object[] { "# #",
				"###", Character.valueOf('#'), Block.WOOD });
		this.addShaped(new ItemStack(Item.BUCKET, 1), new Object[] { "# #",
				" # ", Character.valueOf('#'), Item.IRON_INGOT });
		this.addShaped(new ItemStack(Item.FLINT_AND_STEEL, 1),
				new Object[] { "A ", " B", Character.valueOf('A'),
						Item.IRON_INGOT, Character.valueOf('B'), Item.FLINT });
		this.addShaped(new ItemStack(Item.BREAD, 1), new Object[] { "###",
				Character.valueOf('#'), Item.WHEAT });
		this.addShaped(new ItemStack(Block.WOOD_STAIRS, 4), new Object[] {
				"#  ", "## ", "###", Character.valueOf('#'), Block.WOOD });
		this.addShaped(new ItemStack(Item.FISHING_ROD, 1), new Object[] {
				"  #", " #X", "# X", Character.valueOf('#'), Item.STICK,
				Character.valueOf('X'), Item.STRING });
		this.addShaped(new ItemStack(Block.COBBLESTONE_STAIRS, 4),
				new Object[] { "#  ", "## ", "###", Character.valueOf('#'),
						Block.COBBLESTONE });
		this.addShaped(new ItemStack(Item.PAINTING, 1),
				new Object[] { "###", "#X#", "###", Character.valueOf('#'),
						Item.STICK, Character.valueOf('X'), Block.WOOL });
		this.addShaped(new ItemStack(Item.GOLDEN_APPLE, 1), new Object[] {
				"###", "#X#", "###", Character.valueOf('#'), Block.GOLD_BLOCK,
				Character.valueOf('X'), Item.APPLE });
		this.addShaped(new ItemStack(Block.LEVER, 1),
				new Object[] { "X", "#", Character.valueOf('#'),
						Block.COBBLESTONE, Character.valueOf('X'), Item.STICK });
		this.addShaped(new ItemStack(Block.REDSTONE_TORCH_ON, 1),
				new Object[] { "X", "#", Character.valueOf('#'), Item.STICK,
						Character.valueOf('X'), Item.REDSTONE });
		this.addShaped(new ItemStack(Item.DIODE, 1), new Object[] { "#X#",
				"III", Character.valueOf('#'), Block.REDSTONE_TORCH_ON,
				Character.valueOf('X'), Item.REDSTONE, Character.valueOf('I'),
				Block.STONE });
		this.addShaped(new ItemStack(Item.WATCH, 1), new Object[] { " # ",
				"#X#", " # ", Character.valueOf('#'), Item.GOLD_INGOT,
				Character.valueOf('X'), Item.REDSTONE });
		this.addShaped(new ItemStack(Item.COMPASS, 1), new Object[] { " # ",
				"#X#", " # ", Character.valueOf('#'), Item.IRON_INGOT,
				Character.valueOf('X'), Item.REDSTONE });
		this.addShaped(new ItemStack(Block.STONE_BUTTON, 1), new Object[] {
				"#", "#", Character.valueOf('#'), Block.STONE });
		this.addShaped(new ItemStack(Block.STONE_PLATE, 1), new Object[] {
				"##", Character.valueOf('#'), Block.STONE });
		this.addShaped(new ItemStack(Block.WOOD_PLATE, 1), new Object[] { "##",
				Character.valueOf('#'), Block.WOOD });
		this.addShaped(new ItemStack(Block.DISPENSER, 1), new Object[] { "###",
				"#X#", "#R#", Character.valueOf('#'), Block.COBBLESTONE,
				Character.valueOf('X'), Item.BOW, Character.valueOf('R'),
				Item.REDSTONE });
		this.addShaped(new ItemStack(Item.BED, 1), new Object[] { "###", "XXX",
				Character.valueOf('#'), Block.WOOL, Character.valueOf('X'),
				Block.WOOD });

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void addShaped(ItemStack itemstack, Object... aobject) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;

		if (aobject[i] instanceof String[]) {
			String[] astring = (String[]) ((String[]) aobject[i++]);

			for (int l = 0; l < astring.length; ++l) {
				String s1 = astring[l];

				++k;
				j = s1.length();
				s = s + s1;
			}
		} else {
			while (aobject[i] instanceof String) {
				String s2 = (String) aobject[i++];

				++k;
				j = s2.length();
				s = s + s2;
			}
		}

		HashMap hashmap;

		for (hashmap = new HashMap(); i < aobject.length; i += 2) {
			Character character = (Character) aobject[i];
			ItemStack itemstack1 = null;

			if (aobject[i + 1] instanceof Item) {
				itemstack1 = new ItemStack((Item) aobject[i + 1]);
			} else if (aobject[i + 1] instanceof Block) {
				itemstack1 = new ItemStack((Block) aobject[i + 1], 1, -1);
			} else if (aobject[i + 1] instanceof ItemStack) {
				itemstack1 = (ItemStack) aobject[i + 1];
			}

			hashmap.put(character, itemstack1);
		}

		ItemStack[] aitemstack = new ItemStack[j * k];

		for (int i1 = 0; i1 < j * k; ++i1) {
			char c0 = s.charAt(i1);

			if (hashmap.containsKey(Character.valueOf(c0))) {
				aitemstack[i1] = ((ItemStack) hashmap
						.get(Character.valueOf(c0))).j();
			} else {
				aitemstack[i1] = null;
			}
		}

		this.recipeList.add(new ShapedRecipes(j, k, aitemstack, itemstack));
	}

	public ItemStack getResult(InventoryCrafting inventorycrafting) {
		for (CraftingRecipe craftingrecipe :recipeList) {
			if (craftingrecipe.a(inventorycrafting)) { //if the table items recipe matches a crafting recipe
				return craftingrecipe.b(inventorycrafting); //return the recipe output itemstack
			}
		}

		return null;
	}
}