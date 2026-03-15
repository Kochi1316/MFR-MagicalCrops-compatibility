package portablejim.compatibility;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;

public class Plantable implements IFactoryPlantable {

    private final ItemSeeds seed;

    private static final Block MFR_FERTILE_SOIL =
            GameRegistry.findBlock("MineFactoryReloaded", "farmland");

    public Plantable(ItemSeeds seed) {
        this.seed = seed;
    }

    @Override
    public Item getSeed() {
        return seed;
    }

    @Override
    public boolean canBePlanted(ItemStack stack, boolean forFermenting) {
        return true;
    }

    @Override
    public ReplacementBlock getPlantedBlock(World world, int x, int y, int z, ItemStack stack) {
        return new ReplacementBlock(seed.getPlant(world, x, y, z));
    }

    private boolean isValidSoil(Block ground) {
        return ground == Blocks.farmland || ground == MFR_FERTILE_SOIL;
    }

    @Override
    public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack) {
        if (!world.isAirBlock(x, y, z)) {
            return false;
        }

        Block ground = world.getBlock(x, y - 1, z);
        return isValidSoil(ground);
    }

    @Override
    public void prePlant(World world, int x, int y, int z, ItemStack stack) {
        Block ground = world.getBlock(x, y - 1, z);

        if (ground == Blocks.grass || ground == Blocks.dirt) {
            world.setBlock(x, y - 1, z, Blocks.farmland);
        }
    }

    @Override
    public void postPlant(World world, int x, int y, int z, ItemStack stack) {
    }
}