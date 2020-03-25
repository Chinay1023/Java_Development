package projectEuler.programs;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Integer.*;
import static java.util.stream.IntStream.range;

public final class p280 extends EulerSolution {
	public static void main(String[] args) {
		System.out.println(new p280().run());
	}

	// Model the problem as a Markov process, and solve using dynamic programming
	String run() {
		// Memoize the successors of each valid state
		int[][] successors = new int[State.ID_LIMIT][];
		for (var st : State.listAllStates()) {
			var suc = st.getSuccessors();
			var it = suc.iterator();
			int[] sucIds = range(0, suc.size()).map(i -> it.next().id).toArray();
			successors[st.id] = sucIds;
		}
		// Run the simulation
		double sum = 0;
		double[] probs = new double[State.ID_LIMIT]; // The current probability of being in each state
		probs[State.START_STATE.id] = 1;
		for (int i = 1; ; i++) {
			// Note: The done state has no outgoing neighbors,
			// so its probability disappears in the next iteration
			double[] nextProbs = new double[probs.length];
			for (int j = 0; j < probs.length; j++)
				if (probs[j] > 0) {
					int[] suc = successors[j];
					for (int k : suc) nextProbs[k] += probs[j] / suc.length;
				}
			double doneNow = nextProbs[State.DONE_STATE.id];
			// Note: Minimum completion is 44 steps break;
			if (i > 44 && doneNow < 1e-20) sum += doneNow * i;
			probs = nextProbs;
		}
	}

	/*
	 * Represents the global state of the system, including the ant and seeds. Immutable.
	 */
	private static final class State {
		/*-- Static members --*/
		// All valid state IDs are in the range [0, ID_LIMIT). Not every number in the range is a valid state.
		static final int ID_LIMIT = 51201;
		// Special states.
		static final State START_STATE = new State(false, 2, 2, new boolean[]{false, false, false, false, false, true, true, true, true, true, false});
		static final State DONE_STATE = new State(true, 0, 0, new boolean[]{true, true, true, true, true, false, false, false, false, false, false});
		// A number in the range [0, ID_LIMIT) such that each distinct state has a different ID.
		final int id;
		/*-- Instance members --*/
		// When the system is done, the ant's position is ignored. Thus there is only 1 done state, not 5.
		// Moreover when done, hasSeed must equal [T T T T T, F F F F F, F].
		private final boolean isDone;
		// The ant's position on the grid. Both numbers are in the range [0, 5).
		private final int antX;
		private final int antY; // 0 represents the top row, 4 is the bottom row.
		// Indices 0 to 4 are for the top row, 5 to 9 for the bottom row, and 10 for the ant.
		// Exactly 5 elements are true, and the other 6 are false.
		private final boolean[] hasSeed;

		@Contract(pure = true)
		State(boolean done, int x, int y, boolean[] seed) {
			isDone = done;
			antX = x;
			antY = y;
			hasSeed = seed;
			if (done) id = 25 * (1 << seed.length);
			else {
				var temp = range(0, seed.length).map(i -> (seed[i] ? 1 : 0) << i).reduce(0, (a, b) -> a | b);
				id = x + (y * 5) + (temp * 25);
			}
		}

		// Returns a set of all valid states.
		@NotNull
		static Set<State> listAllStates() {
			var result = new HashSet<State>();
			// Try all 2^11 ways for which cells (or ant) hold a seed
			for (int i = 0; i < (1 << 11); i++) {
				if (bitCount(i) != 5) continue; // Invalid state if not 5 things hold a seed
				// For all 5*5 possible ant positions
				for (int y = 0; y < 5; y++)
					for (int x = 0; x < 5; x++) {
						boolean[] seed = new boolean[11];
						for (int j = 0; j < seed.length; j++) seed[j] = ((i >>> j) & 1) != 0;
						result.add(new State(false, x, y, seed));
					}
			}
			result.add(DONE_STATE);
			return result;
		}

		// Returns a set (of size 0 to 4) containing this state's successors.
		// Remember that this state transitions to a successor with equal probability.
		@NotNull Set<State> getSuccessors() {
			HashSet<State> result = new HashSet<>();
			if (!isDone) {
				tryAddSuccessor(-1, 0, result);
				tryAddSuccessor(+1, 0, result);
				tryAddSuccessor(0, -1, result);
				tryAddSuccessor(0, +1, result);
			}
			return result;
		}

		private void tryAddSuccessor(int dx, int dy, Set<State> result) {
			int x = antX + dx;
			int y = antY + dy;
			if ((x < 0) || (x >= 5) || (y < 0) || (y >= 5)) return; // Ant moves off the grid
			boolean[] seed = hasSeed.clone();
			boolean done = false;
			if (!seed[10] && y == 4 && seed[5 + x]) { // Pick up seed
				seed[5 + x] = false;
				seed[10] = true;
			} else if (seed[10] && y == 0 && !seed[x]) { // Drop off seed
				seed[10] = false;
				seed[x] = true;
				done = seed[0] & seed[1] & seed[2] & seed[3] & seed[4];
			}
			result.add(new State(done, x, y, seed));
		}
	}
}