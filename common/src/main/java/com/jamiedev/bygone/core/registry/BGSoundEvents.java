package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.Bygone;
import com.kekecreations.jinxedlib.core.util.JinxedRegistryHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

import static com.jamiedev.bygone.Bygone.id;

public class BGSoundEvents
{
    public static final String AMBIENT_ANCIENTFOREST_ADDITIONS = "ambient.underhang.additions";
    public static final String AMBIENT_AMBERDESERT_ADDITIONS = "ambient.amber_desert.additions";
    public static final String AMBIENT_PRIMORDIALOCEAN_ADDITIONS = "ambient.primordial_ocean.additions";
    public static final String MUSIC_ALPHAHANG_ADDITIONS = "music.bygone.alphahang";
    public static final String MUSIC_AMBER_DESERT_ADDITIONS = "music.bygone.amber_desert";
    public static final String MUSIC_MEGALITH_FIELDS_ADDITIONS = "music.bygone.megalith_fields";
    public static final String MUSIC_SHELFHOLLOWS_ADDITIONS = "music.bygone.shelfhollows";
    public static final String MUSIC_ANCIENTFOREST_ADDITIONS = "music.bygone.underhang";
    public static final String MUSIC_PRIMORDIALOCEAN_ADDITIONS = "music.bygone.primordial_ocean";
    public static final String AMBIENT_SHELFHOLLOW_ADDITIONS = "ambient.shelfhollow.additions";


    public static final Supplier<SoundEvent> MUSIC_DISC_SHUFFLE = register("music_disc.shuffle");


    public static final String BLOCK_MEGALITH_BLOCK_IDLE = "block.megalith_block.idle";
    public static Supplier<SoundEvent> BLOCK_MEGALITH_BLOCK_IDLE_ADDITIONS_EVENT = register(BLOCK_MEGALITH_BLOCK_IDLE );

    public static final String ENTITY_BIGBEAK_AMBIENT = "entity.bigbeak.ambient";
    public static final String ENTITY_BIGBEAK_HURT = "entity.bigbeak.hurt";
    public static final String ENTITY_BIGBEAK_DEATH = "entity.bigbeak.death";
    public static final String ENTITY_BIGBEAK_JUMP = "entity.bigbeak.jump";
    public static final String ENTITY_BIGBEAK_STEP = "entity.bigbeak.step";
    public static final String ENTITY_BIGBEAK_STEP_WOOD = "entity.bigbeak.step_wood";
    public static final String ENTITY_BIGBEAK_GALLOP = "entity.bigbeak.gallop";

    public static final String ENTITY_COPPERBUG_AMBIENT = "entity.copperbug.ambient";
    public static final String ENTITY_COPPERBUG_HURT = "entity.copperbug.hurt";
    public static final String ENTITY_COPPERBUG_DEATH = "entity.copperbug.death";
    public static final String ENTITY_COPPERBUG_EAT = "entity.copperbug.eat";

    public static final String ENTITY_MOOBOO_AMBIENT = "entity.mooboo.ambient";
    public static final String ENTITY_MOOBOO_HURT = "entity.mooboo.hurt";
    public static final String ENTITY_MOOBOO_DEATH = "entity.mooboo.death";

    public static final String ENTITY_FUNGUSPARENT_AMBIENT = "entity.fungus_parent.ambient";
    public static final String ENTITY_FUNGUSPARENT_BABY_AMBIENT = "entity.fungus_parent.ambient_baby";
    public static final String ENTITY_FUNGUSPARENT_HURT = "entity.fungus_parent.hurt";
    public static final String ENTITY_FUNGUSPARENT_DEATH = "entity.fungus_parent.death";

    public static final String ENTITY_PEST_AMBIENT = "entity.pest.ambient";
    public static final String ENTITY_PEST_HURT = "entity.pest.hurt";
    public static final String ENTITY_PEST_DEATH = "entity.pest.death";
    public static final String ENTITY_PEST_EAT = "entity.pest.eat";

