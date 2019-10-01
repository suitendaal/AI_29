import numpy as np

class Bayes:
    def __init__(self, hypos, priors, obs, likeli):
        self.hypos = hypos
        self.priors = priors
        self.obs = obs
        self.likeli = likeli

    def likelihood(self, observation, hypothesis):
        return self.likeli[self.hypos.index(hypothesis)][self.obs.index(observation)]

    def norm_constant(self, observation):
        sum_observation = 0
        for i in range(len(self.hypos)):
            sum_observation += self.priors[i] * self.likelihood(observation, self.hypos[i])
        return round(sum_observation, 3)

    def single_posterior_update(self, observation, prior_probabilities):
        posterior_probs = []
        for h in self.hypos:
            posterior_probs.append(round(self.likelihood(observation, h)*prior_probabilities[self.hypos.index(h)]
                /self.norm_constant(observation),3))
        return posterior_probs

    def compute_posterior(self, observation):
        probabilities = [1]*len(self.priors)
        for i in range(len(observation)):
            iteration = self.single_posterior_update(observation[i], self.priors)
            probabilities = np.multiply(probabilities, iteration)
        probabilities = self.normalize(probabilities)
        return self.roundList3(probabilities)

    def normalize(self, list):
        som = sum(list)
        for i in range(len(list)):
            list[i] = list[i]/som
        return list

    def roundList3(self, list):
        for i in range(len(list)):
            list[i] = round(list[i],3)
        return list








