package cofh.thermalexpansion.block;

import cofh.api.core.IInitializer;
import cofh.thermalexpansion.block.cache.BlockCache;
import cofh.thermalexpansion.block.cache.ItemBlockCache;
import cofh.thermalexpansion.block.cell.BlockCell;
import cofh.thermalexpansion.block.cell.ItemBlockCell;
import cofh.thermalexpansion.block.device.BlockDevice;
import cofh.thermalexpansion.block.device.ItemBlockDevice;
import cofh.thermalexpansion.block.dynamo.BlockDynamo;
import cofh.thermalexpansion.block.dynamo.ItemBlockDynamo;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.block.machine.ItemBlockMachine;
import cofh.thermalexpansion.block.simple.*;
import cofh.thermalexpansion.block.sponge.BlockSponge;
import cofh.thermalexpansion.block.sponge.ItemBlockSponge;
import cofh.thermalexpansion.block.strongbox.BlockStrongbox;
import cofh.thermalexpansion.block.strongbox.ItemBlockStrongbox;
import cofh.thermalexpansion.block.tank.BlockTank;
import cofh.thermalexpansion.block.tank.ItemBlockTank;
import cofh.thermalexpansion.block.workbench.BlockWorkbench;
import cofh.thermalexpansion.block.workbench.ItemBlockWorkbench;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class TEBlocks {

	private TEBlocks() {

	}

	public static ArrayList<IInitializer> blockList = new ArrayList<IInitializer>();

	public static void preInit() {

		blockMachine = (BlockMachine) addBlock(new BlockMachine());
		blockDevice = (BlockDevice) addBlock(new BlockDevice());
		blockDynamo = addBlock(new BlockDynamo());
		blockCell = addBlock(new BlockCell());
		blockTank = addBlock(new BlockTank());
		blockStrongbox = addBlock(new BlockStrongbox());
		blockCache = addBlock(new BlockCache());
		blockWorkbench = addBlock(new BlockWorkbench());
		blockFrame = addBlock(new BlockFrame());
		blockGlass = addBlock(new BlockGlass());
		blockRockwool = addBlock(new BlockRockwool());
		blockSponge = addBlock(new BlockSponge());

		blockAirSignal = new BlockAirSignal();
		blockAirLight = new BlockAirLight();
		blockAirForce = new BlockAirForce();
		blockAirBarrier = new BlockAirBarrier();

		GameRegistry.registerBlock(blockMachine, ItemBlockMachine.class, "Machine");
		GameRegistry.registerBlock(blockDevice, ItemBlockDevice.class, "Device");
		GameRegistry.registerBlock(blockDynamo, ItemBlockDynamo.class, "Dynamo");
		GameRegistry.registerBlock(blockCell, ItemBlockCell.class, "Cell");
		GameRegistry.registerBlock(blockTank, ItemBlockTank.class, "Tank");
		GameRegistry.registerBlock(blockStrongbox, ItemBlockStrongbox.class, "Strongbox");
		GameRegistry.registerBlock(blockCache, ItemBlockCache.class, "Cache");
		GameRegistry.registerBlock(blockWorkbench, ItemBlockWorkbench.class, "Workbench");
		GameRegistry.registerBlock(blockFrame, ItemBlockFrame.class, "Frame");
		GameRegistry.registerBlock(blockGlass, ItemBlockGlass.class, "Glass");
		GameRegistry.registerBlock(blockRockwool, ItemBlockRockwool.class, "Rockwool");
		GameRegistry.registerBlock(blockSponge, ItemBlockSponge.class, "Sponge");

		GameRegistry.registerBlock(blockAirSignal, null, "FakeAirSignal");
		GameRegistry.registerBlock(blockAirLight, null, "FakeAirLight");
		GameRegistry.registerBlock(blockAirForce, null, "FakeAirForce");
		GameRegistry.registerBlock(blockAirBarrier, null, "FakeAirBarrier");

		for (IInitializer initializer : blockList) {
			initializer.preInit();
		}

	}

	public static void initialize() {

		for (IInitializer initializer : blockList) {
			initializer.initialize();
		}
	}

	public static void postInit() {

		for (IInitializer initializer : blockList) {
			initializer.postInit();
		}
		blockList.clear();
	}

	public static Block addBlock(Block block) {

		blockList.add((IInitializer) block);
		return block;
	}

	public static BlockMachine blockMachine;
	public static BlockDevice blockDevice;
	public static Block blockDynamo;
	public static Block blockCell;
	public static Block blockTank;
	public static Block blockStrongbox;
	public static Block blockCache;
	public static Block blockWorkbench;
	public static Block blockFrame;
	public static Block blockGlass;
	public static Block blockRockwool;
	public static Block blockSponge;

	public static Block blockAirSignal;
	public static Block blockAirLight;
	public static Block blockAirForce;
	public static Block blockAirBarrier;

}
