"""

Imprime la moyenne géométrique (la racine carrée du produit de a par b)
de deux nombres positifs a et b de type float lus en entrée.
Si au moins un de ces nombres est strictement négatif, le programme imprime le texte « Erreur ».

Auteur David VILAJSOANA
Date : 05/09/2021

"""

nombre_1 = float(input())
nombre_2 = float(input())

if nombre_1 < 0 or nombre_2 < 0:
    print("Erreur")
else:
    resultat = (nombre_1*nombre_2)**0.5
    print(resultat)