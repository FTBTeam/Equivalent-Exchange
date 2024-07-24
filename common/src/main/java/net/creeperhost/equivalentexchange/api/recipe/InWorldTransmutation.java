package net.creeperhost.equivalentexchange.api.recipe;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.state.BlockState;

//TODO this should also be a recipe json at some point
@Deprecated
public class InWorldTransmutation
{
    BlockState input;
    BlockState result;
    @Nullable BlockState altResult;

    public InWorldTransmutation(BlockState input, BlockState result, @Nullable BlockState altResult)
    {
        this.input = input;
        this.result = result;
        this.altResult = altResult;
    }

    public BlockState getInput()
    {
        return input;
    }

    public BlockState getResult()
    {
        return result;
    }

    public @Nullable BlockState getAltResult()
    {
        return altResult;
    }
}
