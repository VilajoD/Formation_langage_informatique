"""
Programme qui vérifie si les vent enregistrer poviennet d'une tempete, d'un cyclone ou de rien de bien mechant

En entrée : Liste de valeur des vent enregistré
        [75, 200, 230, 260, 100, 80, 50, 45, 180, 100, 200, 63, 64, 65,118, 119, 153, 154, 280, 345]

En sortie : S'il s'agit d'une tempete, d'un cyclone (de quelle type) ou rien

"""


def categorie(vitesse):
    if vitesse >= 119 and vitesse <= 153:
        categorie = 1

    elif vitesse >= 154 and vitesse <= 177:
        categorie = 2

    elif vitesse >= 178 and vitesse <= 209:
        categorie = 3

    elif vitesse >= 210 and vitesse <= 250:
        categorie = 4

    elif vitesse > 250:
        categorie = 5

    return (categorie)


vent = [75, 200, 230, 260, 100, 80, 50, 45, 180, 100, 200, 63, 64, 65,118, 119, 153, 154, 280, 345]

for i in range(len(vent)):
    if vent[i] < 64 :
        print("Pour l'année " , (2000+i) , "pas de tempete ni dde cyclone")
    elif vent[i] >= 64 and vent[i] <= 118:
        print("Pour l'année " , (2000+i) , "il a y eu une tempete")
    elif vent[i] >= 119:
        print("Pour l'année ", (2000+i), "il a y eu un cyclone de type ", categorie(vent[i]))
