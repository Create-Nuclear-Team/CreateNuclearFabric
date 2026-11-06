package net.nuclearteam.createnuclear.api.multiblock;

import net.nuclearteam.createnuclear.foundation.utility.CreateNuclearLang;

public enum TypeMultiBlock {
    REACTOR_T1(5),
    REACTOR_T2(7),
    REACTOR_T3(9)
    ;

    private final int size;
    private final String name;

    TypeMultiBlock(int size) {
        this.size = size;
        this.name = CreateNuclearLang.asId(name());
    }

    public int getSize() {
        return this.size;
    }

    public String getName() {
        return this.name;
    }
}
