import numpy as np
# goal, h1, h2
# P = [[[0.64, 0.2, 0.16]] * 4 +[[0.8, 0, 0.2]] * 4 + [[1, 0, 0]] * 4]
gamma = 0.9

def calcP():
    P = []
    for i in range(4):
        p = [0.2, round(0.8 * 0.2, 2)]
        p = p + [1 - sum(p)]
        P.append(p)
    for i in range(4):
        p = [0, 0.2]
        p = p + [1 - sum(p)]
        P.append(p)
    for i in range(4):
        P.append([0, 0, 1])
    return P


def calcD():
    D = []
    for i in range(4):
        d = []
        d.append(np.power(gamma, np.power(1 / 0.8, 3 - i)))
        d.append(np.power(gamma, np.power(1 / 0.8, 7 - i)))
        d.append(np.power(gamma, np.power(1 / 0.8, 11 - i)))
        D.append(d)
    for i in range(4, 8):
        d = []
        d.append(0)
        d.append(np.power(gamma, np.power(1 / 0.8, 7 - i)))
        d.append(np.power(gamma, np.power(1 / 0.8, 11 - i)))
        D.append(d)
    for i in range(8, 12):
        d = []
        d.append(0)
        d.append(0)
        d.append(np.power(gamma, np.power(1 / 0.8, 11 - i)))
        D.append(d)
    return D



def main():
    P = calcP()
    D = calcD()
    print(D)



if __name__ == '__main__':
    main()
