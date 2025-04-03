"""
fonction prime_odd_numbers(numbers) qui reçoit une liste de nombres et qui renvoie un couple
d’ensembles contenant respectivement les nombres premiers présents dans la liste et les nombres impairs.

Pour cela, nous vous demandons d’écrire au préalable deux fonctions annexes qui seront appelées dans
le corps de la fonction prime_odd_numbers :

la fonction even qui accepte un nombre entier en paramètre et renvoie l’ensemble des nombres naturels
pairs qui lui sont inférieurs ou égaux

la fonction prime_numbers qui accepte un nombre entier en paramètre et renvoie l’ensemble des nombres
premiers qui lui sont inférieurs ou égaux.
"""


def even(numbers):
    res = set()
    for i in range(numbers + 1):
        if i % 2 == 0:
            res.add(i)
    return (res)

def prime_numbers(numbers):
    def premier(n):
        divisible = False
        for i in range(n):
            if i == 0 or i == 1:
                pass
            else:
                if n % i == 0:
                    divisible = True

        if divisible == False and n != 0 and n != 1:
            prem = True
        else:
            prem = False
        return prem

    list_prem = set()
    i = 0
    nombre = 0
    while i < numbers+1:
        if premier(i) == True:
            list_prem.add(i)
        i += 1

    return (list_prem)


def prime_odd_numbers(numbers):
    def nombre_impaire(numbers):
        res = set()
        for i in numbers:
            if i%2 != 0:
                res.add(i)
        return(res)


    def nombre_premier(numbers):
        res= set()
        for n in numbers:
            divisible = False
            for i in range(2,n):
                if n % i == 0:
                    divisible = True
            if divisible == False and n != 1 and n != 0:
                res.add(n)
        return(res)


    liste_impaire = nombre_impaire(numbers)
    liste_prem = nombre_premier(numbers)
    liste_paire = even(max(numbers))
    liste_enss_prem = prime_numbers(61)

    print("liste_impaire : " , liste_impaire)
    print("liste_pre : " , liste_prem)
    print("liste_paire : " , liste_paire)
    print("liste_enss_prem : " , liste_enss_prem)
    return (liste_prem, liste_impaire)


nombre =[1, 2, 6, 5, 11, 9, 13, 14, 12, 15, 17, 18]

print(prime_odd_numbers(nombre))