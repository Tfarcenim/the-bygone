package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.common.item.MaliciousWarHornItem;
import com.jamiedev.bygone.core.util.HeightGetter;
import com.kekecreations.jinxedlib.core.util.JinxedRegistryHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public class BGDataComponents {

    public static final String ECHO_GONG_DATA = "bygone:echo_gong_data";
    public static final String WAR_HORN_DATA = "bygone:war_horn_data";
    public static final String MAP_HEIGHT = "bygone:map_height";


    public static int getEchoGongData(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(ECHO_GONG_DATA) : 0;
    }

    public static void setEchoGongData(ItemStack stack,int data) {
        stack.getOrCreateTag().putInt(ECHO_GONG_DATA,data);
    }

    public static MaliciousWarHornItem.WarHornData getWarHornData(ItemStack stack,boolean useDefault) {
        if (!stack.hasTag() || !stack.getTag().contains(WAR_HORN_DATA)) {
            return useDefault ? MaliciousWarHornItem.WarHornData.EMPTY : null;
        }
    }

    public static void setWarHornData(ItemStack stack, MaliciousWarHornItem.WarHornData data) {

    }
}