package cyano.electricadvantage.machines;

import cyano.electricadvantage.init.Power;
import cyano.poweradvantage.api.ConduitType;
import cyano.poweradvantage.conduitnetwork.ConduitRegistry;
import cyano.poweradvantage.init.Fluids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ElectricStillBlock extends ElectricMachineBlock{

	@Override
	public ElectricMachineTileEntity createNewTileEntity(World w, int m) {
		return new ElectricStillTileEntity();
	}

///// Overrides to make this a multi-type block /////
	
	/**
	 * This method is called whenever the block is placed into the world
	 */
	@Override
	public void onBlockAdded(World w, BlockPos coord, IBlockState state){
		super.onBlockAdded(w, coord, state);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, cyano.poweradvantage.init.Fluids.fluidConduit_general);
	}
	
	/**
	 * This method is called when the block is removed from the world by an entity.
	 */
	@Override
	public void onBlockDestroyedByPlayer(World w, BlockPos coord, IBlockState state){
		super.onBlockDestroyedByPlayer(w, coord, state);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, cyano.poweradvantage.init.Fluids.fluidConduit_general);
	}
	/**
	 * This method is called when the block is destroyed by an explosion.
	 */
	@Override
	public void onBlockDestroyedByExplosion(World w, BlockPos coord, Explosion boom){
		super.onBlockDestroyedByExplosion(w, coord, boom);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, Fluids.fluidConduit_general);
	}
	

	/**
	 * Determines whether this conduit is compatible with an adjacent one
	 * @param type The type of energy in the conduit
	 * @param state The blockstate
	 * @param blockFace The side through-which the energy is flowing
	 * @return true if this conduit can flow the given energy type through the given face, false 
	 * otherwise
	 */
	public boolean canAcceptType(IBlockState state, ConduitType type, EnumFacing blockFace){
		return canAcceptType(type,blockFace);
	}
	/**
	 * Determines whether this conduit is compatible with an adjacent one
	 * @param type The type of energy in the conduit
	 * @param blockFace The side through-which the energy is flowing
	 * @return true if this conduit can flow the given energy type through the given face, false 
	 * otherwise
	 */
	public boolean canAcceptType(ConduitType type, EnumFacing blockFace){
		return canAcceptType(type);
	}
	/**
	 * Determines whether this conduit is compatible with a type of energy through any side
	 * @param type The type of energy in the conduit
	 * @return true if this conduit can flow the given energy type through one or more of its block 
	 * faces, false otherwise
	 */
	public boolean canAcceptType(ConduitType type){
		return  ConduitType.areSameType(getType(), type) || ConduitType.areSameType(Fluids.fluidConduit_general, type);
	}

	private final ConduitType[] types = {Power.ELECTRIC_POWER, Fluids.fluidConduit_general};

	@Override
	public ConduitType[] getTypes(){
		return types;
	}/**
	 * Determines whether this block/entity should receive energy
	 * @return true if this block/entity should receive energy
	 */
	@Override
	public boolean isPowerSink(ConduitType p){
		return ConduitType.areSameType(Power.ELECTRIC_POWER,p);
	}
	/**
	 * Determines whether this block/entity can provide energy
	 * @return true if this block/entity can provide energy
	 */
	@Override
	public boolean isPowerSource(ConduitType p){
		return Fluids.isFluidType(p);
	}
	///// end multi-type overrides /////
	
}
