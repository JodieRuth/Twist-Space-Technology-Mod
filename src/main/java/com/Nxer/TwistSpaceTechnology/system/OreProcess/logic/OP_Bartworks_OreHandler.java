package com.Nxer.TwistSpaceTechnology.system.OreProcess.logic;

import static com.Nxer.TwistSpaceTechnology.system.OreProcess.logic.OP_Values.OreProcessRecipeDuration;
import static com.Nxer.TwistSpaceTechnology.system.OreProcess.logic.OP_Values.OreProcessRecipeEUt;
import static com.Nxer.TwistSpaceTechnology.util.Utils.setStackSize;
import static gregtech.api.enums.OrePrefixes.dust;
import static gregtech.api.enums.OrePrefixes.gem;
import static gregtech.api.enums.OrePrefixes.gemExquisite;
import static gregtech.api.enums.OrePrefixes.gemFlawless;
import static gregtech.api.enums.OrePrefixes.ore;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.Nxer.TwistSpaceTechnology.common.recipeMap.GTCMRecipe;
import com.github.bartimaeusnek.bartworks.system.material.Werkstoff;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;

public class OP_Bartworks_OreHandler {

    public static final OP_Bartworks_OreHandler instance = new OP_Bartworks_OreHandler();

    public void processBWOreRecipes() {
        for (Werkstoff werkstoff : Werkstoff.werkstoffHashSet) {
            if (!werkstoff.hasItemType(ore)) continue;
            ArrayList<ItemStack> outputs = new ArrayList<>();
            // basic output
            outputs.add(werkstoff.get(dust, 4));

            // gem output
            if (werkstoff.hasItemType(gem)) {
                if (werkstoff.hasItemType(gemExquisite)) {
                    outputs.add(werkstoff.get(gemExquisite, 1));
                    outputs.add(werkstoff.get(gemFlawless, 2));
                    outputs.add(werkstoff.get(gem, 2));
                } else {
                    outputs.add(werkstoff.get(gem, 4));
                }
            }

            // byproducts
            if (werkstoff.getNoOfByProducts() >= 1) {
                if (werkstoff.getNoOfByProducts() == 1) {
                    outputs.add(setStackSize(werkstoff.getOreByProduct(0, dust), 3));
                } else {
                    for (int i = 0; i < werkstoff.getNoOfByProducts(); i++) {
                        outputs.add(setStackSize(werkstoff.getOreByProduct(i, dust), 2));
                    }
                }
            } else {
                outputs.add(werkstoff.get(dust, 3));
            }

            // generate recipes
            GT_Values.RA.stdBuilder()
                .itemInputs(werkstoff.get(ore, 1))
                .itemOutputs(outputs.toArray(new ItemStack[] {}))
                .fluidInputs(Materials.Lubricant.getFluid(1))
                .noFluidOutputs()
                .eut(OreProcessRecipeEUt)
                .duration(OreProcessRecipeDuration)
                .addTo(GTCMRecipe.instance.OreProcessingRecipes);
        }
    }
}
