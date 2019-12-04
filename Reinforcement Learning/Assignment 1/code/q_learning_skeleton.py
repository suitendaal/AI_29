import random
import numpy

NUM_EPISODES = 1000
MAX_EPISODE_LENGTH = 500

DEFAULT_DISCOUNT = 0.9
EPSILON = 0.05
LEARNINGRATE = 0.1


class QLearner:
    """
    Q-learning agent
    """
    def __init__(self, num_states, num_actions, discount=DEFAULT_DISCOUNT, learning_rate=LEARNINGRATE): # You can add more arguments if you want
        self.name = "agent1"
        self.num_states = num_states
        self.num_actions = num_actions
        self.discount = discount
        self.learning_rate = learning_rate
        self.Qtable = numpy.zeros((self.num_states, self.num_actions))

    def process_experience(self, state, action, next_state, reward, done): # You can add more arguments if you want
        """
        Update the Q-value based on the state, action, next state and reward.
        """
        if not done:
            self.Qtable[state][action] = (1 - LEARNINGRATE) * self.Qtable[state][action] + LEARNINGRATE * (
                    reward + self.discount * numpy.max(self.Qtable[next_state]))
        else:
            self.Qtable[state][action] = (1 - LEARNINGRATE) * self.Qtable[state][action] + LEARNINGRATE * reward

    def select_action(self, state):
        """
        Returns an action, selected based on the current state
        """
        if random.random() > EPSILON:
            # Choose a random action
            return random.randint(0, self.num_actions - 1)
        else:
            # Choose best action
            return numpy.argmax(self.Qtable[state])

    def report(self, grid_size_x, grid_size_y):
        """
        Function to print useful information, printed during the main loop
        """
        print("---")
        print("Qtable believe:")
        for col in range(grid_size_y):
            for row in range(grid_size_x):
                print(f"{['<', 'v', '>', '^'][numpy.argmax(self.Qtable[grid_size_x*col+row])]}", end="")
            print()
        print("---")
