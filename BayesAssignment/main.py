from Bayes import Bayes
import numpy as np

if __name__ == '__main__':
    hypos = ["Bowl1", "Bowl2"]
    priors = [0.5, 0.5]
    obs = ["chocolate", "vanilla"]

    # e.g. likelihood[0][1] corresponds to the likehood of Bowl1 and vanilla, or 35/50
    likeli = [[15 / 50, 35 / 50], [30 / 50, 20 / 50]]

    hyposArcher = ["Beginner", "Intermediate", "Advanced", "Expert"]
    priorsArcher = [0.25, 0.25, 0.25, 0.25]
    obsArcher = ["Yellow", "Red", "Blue", "Black", "White"]
    likeliArcher = [[0.05, 0.1, 0.4, 0.25, 0.2],[0.1, 0.2, 0.4, 0.2, 0.1],[0.2, 0.4, 0.25, 0.1, 0.05],[0.3, 0.5, 0.125, 0.05, 0.025]]

    b = Bayes(hypos, priors, obs, likeli)
    l = b.likelihood("chocolate", "Bowl1")
    print("likelihood(chocolate, Bowl1) = %s " % l)
    n_c = b.norm_constant("vanilla")
    print("normalizing constant for vanilla: %s" % n_c)
    p_1 = b.single_posterior_update("vanilla", priors)
    print("vanilla - posterior: %s" % p_1)
    p_2 = b.compute_posterior(["chocolate", "vanilla"])
    print("chocolate, vanilla - posterior: %s" % p_2)


    single_post_prob_choc = b.single_posterior_update("chocolate", priors);
    print("Posterior probabilities for chocolate is: %s" % single_post_prob_choc)
    single_post_prob_vani = b.single_posterior_update("vanilla", priors);
    print("Posterior probabilities for vanilla is: %s" % single_post_prob_vani)

    double_post_prob_choc_choc = b.compute_posterior(["chocolate", "chocolate"])
    print("Posterior probabilities for double chocolate is: %s" % double_post_prob_choc_choc)
    double_post_prob_choc_vani = b.compute_posterior(["chocolate", "vanilla"])
    print("Posterior probabilities for chocolate -> vanilla is: %s" % double_post_prob_choc_vani)
    double_post_prob_vani_choc = b.compute_posterior(["vanilla", "chocolate"])
    print("Posterior probabilities for vanilla -> chocolate is: %s" % double_post_prob_vani_choc)
    double_post_prob_vani_vani = b.compute_posterior(["vanilla", "vanilla"])
    print("Posterior probabilities for double vanilla is: %s" % double_post_prob_vani_vani)
    double_post_prob_choc5 = b.compute_posterior(["chocolate", "chocolate", "chocolate", "chocolate", "chocolate"])
    print("Posterior probabilities for five chocolate is: %s" % double_post_prob_choc5)
    double_post_prob_vani5 = b.compute_posterior(["vanilla", "vanilla", "vanilla", "vanilla", "vanilla"])
    print("Posterior probabilities for five vanilla is: %s" % double_post_prob_vani5)

    a = Bayes(hyposArcher, priorsArcher, obsArcher, likeliArcher)

    print()
    print("Test environment ended")
    print(); print(); print();
    print("Deliverables assignment 1:")
    print(b.single_posterior_update("vanilla", priors)[0])
    print(b.compute_posterior(["vanilla", "chocolate"])[1])
    print(a.compute_posterior(["Yellow", "White", "Blue", "Red", "Red", "Blue"])[1])
    print(hyposArcher[np.argmax(a.compute_posterior(["Yellow", "White", "Blue", "Red", "Red", "Blue"]))])
