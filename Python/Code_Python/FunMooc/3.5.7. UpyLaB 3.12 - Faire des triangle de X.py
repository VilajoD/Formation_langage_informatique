"""
En entrée se trouve un nombre. Le programme créer un triangle de X en fonction du nombre reçu.

Avec la donnée lue suivante : 6
Le résultat à imprimer vaudra :

XXXXXX
 XXXXX
  XXXX
   XXX
    XX
     X

Auteur : David VILAJOSANA
Date : 07/09/2021

"""

nombre = int(input())
blanc = 0

while nombre > 0:
    for j in range(blanc):
        print(" ",end = '')
    for j in range(nombre):
        print("X",end = '')
    print(end ='\n')
    nombre -= 1
    blanc += 1