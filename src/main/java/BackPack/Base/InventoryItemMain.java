package BackPack.Base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;


	@Mod(modid = "inventoryitemmod", name = "Inventory Item Tutorial", version = "1.0.0")

	public final class InventoryItemMain
	{
		
		@Instance("inventoryitemmod")
		public static InventoryItemMain instance;
	
		
		@SidedProxy(clientSide = "BackPack.Base.ClientProxy", serverSide = "BackPack.Base.CommonProxy")
		public static CommonProxy proxy;
		public static Item itemstore;
	
		
		
		/** This is used to keep track of GUIs that we make*/
		private static int modGuiIndex = 0;
	
	
		/** This is the starting index for all of our mod's item IDs  (not needed in 1.7.2) */
		private static int modItemIndex = 7000;
	
	
		/** Set our custom inventory Gui index to the next available Gui index */
		public static final int ItemInventoryGuiIndex = modGuiIndex++;
	
	
	
		// ITEMS ETC.


		
	
		@EventHandler
		public void preInit(FMLPreInitializationEvent event)
		{
			itemstore = new ItemStore();
			itemstore.setUnlocalizedName("item_store").setCreativeTab(CreativeTabs.tabMisc);
			GameRegistry.registerItem(itemstore, "ItemStore");
		}
	
	
	
		@EventHandler
		public void load(FMLInitializationEvent event)
		{
		// no renderers or entities to register, but whatever
		proxy.registerRenderers();
		// register CommonProxy as our GuiHandler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		}
	
		@EventHandler
		public void postInit(FMLPostInitializationEvent event)
		{
	
		}

	}

