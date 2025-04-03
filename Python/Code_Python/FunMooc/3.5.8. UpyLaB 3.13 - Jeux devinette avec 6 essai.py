"""
Un nombre aleatoire ene 0 et 100 est tiré. Le joueur a 6 essais pour retrouver le nombre.

À chaque tour, le joueur est invité à proposer un nombre et le programme doit donner une réponse parmi les suivantes :

« Trop grand » : si le nombre secret est plus petit que la proposition et qu’on n’est pas au maximum d’essais
« Trop petit » : si le nombre secret est plus grand que la proposition et qu’on n’est pas au maximum d’essais
« Gagné en n essai(s) ! » : si le nombre secret est trouvé
« Perdu ! Le secret était nombre » : si le joueur a utilisé six essais sans trouver le nombre secret.

Auteur : David VILAJOSANA
Date : 07/09/2021

"""

import random

NB_ESSAIS_MAX = 6
secret = random.randint(0, 100)
mess_sorti = ''
trouve = False
i=1

while i <= NB_ESSAIS_MAX and trouve == False :
    reponse = int(input())

    if reponse == secret:
        mess_sorti = "Gagné en " + str(i) + " essai(s) !"
        i = NB_ESSAIS_MAX - 1
        trouve = True

    elif reponse < secret:
        mess_sorti = "Trop petit"
        i += 1

    elif reponse > secret:
        mess_sorti = "Trop grand"
        i += 1

    if i > NB_ESSAIS_MAX and trouve == False:
        mess_sorti = "Perdu ! Le secret était " + str(secret)

    print(mess_sorti)
