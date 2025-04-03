"""
Fonction catalan(n), où n est un nombre entier positif ou nul, qui renvoie la valeur du  n-ième nombre de Catalan
"""

def catalan(n):
    def factorielle(x):
        facto=x
        for i in range(1,x):
            facto *= (x-i)
        return (facto)
    if n == 0:
        Cn=1
    else:
        Cn=(factorielle(2*n))/((factorielle((n+1)))*factorielle(n))

    return(int(Cn))


print(catalan(3))