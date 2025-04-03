"""
Filtre les mot avec une E et ne garde que ceux qui n'en ont pas. Puis fait le pourcentage de mot filtré.
"""

import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")

compteur_ecriture = 0
compteur_lecture = 0
fich_out=open('resultat.txt','w', encoding="utf-8")

for m in open('mots.txt', encoding="utf-8"):
    compteur_lecture += 1
    if 'e' not in m and 'E' not in m and 'é' not in m and 'è' not in m and 'ê' not in m and 'ë' not in m:
        fich_out.write(m)
        compteur_ecriture += 1

fich_out.close()
pourcentage = compteur_ecriture * 100 / compteur_lecture
print("lecture : ",compteur_lecture)
print("ecriture : ",compteur_ecriture)
print(pourcentage)



