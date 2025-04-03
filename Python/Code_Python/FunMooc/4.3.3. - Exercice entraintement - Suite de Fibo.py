"""
Programme qui donne les valeur de la suite de fibo pour les 100 1ere valeur.

"""



def fibo(n):
    """calcule le n-ième nombre de Fibonacci, avec : n de type int et
    fibo(0) valant 0
    fibo(1) valant 1 et
    fibo(n+1) valant fibo(n-1) + fibo(n)
    si n < 0 : fibo(n) retourne None"""
    prec = 0
    prec2 = 0
    for i in range(n+1):
        if i == 0:
            pass
            res = 0
        elif i == 1:
            res = 1
            prec = res
        else:
            res = prec + prec2
            prec2 = prec
            prec = res

    return res

for i in range(100):
    print(fibo(i))