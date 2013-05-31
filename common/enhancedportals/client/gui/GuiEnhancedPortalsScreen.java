package enhancedportals.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import alz.core.gui.GuiExtendedScreen;
import enhancedportals.EnhancedPortals;
import enhancedportals.lib.Textures;

public class GuiEnhancedPortalsScreen extends GuiExtendedScreen
{
    public class GuiTextureSlot extends GuiFakeItemSlot
    {
        public GuiTextureSlot(int x, int y, IInventory inv, int slot, GuiExtendedScreen par)
        {
            super(x, y, inv, slot, par);
        }

        public GuiTextureSlot(int x, int y, ItemStack stack, GuiExtendedScreen par)
        {
            super(x, y, stack, par);
        }

        @Override
        public void onClick(int x, int y, int button, ItemStack stack)
        {
            if (!isOnSelf(x, y))
            {
                return;
            }

            if ((button == 0 || button == 1) && stack != null)
            {
                setSlot(stack);
            }
            else if (button == 1 && stack == null)
            {
                setSlot(new ItemStack(Item.dyePowder, 1, 5));
            }
        }
        
        @Override
        public boolean setSlot(ItemStack originalStack)
        {
            ItemStack stack = null;
            
            if (originalStack != null)
            {
                stack = originalStack.copy();

                if (Textures.getTextureFromItemStack(stack) == null)
                {
                    return false;
                }
                else
                {
                    if (stack.itemID == Item.dyePowder.itemID) // Display a coloured portal instead of the dye item
                    {
                        stack = new ItemStack(EnhancedPortals.proxy.blockDummyPortal, 1, stack.getItemDamage());
                    }
                }
            }
            
            return super.setSlot(stack);
        }
    }

    public GuiEnhancedPortalsScreen(Container par1Container, IInventory inventory)
    {
        super(par1Container, inventory);
    }

    @Override
    public void drawExtendedSlotsForeground(int mX, int mY)
    {
        int bX = (width - xSize) / 2, bY = (height - ySize) / 2;
        String str = "";

        GuiFakeItemSlot slot = getSlotAt(mX - bX, mY - bY);

        if (slot != null && slot.getItemStack() != null)
        {
            str = slot.getItemStack().getDisplayName();

            if (str.length() > 0)
            {
                List<String> strList = new ArrayList<String>();
                
                if (slot instanceof GuiTextureSlot)
                {
                    strList.add("Facade");
                    strList.add(EnumChatFormatting.GRAY + str);
                }
                else
                {
                    strList.add(str);
                }

                drawHoveringText(strList, mX - bX, mY - bY, fontRenderer);
                RenderHelper.enableGUIStandardItemLighting();
            }
        }
    }
}
