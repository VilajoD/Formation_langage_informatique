"""
Nous pouvons définir la distance entre deux mots de même longueur (c’est-à-dire ayant le même nombre de lettres)
mot_1 et mot_2 comme le nombre minimum de fois où il faut modifier une lettre de mot_1 pour obtenir mot_2,
sans changer leur place (distance de Hamming).

Par exemple, les mots « lire » et « bise » sont à une distance de 2, puisqu’il faut changer le “l” et le “r”
du mot « lire » pour obtenir « bise ».

Écrire une fonction distance_mots(mot_1, mot_2) qui retourne la distance entre deux mots.

Vous pouvez supposer que les deux mots sont de même longueur, et sont écrits sans accents.
"""

def distance_mots(mot_1, mot_2):
    distance = 0
    for i in range(len(mot_1)):
        if mot_1[i] != mot_2[i]:
            distance += 1

    return(distance)


print(distance_mots("chien", "niche"))