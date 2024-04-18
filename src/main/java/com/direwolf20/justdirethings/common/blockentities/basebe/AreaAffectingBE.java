package com.direwolf20.justdirethings.common.blockentities.basebe;

import com.direwolf20.justdirethings.util.interfacehelpers.AreaAffectingData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

public interface AreaAffectingBE {
    double maxRadius = 5;
    int maxOffset = 9;

    BlockEntity getBlockEntity();

    AreaAffectingData getAreaAffectingData();

    default AABB getAABBOffsetOnly(BlockPos relativePos) {
        double xOffset = getAreaAffectingData().xOffset;
        double yOffset = getAreaAffectingData().yOffset;
        double zOffset = getAreaAffectingData().zOffset;
        if (getAreaAffectingData().xRadius != Math.floor(getAreaAffectingData().xRadius))
            xOffset = xOffset + 0.75;
        if (getAreaAffectingData().yRadius != Math.floor(getAreaAffectingData().yRadius))
            yOffset = yOffset + 0.75;
        if (getAreaAffectingData().zRadius != Math.floor(getAreaAffectingData().zRadius))
            zOffset = zOffset + 0.75;
        return new AABB(relativePos.getX() + xOffset, relativePos.getY() + yOffset, relativePos.getZ() + zOffset, relativePos.getX() + xOffset + (getAreaAffectingData().xRadius != Math.floor(getAreaAffectingData().xRadius) ? 0.5 : 1), relativePos.getY() + yOffset + (getAreaAffectingData().yRadius != Math.floor(getAreaAffectingData().yRadius) ? 0.5 : 1), relativePos.getZ() + zOffset + (getAreaAffectingData().zRadius != Math.floor(getAreaAffectingData().zRadius) ? 0.5 : 1));
    }

    default AABB getAABB(BlockPos relativePos) {
        if (getAreaAffectingData().area == null) {
            double xOffset = getAreaAffectingData().xOffset;
            double yOffset = getAreaAffectingData().yOffset;
            double zOffset = getAreaAffectingData().zOffset;
            if (getAreaAffectingData().xRadius != Math.floor(getAreaAffectingData().xRadius))
                xOffset = xOffset + 0.5;
            if (getAreaAffectingData().yRadius != Math.floor(getAreaAffectingData().yRadius))
                yOffset = yOffset + 0.5;
            if (getAreaAffectingData().zRadius != Math.floor(getAreaAffectingData().zRadius))
                zOffset = zOffset + 0.5;
            getAreaAffectingData().area = new AABB(relativePos.getX() + xOffset, relativePos.getY() + yOffset, relativePos.getZ() + zOffset, relativePos.getX() + xOffset + 1, relativePos.getY() + yOffset + 1, relativePos.getZ() + zOffset + 1).inflate(getAreaAffectingData().xRadius, getAreaAffectingData().yRadius, getAreaAffectingData().zRadius);
        }
        return getAreaAffectingData().area;
    }

    default void setAreaSettings(double x, double y, double z, int xo, int yo, int zo, boolean renderArea) {
        getAreaAffectingData().xRadius = Math.max(0, Math.min(x, maxRadius));
        getAreaAffectingData().yRadius = Math.max(0, Math.min(y, maxRadius));
        getAreaAffectingData().zRadius = Math.max(0, Math.min(z, maxRadius));
        getAreaAffectingData().xOffset = Math.max(-maxOffset, Math.min(xo, maxOffset));
        getAreaAffectingData().yOffset = Math.max(-maxOffset, Math.min(yo, maxOffset));
        getAreaAffectingData().zOffset = Math.max(-maxOffset, Math.min(zo, maxOffset));
        getAreaAffectingData().renderArea = renderArea;
        getAreaAffectingData().area = null;
        if (getBlockEntity() instanceof BaseMachineBE baseMachineBE)
            baseMachineBE.markDirtyClient();
    }

    default void saveAreaSettings(CompoundTag tag) {
        tag.putDouble("xRadiusDouble", getAreaAffectingData().xRadius);
        tag.putDouble("yRadiusDouble", getAreaAffectingData().yRadius);
        tag.putDouble("zRadiusDouble", getAreaAffectingData().zRadius);
        tag.putInt("xOffset", getAreaAffectingData().xOffset);
        tag.putInt("yOffset", getAreaAffectingData().yOffset);
        tag.putInt("zOffset", getAreaAffectingData().zOffset);
        tag.putBoolean("renderArea", getAreaAffectingData().renderArea);
    }

    default void loadAreaSettings(CompoundTag tag) {
        if (tag.contains("xRadius")) { //TODO Remove in 1.21
            getAreaAffectingData().xRadius = tag.getInt("xRadius");
            getAreaAffectingData().yRadius = tag.getInt("yRadius");
            getAreaAffectingData().zRadius = tag.getInt("zRadius");
            getAreaAffectingData().xOffset = tag.getInt("xOffset");
            getAreaAffectingData().yOffset = tag.getInt("yOffset");
            getAreaAffectingData().zOffset = tag.getInt("zOffset");
            getAreaAffectingData().renderArea = tag.getBoolean("renderArea");
        }
        if (tag.contains("xRadiusDouble")) { //Assume all the others are there too...
            getAreaAffectingData().xRadius = tag.getDouble("xRadiusDouble");
            getAreaAffectingData().yRadius = tag.getDouble("yRadiusDouble");
            getAreaAffectingData().zRadius = tag.getDouble("zRadiusDouble");
            getAreaAffectingData().xOffset = tag.getInt("xOffset");
            getAreaAffectingData().yOffset = tag.getInt("yOffset");
            getAreaAffectingData().zOffset = tag.getInt("zOffset");
            getAreaAffectingData().renderArea = tag.getBoolean("renderArea");
        }
    }
}
