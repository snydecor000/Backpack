package BackPack.Base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IChatComponent;

public class InventoryItem implements IInventory
{
	private String name = "Inventory Item";
	
	/** Defining your inventory size this way is handy */
	public static final int INV_SIZE = 8;
	/** Inventory's size must be same as number of slots you add to the Container class */
	ItemStack[] inventory = new ItemStack[INV_SIZE];
	/**
	* @param itemstack - the ItemStack to which this inventory belongs
	*/
	public InventoryItem(ItemStack itemstack)
	{
		// Just in case the itemstack doesn't yet have an NBT Tag Compound:
		if (!itemstack.hasTagCompound())
		{
			itemstack.setTagCompound(new NBTTagCompound());
		}	
		// Read the inventory contents from NBT
		readFromNBT(itemstack.getTagCompound());
	
	}
	
	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{	
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
		{
			if(stack.stackSize > amount)
			{		
				stack = stack.splitStack(amount);
						
				if(stack.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
			else
			{
				setInventorySlotContents(slot, null);
			}
			this.markDirty();
		}
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
		{
			setInventorySlotContents(slot, null);
		}
		return stack;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		this.inventory[slot] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		this.markDirty();
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public boolean hasCustomName()
	{
		return name.length() > 0;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public void markDirty()
	{
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			if (this.getStackInSlot(i) != null && this.getStackInSlot(i).stackSize == 0)
			this.setInventorySlotContents(i, null);
		}
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}
	
	@Override
	public void openInventory(EntityPlayer entity) {}
	
	@Override
	public void closeInventory(EntityPlayer entity) {}
	
	/**
	* This method doesn't seem to do what it claims to do, as
	* items can still be left-clicked and placed in the inventory
	* even when this returns false
	*/
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		// Don't want to be able to store the inventory item within itself
		// Bad things will happen, like losing your inventory
		// Actually, this needs a custom Slot to work
		return !(itemstack.getItem() instanceof ItemStore);
	}
	
	/**
	* A custom method to read our inventory from an ItemStack's NBT compound
	*/
	public void readFromNBT(NBTTagCompound compound) 
	{
		// now you must include the NBTBase type ID when getting the list; NBTTagCompound's ID is 10
		NBTTagList items = compound.getTagList("INVENTORY_ITEM", compound.getId());
		for (int i = 0; i < items.tagCount(); ++i) 
		{
			// tagAt(int) has changed to getCompoundTagAt(int)
			NBTTagCompound item = items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if (slot >= 0 && slot < getSizeInventory()) 
			{
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}	
	/**
	* A custom method to write our inventory to an ItemStack's NBT compound
	*/
	public void writeToNBT(NBTTagCompound tagcompound)
	{
		// Create a new NBT Tag List to store itemstacks as NBT Tags
	
		NBTTagList nbttaglist = new NBTTagList();
	
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			// Only write stacks that contain item
			if (this.getStackInSlot(i) != null)
			{
				// Make a new NBT Tag Compound to write the itemstack and slot index to
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("Slot", i);
				// Writes the itemstack in slot(i) to the Tag Compound we just made
				this.getStackInSlot(i).writeToNBT(nbttagcompound1);
				// add the tag compound to our tag list
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
	// Add the TagList to the ItemStack's Tag Compound with the name "ItemInventory"
	tagcompound.setTag("ItemInventory", nbttaglist);
	}

	@Override
	public IChatComponent getDisplayName() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getField(int id) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() 
	{
		// TODO Auto-generated method stub
		
	}
}
