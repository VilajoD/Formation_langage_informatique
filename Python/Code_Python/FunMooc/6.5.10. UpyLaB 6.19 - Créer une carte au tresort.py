"""
Écrire une fonction create_map(size, trapsNbr) qui reçoit deux entiers en paramètres, size,
compris entre 2 et 100, et trapsNbr, de valeur strictement inférieure à size x size, et qui
retourne un dictionnaire implémentant comme dans l’exemple précédent une carte de taille size et
dans laquelle figurent trapsNbr cases contenant un piège (modélisé par la valeur -1) et une case
contenant un trésor (modélisé par la valeur 1). L’emplacement de ces cases sera aléatoire.

Écrire une fonction play_game(map_size, treasure_map) qui reçoit un entier et une carte de taille
map_size x map_size, telle que celles obtenues grâce à la fonction create_map, et qui demande à
l’utilisateur d’entrer les coordonnées d’une case, jusqu’à tomber sur une case occupée. Si l’utilisateur
a trouvé le trésor, la fonction retourne la valeur True, sinon l’utilisateur est tombé sur un piège et la
fonction retourne False.
"""
from random import randint

def create_map(size, trapsNbr):
    carte = {}
    for i in range(trapsNbr):
        ok = False
        while ok == False :
            x = randint(1,size)
            y = randint(1,size)
            if (x,y) not in carte:
                carte[(x,y)] = -1
                ok = True
    ok = False
    while ok == False :
        x = randint(1, size)
        y = randint(1, size)
        if (x, y) not in carte:
            carte[(x, y)] = 1
            ok = True

    return carte

def play_game(map_size, treasure_map):
    trouver = 0
    while trouver == 0:
        saisi_joueur = input().split()
        if len(saisi_joueur) == 2:
            if saisi_joueur[0].isnumeric() and saisi_joueur[1].isnumeric():
                demande_joueur = (int(saisi_joueur[0]),int(saisi_joueur[1]))
                if int(demande_joueur[0]) <= map_size and int(demande_joueur[1]) <= map_size and int(demande_joueur[0]) > 0 and int(demande_joueur[1]) > 0:
                    if demande_joueur in treasure_map:
                        trouver = treasure_map[demande_joueur]

    if trouver == 1:
        resultat = True
    elif trouver == -1:
        resultat = False

    return resultat

print(play_game(5, {(3, 4): -1, (4, 1): 1, (2, 3): -1, (1, 5): -1}))