    public static final String ENTITY_WHISKBILL_AMBIENT = "entity.whiskbill.ambient";
    public static final String ENTITY_WHISKBILL_HURT = "entity.whiskbill.hurt";
    public static final String ENTITY_WHISKBILL_DEATH = "entity.whiskbill.death";
    public static final String ENTITY_WHISKBILL_ROAR = "entity.whiskbill.roar";

    public static final String ENTITY_NECTAUR_AMBIENT = "entity.nectaur.ambient";
    public static final String ENTITY_NECTAUR_BELLOW = "entity.nectaur.bellow";
    public static final String ENTITY_NECTAUR_HURT = "entity.nectaur.hurt";
    public static final String ENTITY_NECTAUR_DEATH = "entity.nectaur.death";
    public static final String ENTITY_NECTAUR_SCREECH = "entity.nectaur.screech";

    public static final String ENTITY_WRAITH_AMBIENT = "entity.wraith.ambient";
    public static final String ENTITY_WRAITH_ATTACK = "entity.wraith.attack";
    public static final String ENTITY_WRAITH_HURT = "entity.wraith.hurt";
    public static final String ENTITY_WRAITH_DEATH = "entity.wraith.death";
    public static final String ENTITY_WRAITH_TELEPORT = "entity.wraith.teleport";
    public static final String ENTITY_WRAITH_FLY = "entity.wraith.fly";

    public static Supplier<SoundEvent> WRAITH_AMBIENT_ADDITIONS_EVENT = register(ENTITY_WRAITH_AMBIENT);
    public static Supplier<SoundEvent> WRAITH_ATTACK_ADDITIONS_EVENT = register(ENTITY_WRAITH_ATTACK);
    public static Supplier<SoundEvent> WRAITH_DEATH_ADDITIONS_EVENT = register(ENTITY_WRAITH_DEATH);
    public static Supplier<SoundEvent> WRAITH_FLY_ADDITIONS_EVENT = register(ENTITY_WRAITH_FLY);
    public static Supplier<SoundEvent> WRAITH_HURT_ADDITIONS_EVENT = register(ENTITY_WRAITH_HURT);
    public static Supplier<SoundEvent> WRAITH_TELEPORT_ADDITIONS_EVENT = register(ENTITY_WRAITH_TELEPORT);

    public static final String ENTITY_LITHY_AMBIENT = "entity.lithy.ambient";
    public static final String ENTITY_LITHY_TRIP = "entity.lithy.trip";
    public static final String ENTITY_LITHY_HURT = "entity.lithy.hurt";
    public static final String ENTITY_LITHY_DEATH = "entity.lithy.death";

    public static Supplier<SoundEvent> LITHY_AMBIENT_ADDITIONS_EVENT = register(ENTITY_LITHY_AMBIENT);
    public static Supplier<SoundEvent> LITHY_TRIP_ADDITIONS_EVENT = register(ENTITY_LITHY_TRIP);
    public static Supplier<SoundEvent> LITHY_DEATH_ADDITIONS_EVENT = register(ENTITY_LITHY_DEATH);
    public static Supplier<SoundEvent> LITHY_HURT_ADDITIONS_EVENT = register(ENTITY_LITHY_HURT);


    public static final String HOOK_RETRIEVE = "entity.hook.retrieve";
    public static final String HOOK_HIT = "entity.hook.hit";
    public static final String HOOK_THROW = "entity.hook.throw";

    public static final Supplier<SoundEvent> HOOK_RETRIEVE_ADDITIONS_EVENT = register(HOOK_RETRIEVE);
    public static final Supplier<SoundEvent> HOOK_HIT_ADDITIONS_EVENT = register(HOOK_HIT);
    public static final Supplier<SoundEvent> HOOK_THROW_ADDITIONS_EVENT = register(HOOK_THROW);
    
    public static final String ITEM_WAR_HORN_USE_ID = "item.war_horn.use";
    public static final Supplier<SoundEvent> WAR_HORN_USE_EVENT = register(ITEM_WAR_HORN_USE_ID);

