"""
fonction prime_numbers qui reçoit comme paramètre un nombre entier nb et qui renvoie la liste
des nb premiers nombres premiers.

Si le paramètre n’est pas du type attendu, ou ne correspond pas à un nombre entier positif ou nul,
la fonction renvoie None.
"""

def prime_numbers(nbr):

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


    if type(nbr) == str  or nbr < 0:
        list_prem = None
    else:
        list_prem = []
        i = 0
        while len(list_prem) < nbr:
            if premier(i) == True:
                list_prem.append(i)
            i += 1

    return(list_prem)



cas = -2
print(prime_numbers(cas))
