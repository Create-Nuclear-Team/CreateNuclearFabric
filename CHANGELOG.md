# Changelog

## [1.4.0]

### QOL (Quality of Life)
- [d3a7c0a0](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/d3a7c0a0), [642b695c](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/642b695c), [8878d72d](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/8878d72d) — Updated repository links, README and copyright/LICENSE. (Giovanni)

### Bug Fixes
- [d1adbd7f](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/d1adbd7f) — Fixed ingredient tag for `coal dust` in the steel mixing recipe. (Giovanni)
- [ab5646bb](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/ab5646bb), [6df188af](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/6df188af) — Fixed tags/ingredients for the graphene recipe and related adjustments. (Giovanni)
- [a269ee00](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/a269ee00) — Fixed access widener syntax for `Gui#renderHotbar`. (Giovanni)
- [2a6a519e](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/2a6a519e) — Fixed download/resolution for `com.jozufozu.flywheel:flywheel-fabric`. (Phenix-13)

### Additions
- [35cc85c2](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/35cc85c2) — Added `ReactorBluePrintScreen` and `CNGhostIngredientHandler` for drag-and-drop. (Giovanni)
- [a1ea4df3](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/a1ea4df3) — Added armor tags for anti-radiation equipment. (Giovanni)

### Technical / Codebase
- [2eab5e7b](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/2eab5e7b), [5661f8d5](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/5661f8d5), [fdd579ee](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/fdd579ee) — Refactored tags (`CNTags`) and ensured consistency between `CNBlocks` and tags. (Giovanni)
- [bdc95f81](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/bdc95f81), [5150f782](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/5150f782), [af6b7109](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/af6b7109), [8d63e258](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/8d63e258) — Refactored recipe-generation systems, adopted local `REGISTRATE`, and updated datagen. (Giovanni)
- [0a0de765](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/0a0de765), [d855be52](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/d855be52), [7d57bd68](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/7d57bd68) — Refactor/compat for JEI/REI and implemented `REIClientPlugin`. (Giovanni)
- [2becfd01](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/2becfd01), [aa7168f0](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/aa7168f0), [4e372b8c](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/4e372b8c), [9d307c9e](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/9d307c9e), [433c7e1b](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/433c7e1b), [da69e646](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/da69e646) — Refactored build (`build.gradle.kts`), dependency declarations and migrated to Kotlin DSL for Create V6 compatibility. (Giovanni)
- [117d39fc](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/117d39fc), [ab4097a6](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/ab4097a6), [c36e4e8d](https://github.com/Create-Nuclear-Team/CreateNuclearFabric/commit/c36e4e8d) — Code cleanup and removal of unused items. (Giovanni)

### Contributors
- Giovanni
- Phenix-13
- ArcEnStone

### Notes
- Version bump: **1.4.0** (from 1.3.0) aimed at Create V6 compatibility and updated dependencies.
