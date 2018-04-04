package com.rockingstar.modules.TicTacToe.models;

import com.rockingstar.engine.game.models.Player;
import javafx.scene.layout.Pane;

public class Cell {

    public Player _player;

    public Cell() {
        // doe hier wat
    }

    public Cell(Player player) {
        _player = player;
    }

    public Player getPlayer() {
        return _player;
    }

    public void setPlayer(Player player) {
        _player = player;
    }
}
