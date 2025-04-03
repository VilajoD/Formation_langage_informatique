"""
fonction liste_des_mots qui reçoit en paramètre le nom d’un fichier texte, que la fonction doit ouvrir,
et qui renvoie la liste des mots contenus dans le fichier.

Consignes
On peut supposer que le fichier est présent et dans le bon format en encodage UTF-8.

Les mots dans la liste seront écrits en minuscule et triés dans l’ordre donné par la codification UTF-8
(en utilisant la méthode sort par exemple), les accents n’étant pas gérés de façon spécifique (« a » et « à »
sont deux mots différents).

Un même mot ne peut pas se trouver deux fois dans la liste.

Dans le fichier source, les mots peuvent être séparés par des caractères blancs habituels (caractère espace,
tabulation, passage à la ligne), ou par n’importe quel caractère parmi les suivants :

- ' " ? ! : ; . , * = ( ) 1 2 3 4 5 6 7 8 9 0
"""

import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")

def liste_des_mots(texte):
    liste_mots=[]
    separateur =["-", "'", '"', '?', '!', ':', ';', '.', ',', '*', '=', '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0']
    for ligne in open(texte, encoding= 'UTF-8'):
        for sep in separateur:  # enlever les séparateur des lignes lu.
            ligne = ligne.replace(sep, ' ')
        mots = list(ligne.split())  # séparer la ligne en liste de mots
        for m in mots:
            if m.lower() not in liste_mots and m.lower() not in separateur:
                liste_mots.append(m.lower())
    liste_mots.sort() # trier les mots dans l'ordre alphabetique
    return(liste_mots)

print(liste_des_mots('texte_Zola.txt'))