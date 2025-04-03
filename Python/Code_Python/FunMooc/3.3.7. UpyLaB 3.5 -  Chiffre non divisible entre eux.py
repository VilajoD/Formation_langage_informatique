"""

Reception de 2 nombres en entrée. Test s'il sont diviseur l'un de l'autre.
    S'il sont deiviseur --> False
    Sinon --> True
Auteur David VILAJSOANA
Date : 05/09/2021

"""

nombre_1 = int(input())
nombre_2 = int(input())

if (nombre_1 % nombre_2) == 0 or (nombre_2 % nombre_1) == 0:
    print(False)
else:
    print(True)