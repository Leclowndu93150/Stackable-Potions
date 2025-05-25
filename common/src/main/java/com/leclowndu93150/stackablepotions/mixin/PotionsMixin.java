package com.leclowndu93150.stackablepotions.mixin;

import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = {PotionItem.class, SplashPotionItem.class, LingeringPotionItem.class})
public class PotionsMixin{

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Item.Properties iHateMojang(Item.Properties properties) {
        return properties.stacksTo(16);
    }
}