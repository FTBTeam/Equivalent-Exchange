package net.creeperhost.equivalentexchange.api.recipe;

import net.minecraft.world.level.block.state.BlockState;

//TODO this should also be a recipe json at some point
@Deprecated
public class InWorldTransmutation
{
    BlockState input;
    BlockState result;
    BlockState altResult;

    public InWorldTransmutation(BlockState input, BlockState result, BlockState altResult)
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

    public BlockState getAltResult()
    {
        return altResult;
    }
}
