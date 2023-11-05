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
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.kurykat.morematerials.MoreMaterials;
import dev.kurykat.morematerials.MoreMaterialsConstants;
import dev.kurykat.morematerials.tags.MoreMaterialsItemTags;
import dev.kurykat.morematerials.tags.MoreMaterialsTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MoreMaterialsItems {
    private static final Registrate REGISTRATE = MoreMaterials.getRegistrate();

    static {
        REGISTRATE.creativeModeTab(() -> MoreMaterialsConstants.CREATIVE_TAB);
    }

    public static final ItemEntry<Item> RUBY =
            createGemItem("ruby", Item::new)
                    .register();
    public static final ItemEntry<Item> CELESLAR_INGOT =
            createIngotItem("celeslar", Item::new)
                    .properties(Item.Properties::fireResistant)
                    .register();

    private static <T extends Item> ItemBuilder<T, Registrate> createGemItem(String materialName, NonNullFunction<Item.Properties, T> itemFactory) {
        return createTaggedItem(materialName, itemFactory, MoreMaterialsTags.forgeItemTag("gems/" + materialName), MoreMaterialsItemTags.GEMS.tag)
                .model((context, provider) -> {
                    provider.generated(context::get, MoreMaterials.asResource("item/gems/" + materialName));
                });
    }

    private static <T extends Item> ItemBuilder<T, Registrate> createIngotItem(String materialName, NonNullFunction<Item.Properties, T> itemFactory) {
        return createTaggedItem(materialName + "_ingot", itemFactory, MoreMaterialsTags.forgeItemTag("ingots/" + materialName), MoreMaterialsItemTags.INGOTS.tag)
                .model((context, provider) -> {
                    provider.generated(context::get, MoreMaterials.asResource("item/ingots/" + materialName));
                });
    }

    @SafeVarargs
    private static <T extends Item> ItemBuilder<T, Registrate> createTaggedItem(String name, NonNullFunction<Item.Properties, T> itemFactory, TagKey<Item>... tags) {
        return createItem(name, itemFactory).tag(tags);
    }

    private static <T extends Item> ItemBuilder<T, Registrate> createItem(String name, NonNullFunction<Item.Properties, T> itemFactory) {
        return REGISTRATE.item(name, itemFactory);
    }

    public static void register() {
    }
}
