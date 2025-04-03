"""
Une roue avec 100 Barreau. Une noisette est posée sur un barreau. Un écureuil fait des sauts toujours identiques
d'un certain nombre de barreau. Nous voulons si un jour l'écureuil va arriver sur la noisette.
On arrête le programme si l'animal arrive sur la noisette ou s'il revient sur le barreau 0.

On print tous les barreaux parcourus, sauf le dernier s'il arrive sur la noisette puis le résultat :
- "Cible atteinte" si l’écureuil a trouvé la noisette,
- "Pas trouvée" si l’écureuil est revenu en position 0 sans trouver la noisette.

En entrée :
- Nombre de barreau que sauté par l'animal
- Emplacement de la noisette


Auteur : David VILAJOSANA
Date : 07/09/2021

"""


saut = int(input())
position_cible = int(input())
position_courante = 0
fin = False

while fin == False:
    position_courante = (position_courante + saut) % 100
    if position_courante == position_cible:
        fin = True
        print("Cible atteinte")

    elif position_courante == 0:
        fin = True
        print(position_courante)
        print("Pas trouvée")

    else:
        print(position_courante)