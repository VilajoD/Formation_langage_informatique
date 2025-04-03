"""
On demande a l'utilisateur de renseigner le nombre de fois qu'il faut plier une feuille de papier pour avoir
une epaisseur egale a la distance terre-lune

Si bon resultat on lui renvoie "Bravo !" sinon "Mauvaise réponse." et on lui repose la question jusqu'a
la bonne reponse

Aureur : David VILAJOSANA
Date : 07/09/2021

"""


reponse_utilisateur = int(input("Combien de plis sont-ils nécessaires pour se rendre sur la Lune ? : "))

while reponse_utilisateur != 42:
    print("Mauvaise réponse.")
    reponse_utilisateur = int(input("Combien de plis sont-ils nécessaires pour se rendre sur la Lune ? : "))

print("Bravo !")