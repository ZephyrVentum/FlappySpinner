/*
 * Copyright (c) 2014. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zephyr.ventum.interfaces;

/**
 * Game events that are platform specific (i.e. submitting a score or displaying an ad inn an
 * Android app is different than doing the same in a desktop app).
 */
public interface GameEventListener {
    /**
     * Displays an ad
     */
    public void displayAd();

    /**
     * Hides an ad
     */
    public void hideAd();

    /**
     * Hides an ad
     */
    public void changeBackgroundColor(String color);

    /**
     * Displays the scores leaderboard
     */
    public void displayLeaderboard();

    /**
     * Displays the game's achievements
     */
    public void displayAchievements();

    /**
     * Shares the game's website
     */
    public void share();
}
