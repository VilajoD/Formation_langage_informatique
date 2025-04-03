"""
fonction nouveaux_heros dont le but consiste à remplacer les héros d'une histoire.

La fonction acceptera deux paramètres :
le premier sera une chaîne de caractères précisant le nom du fichier contenant l'histoire initiale ;
le deuxième sera une chaîne de caractères précisant le nom du fichier dans lequel sera
sauvegardée l'histoire modifiée comme précisé ci-dessous.

Dans l'histoire initiale, présente dans le fichier dont le nom est donné en premier argument,
trois protagonistes interviennent : Pierre, Paul et Jacqueline.
La fonction devra remplacer ces trois héros par, respectivement, Paul, Tom et Mathilde.

Le texte ainsi modifié sera alors stocké dans le fichier dont le nom est donné en deuxième argument.

Aucune autre modification ne sera apportée au texte initial.
"""
import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")

def nouveaux_heros(fichier_in,fichier_out):
    fic_out = open(fichier_out, 'w', encoding="UTF-8")
    for ligne in open(fichier_in, encoding="UTF-8"):
        ligne = ligne.replace('Paul','Tom')
        ligne = ligne.replace('Pierre', 'Paul')
        ligne = ligne.replace('Jacqueline', 'Mathilde')
        fic_out.write(ligne)
    fic_out.close()

nouveaux_heros('histoire.txt','reecriture.txt')