"""
programme qui additionne des valeurs naturelles lues sur entrée et affiche le résultat.
La première donnée lue ne fait pas partie des valeurs à sommer. Elle détermine si la
liste contient un nombre déterminé à l’avance de valeurs à lire ou non :

    si cette valeur est un nombre positif ou nul, elle donne le nombre de valeurs à lire et à sommer ;

    si elle est égale à -1, cela signifie qu’elle est suivie d’une liste de données à lire qui
    sera terminée par le caractère "F" signifiant que la liste est terminée.

Auteur : David VILAJOSANA
Date : 07/09/2021

"""

nombre = input()
somme = 0

if int(nombre) < 0 :# Nous ne connaissont pas combien il y a nombre a sommer. Boucle jusqu'a trouver "F"
    nombre = input()
    while nombre != "F":
        somme += int(nombre)
        nombre = input()


else:
    for i in range(int(nombre)): # 1er chffire correspond au nombre de nombre qu'on doit sommer.
        nombre = input()
        somme += int(nombre)



print(somme)