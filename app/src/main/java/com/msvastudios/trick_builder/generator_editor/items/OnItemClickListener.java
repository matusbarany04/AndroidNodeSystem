package com.msvastudios.trick_builder.generator_editor.items;

import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.AlgorithmEntity;

public interface OnItemClickListener {
    void onItemClicked(AlgorithmEntity item);
}