    public static final String ITEM_WHIRLIWEED_BUNDLE_USE_ID = "item.whirliweed_bundle.use";
    public static final Supplier<SoundEvent> WHIRLIWEED_BUNDLE_USE_EVENT = register(ITEM_WHIRLIWEED_BUNDLE_USE_ID);

    public static final String ITEM_ECHO_GONG_USE_ID = "item.echo_gong.use";
    public static final Supplier<SoundEvent> ECHO_GONG_USE_EVENT = register(ITEM_ECHO_GONG_USE_ID);

    public static final String ITEM_ECHO_GONG_CHARGE_ID = "item.echo_gong.charge";
    public static final Supplier<SoundEvent> ECHO_GONG_CHARGE_EVENT = register(ITEM_ECHO_GONG_CHARGE_ID);

    public static Supplier<SoundEvent> AMBIENT_ANCIENTFOREST_ADDITIONS_EVENT = register(AMBIENT_ANCIENTFOREST_ADDITIONS);
    public static Supplier<SoundEvent> AMBIENT_AMBERDESERT_ADDITIONS_EVENT = register(AMBIENT_AMBERDESERT_ADDITIONS);
    public static Supplier<SoundEvent> AMBIENT_PRIMORDIALOCEAN_ADDITIONS_EVENT = register(AMBIENT_PRIMORDIALOCEAN_ADDITIONS);

    public static Supplier<SoundEvent> MUSIC_ALPHAHANG_ADDITIONS_EVENT = register(MUSIC_ALPHAHANG_ADDITIONS);
    public static Supplier<SoundEvent> MUSIC_ANCIENTFOREST_ADDITIONS_EVENT = register(MUSIC_ANCIENTFOREST_ADDITIONS);
    public static Supplier<SoundEvent> MUSIC_AMBERDESERT_ADDITIONS_EVENT = register(MUSIC_AMBER_DESERT_ADDITIONS);
    public static Supplier<SoundEvent> MUSIC_MEGALITH_FIELDS_ADDITIONS_EVENT = register(MUSIC_MEGALITH_FIELDS_ADDITIONS);
    public static Supplier<SoundEvent> MUSIC_SHELFHOLLOWS_ADDITIONS_EVENT = register(MUSIC_SHELFHOLLOWS_ADDITIONS);

    public static Supplier<SoundEvent> AMBIENT_SHELFHOLLOW_ADDITIONS_EVENT = register(AMBIENT_SHELFHOLLOW_ADDITIONS);
    public static Supplier<SoundEvent> MUSIC_PRIMORDIALOCEAN_ADDITIONS_EVENT = register(MUSIC_PRIMORDIALOCEAN_ADDITIONS);

    public static Supplier<SoundEvent> BIGBEAK_AMBIENT_ADDITIONS_EVENT = register(ENTITY_BIGBEAK_AMBIENT);
    public static Supplier<SoundEvent> BIGBEAK_HURT_ADDITIONS_EVENT = register(ENTITY_BIGBEAK_HURT);
    public static Supplier<SoundEvent> BIGBEAK_DEATH_ADDITIONS_EVENT = register(ENTITY_BIGBEAK_DEATH);
    public static Supplier<SoundEvent> BIGBEAK_JUMP_ADDITIONS_EVENT = register(ENTITY_BIGBEAK_JUMP);
    public static Supplier<SoundEvent> BIGBEAK_STEP_ADDITIONS_EVENT = register(ENTITY_BIGBEAK_STEP);
    public static Supplier<SoundEvent> BIGBEAK_STEP_WOOD_ADDITIONS_EVENT = register(ENTITY_BIGBEAK_STEP_WOOD);
    public static Supplier<SoundEvent> BIGBEAK_GALLOP_ADDITIONS_EVENT = register(ENTITY_BIGBEAK_GALLOP);

