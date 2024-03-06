package com.direwolf20.justdirethings.common.items.tools;

import com.direwolf20.justdirethings.common.items.tools.basetools.BaseSword;
import com.direwolf20.justdirethings.common.items.tools.utils.GooTier;
import com.direwolf20.justdirethings.common.items.tools.utils.ToolAbility;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FerricoreSword extends BaseSword {
    public FerricoreSword() {
        super(GooTier.FERRICORE, 3, -2.0F, new Item.Properties());
        registerAbility(ToolAbility.MOBSCANNER);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        scanFor(level, player, hand, ToolAbility.MOBSCANNER);
        return super.use(level, player, hand);
    }
}
