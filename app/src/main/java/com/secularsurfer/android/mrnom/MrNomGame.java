package com.secularsurfer.android.mrnom;

import com.secularsurfer.android.framework.Screen;
import com.secularsurfer.android.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame {

    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
