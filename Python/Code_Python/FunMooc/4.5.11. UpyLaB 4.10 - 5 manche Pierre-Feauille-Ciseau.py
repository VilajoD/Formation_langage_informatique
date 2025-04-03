import random

PIERRE = 0
FEUILLE = 1
CISEAUX = 2


def manche(joueur,coup_ordi):


    if (joueur == 0 and coup_ordi == 2) or (joueur == 1 and coup_ordi == 0) or (joueur == 2 and coup_ordi == 1):
        res = "gagne"
    elif (joueur == 0 and coup_ordi == 0) or (joueur == 1 and coup_ordi == 1) or (joueur == 2 and coup_ordi == 2):
        res = "egal"
    else:
        res = "perd"

    return(res)



def calcul_point(resultat):
    """
    Calcul le nombre de point en fonction du resultat en entrée.
    :param resultat : Resultat de la manche
    :return: Nombre de point
        - +1 si joueur gagne
        - -1 si joueur perd
        - 0 si egalité
    """
    points = 0

    if resultat == "gagne":
        points += 1
    elif resultat == "perd":
        points -= 1

    return(points)



def conversion_coup(coup_chiffre):
    """

    :param coup_chiffre: Coup avec son code chiffre
    :return: Le coup en toute lettre
    """

    if coup_chiffre == PIERRE:
        coup_lettre = "Pierre"
    elif coup_chiffre == FEUILLE:
        coup_lettre = "Feuille"
    elif coup_chiffre == CISEAUX:
        coup_lettre = "Ciseaux"
    else:
        coup_lettre = "Erreur"

    return(coup_lettre)



s = int(input())
random.seed(s)
point = 0

for i in range(5):
    coup_joueur = int(input())
    coup_joueur_lettre = conversion_coup(coup_joueur)

    coup_ordi = random.randint(0, 2)
    coup_ordi_lettre = conversion_coup(coup_ordi)

    resultat = manche(coup_joueur, coup_ordi)
    point += calcul_point(resultat)

    if resultat == "gagne":
        print(coup_ordi_lettre, "est battu par", coup_joueur_lettre, ":", point)
    elif resultat == "perd":
        print(coup_ordi_lettre, "bat", coup_joueur_lettre, ":", point)
    elif resultat == "egal":
        print(coup_ordi_lettre, "annule", coup_joueur_lettre, ":", point)


if point > 0:
    print("Gagné")
elif point == 0:
    print("Nul")
elif point < 0:
    print("Perdu")