"""

Reception de 3 nombre en entrée, si au moins 2 de ces nombre sont egaux, le programme renvoie ce nombre.
Auteur David VILAJSOANA
Date : 05/09/2021

"""

nombre_a = int(input())
nombre_b = int(input())
nombre_c = int(input())

if nombre_a == nombre_b:
    print(nombre_a)
elif nombre_a == nombre_c:
    print(nombre_a)
elif nombre_b == nombre_c:
    print(nombre_b)