    public static Supplier<SoundEvent> COPPERBUG_AMBIENT_ADDITIONS_EVENT = register(ENTITY_COPPERBUG_AMBIENT);
    public static Supplier<SoundEvent> COPPERBUG_HURT_ADDITIONS_EVENT = register(ENTITY_COPPERBUG_HURT);
    public static Supplier<SoundEvent> COPPERBUG_DEATH_ADDITIONS_EVENT = register(ENTITY_COPPERBUG_DEATH);
    public static Supplier<SoundEvent> COPPERBUG_EAT_ADDITIONS_EVENT = register(ENTITY_COPPERBUG_EAT);

    public static Supplier<SoundEvent> MOOBOO_AMBIENT_ADDITIONS_EVENT = register(ENTITY_MOOBOO_AMBIENT);
    public static Supplier<SoundEvent> MOOBOO_HURT_ADDITIONS_EVENT = register(ENTITY_MOOBOO_HURT);
    public static Supplier<SoundEvent> MOOBOO_DEATH_ADDITIONS_EVENT = register(ENTITY_MOOBOO_DEATH);

    public static Supplier<SoundEvent> FUNGUSPARENT_AMBIENT_ADDITIONS_EVENT = register(ENTITY_FUNGUSPARENT_AMBIENT);
    public static Supplier<SoundEvent> FUNGUSPARENT_AMBIENT_BABY_ADDITIONS_EVENT = register(ENTITY_FUNGUSPARENT_BABY_AMBIENT);
    public static Supplier<SoundEvent> FUNGUSPARENT_HURT_ADDITIONS_EVENT = register(ENTITY_FUNGUSPARENT_HURT);
    public static Supplier<SoundEvent> FUNGUSPARENT_DEATH_ADDITIONS_EVENT = register(ENTITY_FUNGUSPARENT_DEATH);

    public static Supplier<SoundEvent> NECTAUR_BELLOW_ADDITIONS_EVENT = register(ENTITY_NECTAUR_BELLOW);
    public static Supplier<SoundEvent> NECTAUR_AMBIENT_ADDITIONS_EVENT = register(ENTITY_NECTAUR_AMBIENT);
    public static Supplier<SoundEvent> NECTAUR_HURT_ADDITIONS_EVENT = register(ENTITY_NECTAUR_HURT);
    public static Supplier<SoundEvent> NECTAUR_DEATH_ADDITIONS_EVENT = register(ENTITY_NECTAUR_DEATH);
    public static Supplier<SoundEvent> NECTAUR_SCREECH_ADDITIONS_EVENT = register(ENTITY_NECTAUR_SCREECH);

    public static Supplier<SoundEvent> PEST_AMBIENT_ADDITIONS_EVENT = register(ENTITY_PEST_AMBIENT);
    public static Supplier<SoundEvent> PEST_HURT_ADDITIONS_EVENT = register(ENTITY_PEST_HURT);
    public static Supplier<SoundEvent> PEST_DEATH_ADDITIONS_EVENT = register(ENTITY_PEST_DEATH);
    public static Supplier<SoundEvent> PEST_EAT_ADDITIONS_EVENT = register(ENTITY_PEST_EAT);

    public static Supplier<SoundEvent> WHISKBILL_AMBIENT_ADDITIONS_EVENT = register(ENTITY_WHISKBILL_AMBIENT);
    public static Supplier<SoundEvent> WHISKBILL_HURT_ADDITIONS_EVENT = register(ENTITY_WHISKBILL_HURT);
    public static Supplier<SoundEvent> WHISKBILL_DEATH_ADDITIONS_EVENT = register(ENTITY_WHISKBILL_DEATH);
    public static Supplier<SoundEvent> WHISKBILL_ROAR_ADDITIONS_EVENT = register(ENTITY_WHISKBILL_ROAR);

    private static Supplier<SoundEvent> register(String id) {
        return JinxedRegistryHelper.register(BuiltInRegistries.SOUND_EVENT, Bygone.MOD_ID,id,() -> SoundEvent.createVariableRangeEvent(id(id)));
    }


    public static void init() {}

}
