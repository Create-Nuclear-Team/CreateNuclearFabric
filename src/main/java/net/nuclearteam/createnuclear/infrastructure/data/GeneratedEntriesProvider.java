package net.nuclearteam.createnuclear.infrastructure.data;

import io.github.fabricators_of_create.porting_lib.data.DatapackBuiltinEntriesProvider;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.nuclearteam.createnuclear.CreateNuclear;
import net.nuclearteam.createnuclear.infrastructure.worldgen.CNConfiguredFeatures;
import net.nuclearteam.createnuclear.infrastructure.worldgen.CNPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
public class GeneratedEntriesProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.CONFIGURED_FEATURE, CNConfiguredFeatures::boostrap)
        .add(Registries.PLACED_FEATURE, CNPlacedFeatures::boostrap)
    ;

    public GeneratedEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(CreateNuclear.MOD_ID));
    }

    @Override
    public String getName() {
        return "CreateNuclear Generated Registry Entries";
    }
}
