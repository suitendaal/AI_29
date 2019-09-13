class Bayes:
	def __init__(self, hypos, priors, obs, likelihood):
		self.hypos = hypos
		self.priors = priors
		self.obs = obs
		self.ll = likelihood

	def likelihood(self, observ, hypo):
		return self.ll[self.hypos.index(hypo)][self.obs.index(observ)]

	def norm_constant(self, observ):
		return sum(self.priors[self.hypos.index(x)] * self.likelihood(observ, x) for x in self.hypos)

	def single_posterior_update(self, observ, priors):
		return [p * self.likelihood(observ, self.hypos[i]) / self.norm_constant(observ) for i, p in enumerate(priors)]

	def compute_posterior(self, observs):
		for obs in observs:
			self.priors = self.single_posterior_update(obs, self.priors)
		return self.priors

if __name__ == '__main__':
	hypos = ["Beginner", "Intermediate", "Advanced", "Expert"]
	priors = [0.25, 0.25, 0.25, 0.25]
	obs = ["yellow", "red", "blue", "black", "white"]
	# e.g. likelihood[0][1] corresponds to the likehood of Bowl1 and vanilla, or 35/50
	likelihood = [[0.05, 0.1, 0.4, 0.25, 0.2],
				  [0.1, 0.2, 0.4, 0.2, 0.1],
				  [0.2, 0.4, 0.25, 0.1, 0.05],
				  [0.3, 0.5, 0.125, 0.05, 0.025]]

	b = Bayes(hypos, priors, obs, likelihood)
	
	p_1 = b.compute_posterior(["yellow", "white", "blue", "red", "red", "blue"])
	for i in range(len(hypos)):
		print(f"{hypos[i]}: {p_1[i]:0.3f}")