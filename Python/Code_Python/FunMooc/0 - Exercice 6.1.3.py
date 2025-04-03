import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")



# Ecercice 3 : faire un dictionnaire avec le nom de mes amis en clé et en valeur tout leur amis a eux

def construction_dict_amis(liste_duple):
    dico_ami = {}
    for mon_ami,son_ami in liste_duple:
        if mon_ami in dico_ami:
            dico_ami[mon_ami].add(son_ami)
        else :
            dico_ami[mon_ami] = {son_ami}

        if son_ami not in dico_ami:
            dico_ami[son_ami] = set()

    return dico_ami


liste = [('Quidam', 'Pierre'),
                        ('Thierry', 'Michelle'),
                        ('Thierry', 'Pierre')]

print(construction_dict_amis(liste))



## Ecercice 2 : Fonction histogram(s) qui reçoit une chaîne de caractère s et renvoie un dictionnaire dont les clés
## sont les lettres rencontrées et la valeur associée est la fréquence de la lettre dans s.
#def histogram(s):
#    dictio = {}
#    for lettre in s:
#        if lettre in dictio:
#            dictio[lettre] += 1
#        else :
#            dictio[lettre] = 1
#    return dictio
#print(histogram('la belle lucie aime le magnifique david'))




## Ecercice 1 : Liste de mot contennant les 6 voyalle de l'aphabet
#print([mot for mot in (open('mots.txt')) if (list(set(mot) & set('aeiouy'))) == list(set('aeiouy'))])