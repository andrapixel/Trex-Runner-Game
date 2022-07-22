package game.objects;

// an enum of all the states a Trex can be in
// by knowing the current state of the Trex, the game can be controlled accordingly
public enum TrexState {
        INITIAL_STATE,  // the state of the Trex at the beggining of the game, before the space bar is pressed
        RUNNING,
        JUMPING,
        DUCKING,
        DEAD
}
