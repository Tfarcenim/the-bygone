package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.Bygone;

import java.util.Locale;
import java.util.function.Supplier;

import com.jamiedev.bygone.common.worldgen.structure.*;
import com.kekecreations.jinxedlib.core.util.JinxedRegistryHelper;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class BGStructures
{
    public static final Supplier<StructureType<AncientRootStructure>> ANCIENT_ROOTS =  register("ancient_roots", AncientRootStructure.CODEC);
    public static final Supplier<StructurePieceType> ANCIENT_ROOTS_PIECES = registerPieces("ancient_roots", () -> AncientRootGenerator.Piece::new);
    public static final Supplier<StructureType<AncientRootStructure>> ABANDONED_FARM = register("abandoned_farm", AncientRootStructure.CODEC);
    public static final Supplier<StructurePieceType> ABANDONED_FARM_PIECES = registerPieces("abandoned_farm",() ->  AbandonedFarmGenerator.Piece::new);

    public static final Supplier<StructureType<RuinStructure>> BLEMISH_RUINS = register( "ruin", RuinStructure.CODEC);
    public static final Supplier<StructureType<AmberRuinsStructure>> AMBER_RUINS = register( "amber_ruins", AmberRuinsStructure.CODEC);
    public static final Supplier<StructureType<AmberPyramidStructure>> AMBER_PYRAMID = register( "amber_pyramid", AmberPyramidStructure.CODEC);
    public static final Supplier<StructureType<MegalithRuinsStructure>> MEGALITH_RUINS = register( "megalith_ruins", MegalithRuinsStructure.CODEC);
    public static final Supplier<StructureType<MinilithStructure>> MINILITHS = register( "miniliths", MinilithStructure.CODEC);

    public static final Supplier<StructureType<BygoneMineshaftStructure>> BYGONE_MINESHAFT = register( "bygone_mineshaft", BygoneMineshaftStructure.CODEC);
    public static Supplier<StructurePieceType> BYGONE_MINESHAFT_CORRIDOR = registerPieces("corr",() -> BygoneMineshaftGenerator.BygoneMineshaftCorridor::new);
    public static Supplier<StructurePieceType> BYGONE_MINESHAFT_CROSSING = registerPieces("cros",() ->  BygoneMineshaftGenerator.BygoneMineshaftCrossing::new);

    public static Supplier<StructurePieceType> BYGONE_MINESHAFT_ROOM = registerPieces("room",() -> BygoneMineshaftGenerator.BygoneMineshaftRoom::new);
    public static Supplier<StructurePieceType> BYGONE_MINESHAFT_STAIRS = registerPieces("star",() -> BygoneMineshaftGenerator.BygoneMineshaftStairs::new);

    public static final Supplier<StructureType<BygoneFossilStructure>> BYGONE_FOSSIL = register( "bygone_fossil", BygoneFossilStructure.CODEC);
    public static Supplier<StructurePieceType> FOSSIL_PIECES = registerPieces("bygone_fossil",() -> BygoneFossilGenerator.Piece::new);
    public static Supplier<StructurePieceType> AMBER_RUIN_PIECES = registerPieces("ruin",() ->  RuinGenerator.Piece::new);
    public static Supplier<StructurePieceType> RUIN_PIECES = registerPieces("amber_ruins", () -> AmberRuinsGenerator.Piece::new);
    public static Supplier<StructurePieceType> PYRAMID_PIECES = registerPieces("amber_pyramid",() -> AmberRuinsGenerator.Piece::new);

    public static Supplier<StructurePieceType> MEGALITH_RUINS_PIECES = registerPieces("megalith_ruins",() -> AmberRuinsGenerator.Piece::new);
    public static Supplier<StructurePieceType> MINILITH_PIECES = registerPieces("miniliths",() ->  MinilithGenerator.Piece::new);

    public static final  Supplier<StructureType<TestRootStructure>> TEST_ROOTS = register( "test_roots", TestRootStructure.CODEC);

    public static final  Supplier<StructureType<BygonePortalStructure>> BYGONE_PORTAL = register( "bygone_portal", BygonePortalStructure.CODEC);
    public static Supplier<StructurePieceType> PORTAL_PIECE = registerPieces("bygone_portal",() -> BygonePortalGenerator.Piece::new);


    private static StructurePieceType register(StructurePieceType type, String id) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, id.toLowerCase(Locale.ROOT), type);
    }

    private static StructurePieceType register(StructurePieceType.ContextlessType type, String id) {
        return register((StructurePieceType)type, id);
    }

    private static StructurePieceType register(StructurePieceType.StructureTemplateType type, String id) {
        return register((StructurePieceType)type, id);
    }

    private static <S extends Structure> Supplier<StructureType<S>> register(String name, Codec<S> codec) {
        return (Supplier<StructureType<S>>)(Object) JinxedRegistryHelper.register(BuiltInRegistries.STRUCTURE_TYPE, Bygone.MOD_ID, name,
                () -> (StructureType<S>)() -> codec);
    }

    private static Supplier<StructurePieceType> registerPieces(String name, Supplier<StructurePieceType> supplier) {
        return JinxedRegistryHelper.register(BuiltInRegistries.STRUCTURE_PIECE, Bygone.MOD_ID, name,supplier);
    }

    public static void init() {
    }
}
