package br.com.mariojp.app.paint;

import java.util.Stack;

public class CanvasCaretaker {
    private Stack<CanvasMemento> previous = new Stack<CanvasMemento>();
    private Stack<CanvasMemento> next = new Stack<CanvasMemento>();
    private CanvasMemento currentSnapshot;

    public CanvasMemento undo() {
        if (!previous.isEmpty()) {
            next.add(currentSnapshot);
            currentSnapshot = previous.pop();
        }
        return currentSnapshot;
    }

    public CanvasMemento redo() {
        if (!next.isEmpty()) {
            previous.add(currentSnapshot);
            currentSnapshot = next.pop();
        }
        return currentSnapshot;
    }

    public void doDraw(CanvasMemento snapshot) {
        if (!next.isEmpty()) {
            next.clear();
        }

        if (currentSnapshot != null) {
            previous.add(currentSnapshot);
        }

        currentSnapshot = snapshot;
    }
} 