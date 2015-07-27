package BackPack.Base;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStore extends Item

{

public ItemStore()

{

super();

// ItemStacks that store an NBT Tag Compound are limited to stack size of 1

this.maxStackSize = 1;

}



/**

 * Called every tick while there is an ItemInventory in the player's inventory

 * This is the method we will use to access the GUI and also to write to NBT

 * when necessary

 */

@Override

public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean isCurrentItem)

{

// Only Player's will be accessing the GUI

if (!world.isRemote && entity instanceof EntityPlayer)

{

// Cast Entity parameter as an EntityPlayer

EntityPlayer player = (EntityPlayer) entity;



// Check if the player is not in a menu, if key 'I' is pressed and

// the player is currently holding the correct type of item (an ItemInventory)

/*if (FMLClientHandler.instance().getClient().inGameHasFocus

&& Keyboard.isKeyDown(Keyboard.KEY_I) &&

player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemStore)

{

// Open the correct GUI for the player at player's position

player.openGui(InventoryItemMain.instance, InventoryItemMain.ItemInventoryGuiIndex, world, (int) player.posX, (int) player.posY, (int) player.posZ);

}*/



// If our ContainerItem is currently open, write contents to NBT when needsUpdate is true

if(player.openContainer != null && player.openContainer instanceof ContainerItem

&& ((ContainerItem) player.openContainer).needsUpdate)

{

((ContainerItem) player.openContainer).writeToNBT();

// Set needsUpdate back to false so we don't continually write to NBT

((ContainerItem) player.openContainer).needsUpdate = false;

}

}

}

@Override
public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) 
{
	if(!par2World.isRemote && par3EntityPlayer instanceof EntityPlayer)
	{
		if(par3EntityPlayer.getHeldItem() != null && par3EntityPlayer.getHeldItem().getItem() instanceof ItemStore)
		{
			par3EntityPlayer.openGui(InventoryItemMain.instance, InventoryItemMain.ItemInventoryGuiIndex, par2World, (int) par3EntityPlayer.posX, (int) par3EntityPlayer.posY, (int) par3EntityPlayer.posZ);
		}
	}
	return par1ItemStack;
}

/*@Override
public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, net.minecraft.util.BlockPos pos, net.minecraft.util.EnumFacing side, float hitX, float hitY, float hitZ) 
{
	if(!worldIn.isRemote && playerIn instanceof EntityPlayer)
	{
		if(playerIn.getHeldItem() != null && playerIn.getHeldItem().getItem() instanceof ItemStore)
		{
			playerIn.openGui(InventoryItemMain.instance, InventoryItemMain.ItemInventoryGuiIndex, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		}
	}
	return true;
	
	
}*/


@Override
public int getMaxItemUseDuration(ItemStack itemstack)
{
	return 1;
}



/*@Override

@SideOnly(Side.CLIENT)
public void registerIcons(IconRegister iconRegister)

{

this.itemIcon = iconRegister.registerIcon("inventoryitemmod:" + this.getUnlocalizedName().substring(5));

}*/

}
