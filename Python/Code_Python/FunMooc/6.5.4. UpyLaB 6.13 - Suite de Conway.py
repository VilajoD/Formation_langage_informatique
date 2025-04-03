"""
Voici le début d’une suite logique inventée par John Horton Conway (et connue donc sous le nom de suite de Conway).

1
1 1
2 1
1 2 1 1
1 1 1 2 2 1
3 1 2 2 1 1
...
Chaque ligne, à partir de la deuxième, décrit la précédente :

la première ligne, 1, est formée de un “1”, d’où la deuxième ligne : 1 1 ;

la troisième ligne décrit la deuxième ligne, où l’on voit deux “1”, d’où 2 1 ;

la quatrième ligne décrit la troisième ligne, où l’on voit un “2” et un “1”, d’où 1 2 1 1 ;

et ainsi de suite.

Écrire une fonction next_line(line) qui reçoit une liste d’entiers, et qui retourne la liste correspondant
à la ligne suivante.
Notez que les valeurs de la liste reçue sont toujours entières, mais cette dernière peut ne pas correspondre
à une suite de Conway (par exemple [4,2] pourrait être donné).
"""

def next_line(line):
    nombre = 0
    quantite = 1
    resultat = []
    for n in line:
        if nombre == 0:
            resultat.clear()
            quantite = 1
            nombre = n
        elif n == nombre:
            quantite += 1
        else:
            resultat.extend([quantite,nombre])
            quantite = 1
            nombre = n
    if line == []:
        resultat.append(1)
    else:
        resultat.extend([quantite, nombre])

    return resultat

print(next_line([3, 1, 2, 2, 1, 1]))