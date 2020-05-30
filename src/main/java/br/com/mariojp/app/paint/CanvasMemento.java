package br.com.mariojp.app.paint;

import java.awt.Image;

public class CanvasMemento {
    private Image stateImage;

    public CanvasMemento(Image stateImage) {
        this.stateImage = stateImage;
    }

    public Image getState() {
        return stateImage;
    }
} 