"""
Programme qui lit :

la première lettre en majuscule du nom du polyèdre ("T", "C", "O", "D" ou "I"),

la longueur de l’arête du polyèdre,

et qui imprime le volume du polyèdre correspondant.

Si la lettre lue ne fait pas partie des cinq initiales, le programme imprime le message "Polyèdre non connu".

Auteur David VILAJSOANA
Date : 05/09/2021

"""

polyedre = input()
arete = float(input())
volume = 0.0

if polyedre == "T":
    volume = (2**0.5/12)*arete**3
    print(volume)

elif polyedre == "C":
    volume = arete**3
    print(volume)

elif polyedre == "O":
    volume = (2**0.5/3)*arete**3
    print(volume)

elif polyedre == "D":
    volume = ((15+7*5**0.5)/4)*arete**3
    print(volume)

elif polyedre == "I":
    volume = ((5*(3+5**0.5))/12)*arete**3
    print(volume)

else:
    print("Polyèdre non connu")
