"""
Fonction belongs_to_file(word, filename) qui reçoit deux chaînes de caractères en paramètre.
La première correspond à un mot, et la deuxième au nom d’un fichier contenant une liste de mots,
chacun sur sa propre ligne. La fonction vérifie si le mot figure dans cette liste, et retourne True
si c’est bien le cas, False sinon.
"""
import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")

def belongs_to_file(word, filename):
    resultat = False
    print(filename)
    for mot in open(filename):
        if word.strip() == mot.strip():
            resultat = True

    return resultat

print(belongs_to_file("renard", "words.txt"))