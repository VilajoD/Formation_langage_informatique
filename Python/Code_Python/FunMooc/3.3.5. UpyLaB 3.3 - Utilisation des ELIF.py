"""

Reception de 3 nombres entiés. Resutat en sorti en fonction de la valeur de C.
Auteur David VILAJSOANA
Date : 05/09/2021

"""

a = int(input())
b = int(input())
c = int(input())

if c == 1:
    print(a+b)
elif c == 2:
    print(a-b)
elif c == 3:
    print(a*b)
elif c == 4:
    print((a**2)+(a*b))
else:
    print("Erreur")
