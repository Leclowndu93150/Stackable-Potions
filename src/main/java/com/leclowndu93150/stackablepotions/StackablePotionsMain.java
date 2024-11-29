package com.leclowndu93150.stackablepotions;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(StackablePotionsMain.MODID)
public class StackablePotionsMain
{
    public static final String MODID = "stackablepotions";
    private static final Logger LOGGER = LogUtils.getLogger();

    public StackablePotionsMain(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Hello from StackablePotionsMain !");
    }

}
