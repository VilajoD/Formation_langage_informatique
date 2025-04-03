"""
fonction acrostiche qui reçoit en paramètre le nom d’un fichier et qui retourne la chaîne de
caractères formée par les premières lettres de chaque ligne du fichier.
"""

import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")

def acrostiche(fichier):
    res=''
    for ligne in open(fichier):
        lettre = list(enumerate(ligne))
        res = res + lettre[0][1]
    return(res)

print(acrostiche('Acrostiche.txt'))