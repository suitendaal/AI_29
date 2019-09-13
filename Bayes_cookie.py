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
	hypos = ["Bowl1", "Bowl2"]
	priors = [0.5, 0.5]
	obs = ["chocolate", "vanilla"]
	# e.g. likelihood[0][1] corresponds to the likehood of Bowl1 and vanilla, or 35/50
	likelihood = [[15/50, 35/50], [30/50, 20/50]]
	b = Bayes(hypos, priors, obs, likelihood)
	l = b.likelihood("chocolate", "Bowl1")
	print("likelihood(chocolate, Bowl1) = %s " % l)
	n_c = b.norm_constant("vanilla")
	print("normalizing constant for vanilla: %s" % n_c)
	p_1 = b.single_posterior_update("vanilla", [0.5, 0.5])
	print(f"vanilla - posterior: [bowl1: {p_1[0]:0.3f} bowl2: {p_1[1]:0.3f}]")

	p_2 = b.compute_posterior(["chocolate", "vanilla"])
	print(f"chocolate, vanilla - posterior: [bowl1: {p_2[0]:0.3f} bowl2: {p_2[1]:0.3f}]")