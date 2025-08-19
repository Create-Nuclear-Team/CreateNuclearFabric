package net.nuclearteam.createnuclear.api.multiblock;

import lib.multiblock.test.impl.IMultiBlockPattern;

public record BlockPattern<T>(String id, T data, IMultiBlockPattern structure) {}