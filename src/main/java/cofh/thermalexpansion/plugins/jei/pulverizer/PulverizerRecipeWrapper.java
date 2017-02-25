package cofh.thermalexpansion.plugins.jei.pulverizer;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.machine.TilePulverizer;
import cofh.thermalexpansion.plugins.jei.Drawables;
import cofh.thermalexpansion.plugins.jei.JEIPluginTE;
import cofh.thermalexpansion.plugins.jei.RecipeUidsTE;
import cofh.thermalexpansion.util.crafting.PulverizerManager.ComparableItemStackPulverizer;
import cofh.thermalexpansion.util.crafting.PulverizerManager.RecipePulverizer;
import cofh.thermalfoundation.init.TFFluids;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PulverizerRecipeWrapper extends BlankRecipeWrapper {

	/* Recipe */
	final List<List<ItemStack>> inputs;
	final List<List<FluidStack>> inputFluids;
	final List<ItemStack> outputs;

	final int energy;
	final int chance;
	final String uId;

	/* Animation */
	final IDrawableAnimated fluid;
	final IDrawableAnimated progress;
	final IDrawableAnimated speed;
	final IDrawableAnimated energyMeter;

	public PulverizerRecipeWrapper(IGuiHelper guiHelper, RecipePulverizer recipe) {

		this(guiHelper, recipe, RecipeUidsTE.PULVERIZER);
	}

	public PulverizerRecipeWrapper(IGuiHelper guiHelper, RecipePulverizer recipe, String uIdIn) {

		uId = uIdIn;

		List<ItemStack> recipeInputs = new ArrayList<>();
		List<FluidStack> recipeInputFluids = new ArrayList<>();
		List<ItemStack> recipeOutputs = new ArrayList<>();

		if (ComparableItemStackPulverizer.getOreID(recipe.getInput()) != -1) {
			for (ItemStack ore : OreDictionary.getOres(ItemHelper.getOreName(recipe.getInput()))) {
				recipeInputs.add(ItemHelper.cloneStack(ore, recipe.getInput().stackSize));
			}
		} else {
			recipeInputs.add(recipe.getInput());
		}
		if (uId.equals(RecipeUidsTE.PULVERIZER_PETROTHEUM)) {
			recipeInputFluids.add(new FluidStack(TFFluids.fluidPetrotheum, 50));
			inputFluids = Collections.singletonList(recipeInputFluids);
			recipeOutputs.add(ItemHelper.cloneStack(recipe.getPrimaryOutput(), recipe.getPrimaryOutput().stackSize + 1));
			energy = recipe.getEnergy() * 3 / 2;
		} else {
			inputFluids = Collections.emptyList();
			recipeOutputs.add(recipe.getPrimaryOutput());
			energy = recipe.getEnergy();
		}
		if (recipe.getSecondaryOutput() != null) {
			recipeOutputs.add(recipe.getSecondaryOutput());
		}
		inputs = Collections.singletonList(recipeInputs);
		outputs = recipeOutputs;

		chance = recipe.getSecondaryOutputChance();

		IDrawableStatic fluidDrawable = Drawables.getDrawables(guiHelper).getProgress(1);
		IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper).getProgressFill(uId.equals(RecipeUidsTE.PULVERIZER_PETROTHEUM) ? 1 : 0);
		IDrawableStatic speedDrawable = Drawables.getDrawables(guiHelper).getSpeedFill(1);
		IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper).getEnergyFill();

		fluid = guiHelper.createAnimatedDrawable(fluidDrawable, energy / TilePulverizer.basePower, StartDirection.LEFT, true);
		progress = guiHelper.createAnimatedDrawable(progressDrawable, energy / TilePulverizer.basePower, StartDirection.LEFT, false);
		speed = guiHelper.createAnimatedDrawable(speedDrawable, 1000, StartDirection.TOP, true);
		energyMeter = guiHelper.createAnimatedDrawable(energyDrawable, 1000, StartDirection.TOP, true);
	}

	public String getUid() {

		return uId;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setInputLists(FluidStack.class, inputFluids);
		ingredients.setOutputs(ItemStack.class, outputs);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		if (uId.equals(RecipeUidsTE.PULVERIZER_PETROTHEUM)) {
			JEIPluginTE.drawFluid(69, 23, inputFluids.get(0).get(0), 24, 16);
			fluid.draw(minecraft, 69, 23);
		}
		progress.draw(minecraft, 69, 23);
		speed.draw(minecraft, 43, 33);
		energyMeter.draw(minecraft, 2, 8);
	}

	@Nullable
	public List<String> getTooltipStrings(int mouseX, int mouseY) {

		List<String> tooltip = new ArrayList<>();

		if (mouseX > 2 && mouseX < 15 && mouseY > 8 && mouseY < 49) {
			tooltip.add(StringHelper.localize("info.cofh.energy") + ": " + energy + " RF");
		}
		return tooltip;
	}

}
