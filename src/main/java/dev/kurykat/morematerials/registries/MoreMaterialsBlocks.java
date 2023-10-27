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

import dev.kurykat.morematerials.MoreMaterialsConstants;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MoreMaterialsBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreMaterialsConstants.MOD_ID);

    public static final RegistryObject<Block> RUBY_ORE = register(
            "ruby_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties
                            .of(Material.STONE)
                            .strength(3.0F, 3.0F)
                            .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)
            ),
            MoreMaterialsConstants.DEFAULT_ITEM_PROPS
    );

    public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE = register(
            "deepslate_ruby_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties
                            .copy(RUBY_ORE.get())
                            .color(MaterialColor.DEEPSLATE)
                            .strength(4.5F, 3.0F)
                            .sound(SoundType.DEEPSLATE),
                    UniformInt.of(3, 7)
            ),
            MoreMaterialsConstants.DEFAULT_ITEM_PROPS
    );

    public static final RegistryObject<Block> RUBY_BLOCK = register(
            "ruby_block",
            () -> new Block(
                    BlockBehaviour.Properties
                            .of(Material.METAL, MaterialColor.COLOR_RED)
                            .strength(5.0F, 6.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.METAL)
            ),
            MoreMaterialsConstants.DEFAULT_ITEM_PROPS
    );

    private static <T extends Block> RegistryObject<T> register(String blockName, Supplier<T> blockSupplier, Item.Properties itemProperties) {
        RegistryObject<T> block = BLOCKS.register(blockName, blockSupplier);
        MoreMaterialsItems.ITEMS.register(blockName, () -> new BlockItem(block.get(), itemProperties));
        return block;
    }
}
