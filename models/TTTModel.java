/*
 * Enjun
 *
 * @version     1.0 Beta 1
 * @author      Rocking Stars
 * @copyright   2018, Enjun
 *
 * Copyright 2018 RockingStars

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rockingstar.modules.TicTacToe.models;

import com.rockingstar.engine.game.Player;
import com.rockingstar.modules.TicTacToe.views.TTTView;

/**
 * This class contains algorithmic methods, used for determining things like if there is a winner,
 * checking if a move is valid and clearing the board.
 * @author Rocking Stars
 * @since 1.0 Beta 1
 */
public class TTTModel {

    /**
     * The game board, made up of player objects.
     */
    private Player[][] _board = new Player[3][3];

    /**
     * The TicTacToe view
     */
    private TTTView _view;

    /**
     * The TTTModel constructor
     * @param view The TTT View
     */
    public TTTModel(TTTView view) {
        _view = view;
    }

    /**
     * Clears the game board
     */
    public void clearBoard() {
        _board = new Player[3][3];
        createCells();

        _view.setBoard(_board);
        _view.generateBoardVisual();
        _view.setIsFinished(false);
    }

    /**
     * Sets all cells to null
     */
    public void createCells() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                _board[i][j] = null;
    }

    /**
     * Returns the game board.
     * @return The game board
     */
    public Player[][] getBoard() {
        return _board;
    }

    /**
     * Returns whether or not the move is valid.
     * @param x The x position
     * @param y The y position
     * @return WHether or not the move is valid
     */
    public boolean isValidMove(int x, int y) {
        if (x > 2 || y > 2 || x < 0 || y < 0)
            return false;

        return _board[x][y] == null;
    }

    /**
     * Adds the given player object to the board at the given position
     * @param player The player object
     * @param x The x position
     * @param y The y position
     */
    public void setPlayerAtPosition(Player player, int x, int y) {
        _board[x][y] = player;
    }

    /**
     * Returns whether or not the specified player has won
     * @param player The player
     * @return Whether or not the player has won
     */
    public boolean hasWon(Player player) {
        for (int i = 0; i < 3; i++)
            if (_board[i][0] == player && _board[i][1] == player && _board[i][2] == player)
                return true;

        for (int j = 0; j < 3; j++)
            if (_board[0][j] == player && _board[1][j] == player && _board[2][j] == player)
                return true;

        if (_board[0][0] == player && _board[1][1] == player && _board[2][2] == player)
            return true;

        return _board[0][2] == player && _board[1][1] == player && _board[2][0] == player;
    }

    /**
     * Returns whether or not the board is full or not
     * @return Whether or not the board is full
     */
    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (_board[i][j] == null)
                    return false;

        return true;
    }
}
