"""
Programme qui verifie si un nombre et un nombre premier

"""



def premier (n):
    divisible = False
    for i in range(n):
        if i == 0 or i == 1 :
            pass
        else :
            if  n % i == 0 :
                divisible = True

    if divisible == False and n != 0 and n != 1:
        prem = True
    else:
        prem = False

    return prem


x = int(input())

for i in range(x+1):
    if premier(i) == True:
        print(i)
