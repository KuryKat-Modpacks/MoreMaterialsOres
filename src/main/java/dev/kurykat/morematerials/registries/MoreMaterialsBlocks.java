/*
 *     MoreMaterials - Minecraft Mod
 *     Copyright (C) 2023 KuryKat
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.kurykat.morematerials.registries;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.kurykat.morematerials.MoreMaterials;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.Tags;

import static dev.kurykat.morematerials.foundation.data.TagGen.pickaxeOnly;
import static dev.kurykat.morematerials.foundation.data.TagGen.tagBlockAndItem;

@SuppressWarnings({"unused", "SameParameterValue"})
public class MoreMaterialsBlocks {
    private static final Registrate REGISTRATE = MoreMaterials.getRegistrate();

    static {
        REGISTRATE.creativeModeTab(() -> MoreMaterials.CREATIVE_TAB);
    }

    public static final BlockEntry<DropExperienceBlock> RUBY_ORE = createOreBlock(
            "ruby", OreTypes.STONE, properties -> new DropExperienceBlock(properties, UniformInt.of(3, 7)),
            Material.STONE, MaterialColor.STONE, SoundType.STONE, MoreMaterialsItems.RUBY
    ).register();

    public static final BlockEntry<DropExperienceBlock> DEEPSLATE_RUBY_ORE = createOreBlock(
            "ruby", OreTypes.DEEPSLATE, properties -> new DropExperienceBlock(properties, UniformInt.of(3, 7)),
            Material.STONE, MaterialColor.DEEPSLATE, SoundType.DEEPSLATE, MoreMaterialsItems.RUBY
    ).register();

    public static final BlockEntry<Block> RUBY_BLOCK = createStorageBlock(
            "ruby", Block::new,
            Material.METAL, MaterialColor.COLOR_RED, SoundType.METAL
    ).register();

    public static final BlockEntry<DropExperienceBlock> ALEXANDRITE_ORE = createOreBlock(
            "alexandrite", OreTypes.STONE, properties -> new DropExperienceBlock(properties, UniformInt.of(3, 7)),
            Material.STONE, MaterialColor.STONE, SoundType.STONE, MoreMaterialsItems.ALEXANDRITE
    ).register();

    public static final BlockEntry<DropExperienceBlock> DEEPSLATE_ALEXANDRITE_ORE = createOreBlock(
            "alexandrite", OreTypes.DEEPSLATE, properties -> new DropExperienceBlock(properties, UniformInt.of(3, 7)),
            Material.STONE, MaterialColor.DEEPSLATE, SoundType.DEEPSLATE, MoreMaterialsItems.ALEXANDRITE
    ).register();

    public static final BlockEntry<Block> ALEXANDRITE_BLOCK = createStorageBlock(
            "alexandrite", Block::new,
            Material.METAL, MaterialColor.COLOR_CYAN, SoundType.METAL
    ).register();

    public static final BlockEntry<Block> END_CELESLAR_ORE = createOreBlock(
            "celeslar", OreTypes.END, Block::new,
            Material.STONE, MaterialColor.SAND, SoundType.STONE, MoreMaterialsItems.RAW_CELESLAR
    ).item().properties(Item.Properties::fireResistant).build().register();

    public static final BlockEntry<Block> CELESLAR_BLOCK = createStorageBlock(
            "celeslar", Block::new,
            Material.METAL, MaterialColor.LAPIS, SoundType.METAL
    ).item().properties(Item.Properties::fireResistant).build().register();

    public static final BlockEntry<Block> RAW_CELESLAR_BLOCK = createRawStorageBlock(
            "celeslar", Block::new,
            Material.STONE, MaterialColor.GLOW_LICHEN, SoundType.STONE
    ).item().properties(Item.Properties::fireResistant).build().register();

    private enum OreTypes {
        STONE,
        DEEPSLATE,
        NETHER,
        END
    }

    private static <T extends Block> BlockBuilder<T, Registrate> createOreBlock(
            String materialName,
            OreTypes type,
            NonNullFunction<BlockBehaviour.Properties, T> blockFactory,
            Material material,
            MaterialColor materialColor,
            SoundType soundType,
            ItemEntry<Item> itemDrop
    ) {
        String typeName = type.name().toLowerCase();
        String blockNameSuffix = materialName + "_ore";
        String blockName = type.equals(OreTypes.STONE) ? blockNameSuffix : typeName + "_" + blockNameSuffix;
        String groundTag = type.equals(OreTypes.DEEPSLATE) ? "ores_in_ground/deepslate"
                : type.equals(OreTypes.NETHER) ? "ores_in_ground/netherrack"
                : type.equals(OreTypes.END) ? "ores_in_ground/end_stone"
                : "ores_in_ground/stone";

        return REGISTRATE
                .block(blockName, blockFactory)
                .initialProperties(material)
                .properties(prop -> prop
                        .color(materialColor)
                        .requiresCorrectToolForDrops()
                        .strength(
                                type.equals(OreTypes.DEEPSLATE) ? 4.5F : 3.0F,
                                type.equals(OreTypes.END) ? 9.0F : 3.0F
                        )
                        .sound(soundType)
                )
                .transform(pickaxeOnly())
                .blockstate((context, provider) ->
                        provider.simpleBlock(
                                context.get(),
                                provider.models()
                                        .cubeAll(
                                                context.getName(),
                                                provider.modLoc("block/ores/" + materialName + "/" + typeName)
                                        )
                        )
                )
                .loot((lootTables, block) -> lootTables.add(
                        block, RegistrateBlockLootTables.createOreDrop(block, itemDrop.get())
                ))
                .tag(BlockTags.NEEDS_IRON_TOOL)
                .tag(Tags.Blocks.ORES)
                .transform(
                        tagBlockAndItem(
                                "ores/" + materialName,
                                groundTag
                        )
                )
                .tag(Tags.Items.ORES)
                .build();
    }

    private static <T extends Block> BlockBuilder<T, Registrate> createStorageBlock(
            String materialName,
            NonNullFunction<BlockBehaviour.Properties, T> blockFactory,
            Material material,
            MaterialColor materialColor,
            SoundType soundType
    ) {
        return REGISTRATE
                .block(materialName + "_block", blockFactory)
                .initialProperties(material, materialColor)
                .properties(properties -> properties
                        .strength(5.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .sound(soundType)
                )
                .transform(pickaxeOnly())
                .blockstate((context, provider) ->
                        provider.simpleBlock(
                                context.get(),
                                provider.models()
                                        .cubeAll(
                                                context.getName(),
                                                provider.modLoc("block/storage_blocks/" + materialName)
                                        )
                        )
                )
                .tag(BlockTags.NEEDS_IRON_TOOL)
                .tag(Tags.Blocks.STORAGE_BLOCKS)
                .tag(BlockTags.BEACON_BASE_BLOCKS)
                .transform(tagBlockAndItem("storage_blocks/" + materialName))
                .tag(Tags.Items.STORAGE_BLOCKS)
                .build();
    }

    private static <T extends Block> BlockBuilder<T, Registrate> createRawStorageBlock(
            String materialName,
            NonNullFunction<BlockBehaviour.Properties, T> blockFactory,
            Material material,
            MaterialColor materialColor,
            SoundType soundType
    ) {
        String rawMaterialName = "raw_" + materialName;
        return REGISTRATE
                .block(rawMaterialName + "_block", blockFactory)
                .initialProperties(material, materialColor)
                .properties(properties -> properties
                        .strength(5.0F, 6.0F)
                        .requiresCorrectToolForDrops()
                        .sound(soundType)
                )
                .transform(pickaxeOnly())
                .blockstate((context, provider) ->
                        provider.simpleBlock(
                                context.get(),
                                provider.models()
                                        .cubeAll(
                                                context.getName(),
                                                provider.modLoc("block/storage_blocks/" + rawMaterialName)
                                        )
                        )
                )
                .tag(BlockTags.NEEDS_IRON_TOOL)
                .tag(Tags.Blocks.STORAGE_BLOCKS)
                .transform(tagBlockAndItem("storage_blocks/" + rawMaterialName))
                .tag(Tags.Items.STORAGE_BLOCKS)
                .build();
    }

    public static void register() {
    }
}
