"""

Reception d'un nombre en entrée. Test de la parité du nombre. Si pair, renvoyer True
Auteur David VILAJSOANA
Date : 05/09/2021

"""

nombre = int(input())

if (nombre % 2) == 0:
    print(True)
else:
    print(False)