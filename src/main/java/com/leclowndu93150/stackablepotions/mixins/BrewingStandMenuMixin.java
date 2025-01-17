package com.leclowndu93150.stackablepotions.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BrewingStandMenu.class)
public abstract class BrewingStandMenuMixin extends AbstractContainerMenu {
    private BrewingStandMenuMixin(MenuType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(
            method = {"quickMoveStack"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/BrewingStandMenu$PotionSlot;mayPlaceItem(Lnet/minecraft/world/item/alchemy/PotionBrewing;Lnet/minecraft/world/item/ItemStack;)Z"
            )},
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true
    )
    private void onQuickMoveStack(Player player, int index, CallbackInfoReturnable<ItemStack> info, ItemStack itemStack, Slot slot, ItemStack itemStack2) {
        if (slot.mayPlace(itemStack)) {
            boolean movedItems = false;

            for (int i = 0; i < 3; ++i) {
                Slot slot2 = this.getSlot(i);
                if (slot2.getItem().isEmpty() && slot.mayPlace(itemStack2)) {
                    if (itemStack2.getCount() > slot2.getMaxStackSize()) {
                        slot2.set(itemStack2.split(slot2.getMaxStackSize()));
                    } else {
                        slot2.set(itemStack2.split(itemStack2.getCount()));
                    }

                    movedItems = true;
                    slot2.setChanged();
                    if (itemStack2.isEmpty()) {
                        break;
                    }
                }
            }

            if (movedItems) {
                info.setReturnValue(ItemStack.EMPTY);
            }
        }

    }

    @Redirect(
            method = {"quickMoveStack"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/BrewingStandMenu$PotionSlot;mayPlaceItem(Lnet/minecraft/world/item/alchemy/PotionBrewing;Lnet/minecraft/world/item/ItemStack;)Z"
            )
    )
    private boolean onQuickMoveStackRedirect(PotionBrewing potionBrewing, ItemStack p_39134_) {
        return false;
    }
}