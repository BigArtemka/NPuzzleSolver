package model;

import java.io.IOException;
import java.util.*;

/**
 * @author Tatarin Esli Che
 */
public class Solver {

    public static Deque<State> search(Game game) throws IOException, ClassNotFoundException {
        State root = new State(game);
        List<State> close = new ArrayList<>();
        Deque<State> open = new LinkedList<>();
        open.add(root);
        while (!open.isEmpty()) {
            State state = getMinFState(open);
            if (state.isSolved()) {
                return solution(state);
            }
            open.remove(state);
            close.add(state);
            Deque<State> neighbors = getNeighbors(state);
            for (State neighbor : neighbors) {
                if (!close.contains(neighbor)) {
                    if (!open.contains(neighbor)) {
                        open.add(neighbor);
                    }
                }
            }
            }
        return null;
    }

    private static Deque<State> getNeighbors(State state) throws IOException, ClassNotFoundException {
        Deque<State> neigb = new LinkedList<>();
        if (state.getBlankPos() % state.getSize() > 0) {
            State state1 = state.cloneState();
            state1.swap(state1.getTiles()[state1.getBlankPos()], state1.getTiles()[state1.getBlankPos() - 1]);
            state1.setBlankPos(state1.getBlankPos() - 1);
            neigb.add(state1);
        }
        if (state.getBlankPos() % state.getSize() < state.getSize() - 1) {
            State state1 = state.cloneState();
            state1.swap(state1.getTiles()[state1.getBlankPos()], state1.getTiles()[state1.getBlankPos() + 1]);
            state1.setBlankPos(state1.getBlankPos() + 1);
            neigb.add(state1);
        }
        if (state.getBlankPos() >= state.getSize()) {
            State state1 = state.cloneState();
            state1.swap(state1.getTiles()[state1.getBlankPos()], state1.getTiles()[state1.getBlankPos() - state1.getSize()]);
            state1.setBlankPos(state1.getBlankPos() - state1.getSize());
            neigb.add(state1);
        }
        if (state.getBlankPos() < state.getSize() * (state.getSize() - 1)) {
            State state1 = state.cloneState();
            state1.swap(state1.getTiles()[state1.getBlankPos()], state1.getTiles()[state1.getBlankPos() + state1.getSize()]);
            state1.setBlankPos(state1.getBlankPos() + state1.getSize());
            neigb.add(state1);
        }
        return neigb;
        }

        private static State getMinFState(Deque<State> list) {
        State s = null;
        int min = Integer.MAX_VALUE;
        for (State state: list) {
            if (state.getF() < min) {
                min = state.getF();
                s = state;
            }
        }
        return s;
        }

        private static Deque<State> solution(State solve) {
         Deque<State> path = new LinkedList<>();
            State c = solve;
            while (c != null) {
                path.add(c);
                c = c.getParent();
            }
            return path;
        }

}
